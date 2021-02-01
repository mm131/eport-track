package com.njeport.track.auth.filter;

import com.njeport.track.auth.bean.JwtToken;
import com.njeport.track.common.constants.EportConstants;
import com.njeport.track.common.utils.ResponseUtils;
import com.njeport.track.common.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author kongming
 * @date 2019/12/18
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return executeLogin(request, response);
        } catch (Exception e) {
            throw new AuthenticationException("token失效，请重新登录", e);
        }
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(EportConstants.TOKEN_HEADER);
        JwtToken jwtToken = new JwtToken(token);
        try {
            getSubject(request, response).login(jwtToken);
        } catch (AuthenticationException e) {
            log.error("捕捉到 AuthenticationException: {}", e.getMessage());
            ((HttpServletResponse) response).setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.getWriter().print("{\"success\": false, \"code\":" + HttpStatus.SC_UNAUTHORIZED + ", \"result\":\"\"" + ", \"message\":\"" + e.getMessage() + "\"}");
            response.getWriter().flush();
            return false;
        }
        return true;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ResponseUtils.setHeader(httpServletResponse);

        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.setStatus(HttpStatus.SC_OK);
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing) throws ServletException, IOException {
        super.cleanup(request, response, existing);
        SessionUtils.clearCurrentUser();
    }
}
