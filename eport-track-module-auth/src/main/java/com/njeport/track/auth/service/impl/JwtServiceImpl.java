package com.njeport.track.auth.service.impl;

import com.njeport.track.auth.dao.meta.track.SysUserDO;
import com.njeport.track.auth.service.JwtService;
import com.njeport.track.auth.service.SysUserService;
import com.njeport.track.auth.utils.JwtUtils;
import com.njeport.track.common.constants.EportConstants;
import com.njeport.track.common.enums.DelFlagEnum;
import com.njeport.track.common.vo.LoginUserVO;
import com.njeport.track.middleware.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kongming
 * @date 2019/12/18
 */
@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private RedisService redisService;

    @Value("${shiro.token-expire}")
    private long tokenExpire;

    /**
     * 将 token 解析为登录用户信息
     *
     * @param token jwt token字符串
     * @return 登录用户信息
     */
    public LoginUserVO parseToken(String token) {
        String username = JwtUtils.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token无效");
        }
        SysUserDO sysUser = sysUserService.queryByUsername(username);
        if (sysUser == null) {
            throw new AuthenticationException("用户名不存在");
        }
        if (sysUser.getDelFlag() == DelFlagEnum.DELETED) {
            throw new AuthenticationException("账号已被停用");
        }
        if (!refreshToken(token, username, sysUser.getPassword())) {
            throw new AuthenticationException("token已过期");
        }
        LoginUserVO loginUser = new LoginUserVO();
        loginUser.setUserId(sysUser.getId());
        BeanUtils.copyProperties(sysUser, loginUser);
        return loginUser;
    }

    /**
     * 刷新 token
     *
     * @param token    jwt token字符串
     * @param username 用户名
     * @param passWord 密码
     * @return token失效返回false
     */
    private boolean refreshToken(String token, String username, String passWord) {
        String cacheToken = redisService.readString(EportConstants.TOKEN_PREFIX + token);
        if (StringUtils.isNotEmpty(cacheToken)) {
            if (!JwtUtils.verify(cacheToken, username, passWord)) {
                String newAuthorization = JwtUtils.sign(username, passWord, tokenExpire);
                redisService.writeObject(EportConstants.TOKEN_PREFIX + token, newAuthorization, tokenExpire * 2 / 1000);
            }
            return true;
        }
        return false;
    }
}
