package com.njeport.track.auth.controller.common;

import com.google.common.collect.Maps;
import com.njeport.track.auth.dao.condition.LoginCondition;
import com.njeport.track.auth.dao.meta.track.SysUserDO;
import com.njeport.track.auth.service.SysUserService;
import com.njeport.track.auth.service.VerificationCodeService;
import com.njeport.track.auth.utils.JwtUtils;
import com.njeport.track.common.constants.EportConstants;
import com.njeport.track.common.utils.EncryptUtils;
import com.njeport.track.common.vo.CommonReturnVO;
import com.njeport.track.middleware.redis.service.RedisService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @author kongming
 * @date 2019/12/18
 */
@RestController
@RequestMapping("/sys")
@Slf4j
public class LoginController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private RedisService redisService;

    @Resource
    private VerificationCodeService verificationCodeService;

    @Value("${shiro.token-expire}")
    private long tokenExpire;

    //客户端使用
    @Value("${shiro.token-expire2}")
    private long tokenExpire2;

    /**
     * 登录接口
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonReturnVO login(@RequestBody LoginCondition loginCondition) {
        String username = loginCondition.getUsername();
        String password = loginCondition.getPassword();
        String captchaId = loginCondition.getCaptchaId();
        String captchaValue = loginCondition.getCaptchaValue();

        // 验证码校验
        verificationCodeService.checkCode(captchaId, captchaValue);

        // 密码校验
        SysUserDO user = sysUserService.queryByUsername(username);
        if (user == null) {
            return CommonReturnVO.fail("无效的用户名");
        }
        sysUserService.checkValidUser(user);
        String tempPass = EncryptUtils.encrypt(username, password, user.getSalt());
        if (!Objects.equals(tempPass, user.getPassword())) {
            return CommonReturnVO.fail("用户名或密码错误");
        }

        Map<String, Object> result = Maps.newHashMap();
        result.put("id", user.getId());
        result.put("username", user.getUsername());
        result.put("realname", user.getRealname());
        result.put("avatar", user.getAvatar());
        result.put("orgCode", user.getOrgCode());
        wrapLoginResult(username, user.getPassword(), result);
        return CommonReturnVO.succ(result);
    }

    /**
     * 登录成功后，在redis里写入登录Token缓存
     *
     * @param username 用户名
     * @param password 密码
     */
    private void wrapLoginResult(String username, String password, Map<String, Object> result) {
        String token = JwtUtils.sign(username, password, tokenExpire);
        redisService.writeString(EportConstants.TOKEN_PREFIX + token, token, tokenExpire * 2 / 1000);
        result.put("token", token);
    }


    /**
     * 客户端登录接口
     */
    @RequestMapping(value = "/customLogin", method = RequestMethod.POST)
    public CommonReturnVO customLogin(@RequestBody LoginCondition loginCondition) {
        String username = loginCondition.getUsername();
        String password = loginCondition.getPassword();

        // 密码校验
        SysUserDO user = sysUserService.queryByUsername(username);
        if (user == null) {
            return CommonReturnVO.fail("无效的用户名");
        }
        sysUserService.checkValidUser(user);
        String tempPass = EncryptUtils.encrypt(username, password, user.getSalt());
        if (!Objects.equals(tempPass, user.getPassword())) {
            return CommonReturnVO.fail("用户名或密码错误");
        }

        Map<String, Object> result = Maps.newHashMap();
        result.put("id", user.getId());
        result.put("username", user.getUsername());
        result.put("realname", user.getRealname());
        result.put("avatar", user.getAvatar());
        result.put("orgCode", user.getOrgCode());
        wrapCustomLoginResult(username, user.getPassword(), result);
        return CommonReturnVO.succ(result);
    }


    /**
     * 客户端登录成功后，在redis里写入登录Token缓存
     *
     * @param username 用户名
     * @param password 密码
     */
    private void wrapCustomLoginResult(String username, String password, Map<String, Object> result) {
        String token = JwtUtils.sign(username, password, tokenExpire2);
        redisService.writeString(EportConstants.TOKEN_PREFIX + token, token, tokenExpire2 * 2 / 1000);
        result.put("token", token);
    }


    /**
     * 登出接口
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public CommonReturnVO logout(HttpServletRequest request) {
        String token = request.getHeader(EportConstants.TOKEN_HEADER);
        if (StringUtils.isEmpty(token)) {
            return CommonReturnVO.fail("退出登录失败");
        }
        String username = JwtUtils.getUsername(token);
        SysUserDO user = sysUserService.queryByUsername(username);
        if (user != null) {
            redisService.del(EportConstants.TOKEN_PREFIX + token);
            redisService.del(String.format("%s::%s", EportConstants.CACHE_NAME, username));
            SecurityUtils.getSubject().logout();
            return CommonReturnVO.succ("退出登录成功");
        } else {
            return CommonReturnVO.fail("token无效，退出登录失败");
        }
    }

    /**
     * 无权限返回 403
     */
    @GetMapping("/noauth")
    public CommonReturnVO noauth() {
        return CommonReturnVO.fail(HttpStatus.SC_FORBIDDEN, null, "无访问权限");
    }
}
