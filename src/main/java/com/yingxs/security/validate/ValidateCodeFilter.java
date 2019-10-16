package com.yingxs.security.validate;

import com.yingxs.security.controller.ValidateCodeController;
import com.yingxs.security.exception.ValidateCodeExcecption;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ValidateCodeFilter extends OncePerRequestFilter {


    // 认证失败处理器
    private AuthenticationFailureHandler authenticationFailureHandler;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if(StringUtils.equals("/authentication/form", request.getRequestURI())  && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            try {
                validate(request);
            } catch (ValidateCodeExcecption e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response,e);
                return ;
            }

        }
        filterChain.doFilter(request, response);
    }


    private void validate(HttpServletRequest request) throws ServletRequestBindingException {
        HttpSession session = request.getSession();
        String codeId = request.getParameter("codeId");
        String codeInRequest = request.getParameter("imageCode");
        // 从session中取出之前的ImageCode
        ImageCode codeInSession = (ImageCode) session.getAttribute(ValidateCodeController.SESSION_KEY+codeId);

        if (StringUtils.isBlank(codeId)) {
            throw new ValidateCodeExcecption("验证码标识不能为空");
        }
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeExcecption("验证码的值不能为空");
        }
        if (codeInSession == null) {
            throw new ValidateCodeExcecption("验证码不存在");
        }

        if (codeInSession.isExpried()) {
            session.removeAttribute(ValidateCodeController.SESSION_KEY+codeId);
            throw new ValidateCodeExcecption("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeExcecption("验证码不匹配");
        }

        // 验证码认证成功，将验证码从session中移除
        session.removeAttribute(ValidateCodeController.SESSION_KEY+codeId);
    }


    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
