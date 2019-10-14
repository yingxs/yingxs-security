package com.yingxs.security.authentication.form;

import com.yingxs.security.config.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义的用户名密码认证过滤器
 * @author yingxs
 * @date 2019-10-14 16:08:23
 */
public class YingxUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 这个参数为UsernamePasswordAuthenticationFilter的私有参数 无法通过继承共享
     */
    private boolean postOnly = true;

    public YingxUsernamePasswordAuthenticationFilter() {
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("登录请求仅支持POST方式");
        }
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null || "".equals(username)) {
            throw new AuthenticationServiceException("username 参数不能为空");
        }

        if (password == null || "".equals(password)) {
            throw new AuthenticationServiceException("password 参数不能为空");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username.trim(), password.trim());
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }




}
