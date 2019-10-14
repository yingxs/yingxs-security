package com.yingxs.security.config;

import com.yingxs.security.authentication.YingxsAuthenticationFaiurelHandler;
import com.yingxs.security.authentication.YingxsAuthenticationSuccessHandler;
import com.yingxs.security.authentication.form.YingxUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationSecurityConfig  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    YingxsAuthenticationSuccessHandler yingxsAuthenticationSuccessHandler;

    @Autowired
    YingxsAuthenticationFaiurelHandler yingxsAuthenticationFaiurelHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        YingxUsernamePasswordAuthenticationFilter yingxUsernamePasswordAuthenticationFilter = new YingxUsernamePasswordAuthenticationFilter();
        yingxUsernamePasswordAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        yingxUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(yingxsAuthenticationFaiurelHandler);
        yingxUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(yingxsAuthenticationSuccessHandler);
        http.addFilterAfter(yingxUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
