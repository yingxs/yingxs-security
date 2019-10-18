package com.yingxs.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yingxs.security.support.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class YingxsLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {


    private ObjectMapper objectMapper = new ObjectMapper();

    public YingxsLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        HashMap<String, String> result = new HashMap<>();
        result.put("message","请先登录");
        result.put("loginUrl","/authentication/form");
        response.getWriter().write(objectMapper.writeValueAsString( result));
    }
}
