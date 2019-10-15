package com.yingxs.security.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * WEB工具类
 * @author yingxs
 * @date 2019-10-15 10:45:20
 */
public class WebUtil {

	public static final String COOKIE_NAME = "myCookie";
	//可写在Constant常量类里
	public static final String SESSION_USER = "sessionUser";
	
	public static void clearLoginCookies(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		if (session != null) {
			session.removeAttribute(SESSION_USER);//Constant.SESSION_USER
			session.invalidate();
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				cookie.setDomain("renren.com");
				cookie.setPath("/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			Cookie c = new Cookie(COOKIE_NAME, null);
			c.setPath("/");
			c.setMaxAge(0);
			response.addCookie(c);
		}
	}

	public static String getBrowserName() {
		return getBrowserName(ContextUtil.getRequest());
	}
	
	public static String getBrowserName(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");
		if (agent.indexOf("msie 7") > 0) {
			return "IE7";
		} else if (agent.indexOf("msie 8") > 0) {
			return "IE8";
		} else if (agent.indexOf("msie 9") > 0) {
			return "IE9";
		} else if (agent.indexOf("msie 10") > 0) {
			return "IE10";
		} else if (agent.indexOf("MSIE") > 0) {
			return "IE";
		} else if (agent.indexOf("opera") > 0) {
			return "opera";
		} else if (agent.indexOf("opera") > 0) {
			return "opera";
		} else if (agent.indexOf("Firefox") > 0) {
			return "Firefox";
		}else if(agent.indexOf("Chrome")>0){
			return "Chrome";
		} else if (agent.indexOf("WebKit") > 0) {
			return "WebKit";
		} else if (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0) {
			return "ie11";
		} else {
			return "Others";
		}
	}
	
}
