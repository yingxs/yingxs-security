package com.yingxs.security.config;

import com.yingxs.security.authentication.YingxsAccessDeniedHandler;
import com.yingxs.security.authentication.YingxsLoginUrlAuthenticationEntryPoint;
import com.yingxs.security.authentication.form.YingxUsernamePasswordAuthenticationFilter;
import com.yingxs.security.authentication.YingxsAuthenticationFaiurelHandler;
import com.yingxs.security.authentication.YingxsAuthenticationSuccessHandler;
import com.yingxs.security.properties.SecurityProperties;
import com.yingxs.security.validate.ImageCodeGenerator;
import com.yingxs.security.validate.ValidateCodeFilter;
import com.yingxs.security.validate.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;

/**
 *  springScurity安全配置
 *  @author yingxs
 *  @date 2019-10-9 11:04:40
 *  @email ying_xs@163.com
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private YingxsAuthenticationSuccessHandler yingxsAuthenticationSuccessHandler;

    @Autowired
    private YingxsAuthenticationFaiurelHandler yingxsAuthenticationFaiurelHandler;

    @Autowired
    private UsernamePasswordAuthenticationSecurityConfig usernamePasswordAuthenticationSecurityConfig;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AccessDeniedHandler yingxsAccessDeniedHandler;

    // 配置密码加密与解密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity  http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(yingxsAuthenticationFaiurelHandler);

        http
            .exceptionHandling()
                .authenticationEntryPoint(new YingxsLoginUrlAuthenticationEntryPoint(""))
                .accessDeniedHandler(yingxsAccessDeniedHandler);
        if (securityProperties.getCode().getImage().isEnable()) {
            http.addFilterBefore(validateCodeFilter,YingxUsernamePasswordAuthenticationFilter.class);
        }
        http.apply(usernamePasswordAuthenticationSecurityConfig)
                .and()
            .formLogin()
            .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
//            .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
            .failureHandler(yingxsAuthenticationFaiurelHandler)
            .successHandler(yingxsAuthenticationSuccessHandler)
                .and()
            .authorizeRequests()
            .antMatchers( SecurityConstants.DEFAULT_LOGIN_PAGE,SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,"/code/*" )
            .permitAll()
                .antMatchers(HttpMethod.GET,"/customer").hasRole("ADMIN")
            .anyRequest()
            .authenticated()
                .and()
            .csrf().disable();

//        http.httpBasic()			//使用http认证的形式进行登录
//                .and()
//                .authorizeRequests()	// 对请求做授权，以下配置都是请求授权的配置
//                .anyRequest()			// 任何请求
//                .authenticated();		// 都需要身份认证

    }

    @Bean // 不存在名为imageCodeGenerator的bean时才用这个
    @ConditionalOnMissingBean( name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }


}
