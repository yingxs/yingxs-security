package com.yingxs.security.config;

/**
 * 常量配置
 * @author yingxs
 * @date 2019-10-12 10:48:15
 */
public class SecurityConstants {

    // 当请求需要身份认证时，默认跳转的url
    public static final String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    // 默认登录页面
    public static final String DEFAULT_LOGIN_PAGE = "/yingxs-signIn.html";

    // 默认的用户名密码登录请求处理url
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";


}
