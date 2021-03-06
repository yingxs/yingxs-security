package com.yingxs.security.controller;


import com.yingxs.security.config.SecurityConstants;
import com.yingxs.security.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class YingxsSecurityController {

    // 缓存request
    private RequestCache requestCache = new HttpSessionRequestCache();


    // 请求跳转工具类
    private RedirectStrategy redirectStrategy= new DefaultRedirectStrategy();


    /**
     * 当需要身份认证时，跳转到这里 在此判断引发跳转的请求是html与否
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request , HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是："+targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, SecurityConstants.DEFAULT_LOGIN_PAGE);
            }
        }

        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }

}
