package com.yingxs.security.authentication;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yingxs.security.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 * @author yingxs
 * @date 2019-10-9 10:56:01
 * @email ying_xs@163.com
 */
@Component("yingxsAuthenticationFaiurelHandler")
public class YingxsAuthenticationFaiurelHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");

        String message ;
        if ( exception instanceof InternalAuthenticationServiceException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            message = "用户名或密码不能为空";
        } else {
            message = exception.getMessage();
        }

        response.getWriter().write(objectMapper.writeValueAsString( new SimpleResponse(message)));
    }
}

