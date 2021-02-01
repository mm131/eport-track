package com.njeport.track.auth.realm;

import com.njeport.track.auth.bean.JwtToken;
import com.njeport.track.auth.service.JwtService;
import com.njeport.track.auth.service.SysUserService;
import com.njeport.track.common.utils.SessionUtils;
import com.njeport.track.common.vo.LoginUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author kongming
 * @date 2019/12/17
 */
@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private JwtService jwtService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权方法，告诉 Shiro 当前用户有哪些权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = null, userId = null;
        if (principalCollection != null) {
            LoginUserVO loginUser = (LoginUserVO) principalCollection.getPrimaryPrincipal();
            userId = loginUser.getUserId();
            username = loginUser.getUsername();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roleSet = sysUserService.queryRoles(userId);
        info.setRoles(roleSet);
        Set<String> permissionSet = sysUserService.queryResources(username);
        info.addStringPermissions(permissionSet);
        return info;
    }

    /**
     * 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        if (StringUtils.isEmpty(token)) {
            throw new AuthenticationException("token为空");
        }
        LoginUserVO loginUser = jwtService.parseToken(token);
        SessionUtils.setCurrentUser(loginUser);
        return new SimpleAuthenticationInfo(loginUser, token, getName());
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
