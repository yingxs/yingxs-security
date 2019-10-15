package com.yingxs.security.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 应用上下文工具类
 * @author yingxs
 * @date 2019-10-15 10:45:33
 */
public class ContextUtil {

	/**
	 * 获取request对象
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	/**
	 * 获取session对象
	 */
	public static HttpSession getSession() {
		HttpServletRequest request = ContextUtil.getRequest();
		return request.getSession();
	}
	
	public static boolean isEmpty(String str) {
		return null == str || str.trim().length() == 0;
	}
	
	/**
     * 通过HttpServletRequest返回IP地址
     * @return ip IP地址
     */
	public static String getIpAddr() {
		HttpServletRequest request = ContextUtil.getRequest();
		return getIpAddr(request);
	}
	
	/**
     * 通过HttpServletRequest返回IP地址
     * @param request HttpServletRequest
     * @return IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
    	String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ip;
    }
    
}
