package com.yingxs.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    // 配置密码加密与解密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/yingxs-signIn.html")           // 登陆页面地址
                .loginProcessingUrl("/authentication/form")// 登录请求地址
            .and()
                .authorizeRequests()
                .antMatchers( "/yingxs-signIn.html" )
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
