package com.yingxs.security.config;

import com.yingxs.security.authentication.form.YingxUsernamePasswordAuthenticationFilter;
import com.yingxs.security.authentication.YingxsAuthenticationFaiurelHandler;
import com.yingxs.security.authentication.YingxsAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;

/**
 *  springScurity安全配置
 *  @author yingxs
 *  @date 2019-10-9 11:04:40
 *  @email ying_xs@163.com
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    YingxsAuthenticationSuccessHandler yingxsAuthenticationSuccessHandler;

    @Autowired
    YingxsAuthenticationFaiurelHandler yingxsAuthenticationFaiurelHandler;

    @Autowired
    UsernamePasswordAuthenticationSecurityConfig usernamePasswordAuthenticationSecurityConfig;

    // 配置密码加密与解密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity  http) throws Exception {
        http.apply(usernamePasswordAuthenticationSecurityConfig)
                .and()
            .formLogin()
            .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
//                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
//                .failureHandler(yingxsAuthenticationFaiurelHandler)
//                .successHandler(yingxsAuthenticationSuccessHandler)
            .and()
                .authorizeRequests()
                .antMatchers( SecurityConstants.DEFAULT_LOGIN_PAGE,SecurityConstants.DEFAULT_UNAUTHENTICATION_URL )
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();

//        http.httpBasic()			//使用http认证的形式进行登录
//                .and()
//                .authorizeRequests()	// 对请求做授权，以下配置都是请求授权的配置
//                .anyRequest()			// 任何请求
//                .authenticated();		// 不需要身份认证

    }
}
