package com.yingxs.security.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yingxs.security.support.SimpleResponse;
import com.yingxs.security.support.UserInfo;
import com.yingxs.security.utils.ContextUtil;
import com.yingxs.security.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LogAspects   {

    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("@annotation(com.yingxs.security.aop.SysLog)")
    public void pointCut() {};


    @Around("pointCut()")
    public SimpleResponse saveSystemLog(ProceedingJoinPoint pjp) throws Exception {
        HttpServletRequest request = ContextUtil.getRequest();
        String method = request.getMethod();
        String loginIp = ContextUtil.getIpAddr(request);

        //获取注解描述信息
        String action  = getMethodDescription(pjp);
        String ctx = request.getContextPath();
        String requestUri = getRequestUri(request);
        requestUri = requestUri.substring(ctx.length()+1);

        Object[] args = pjp.getArgs();
        //方法参数
        String params = "";


        // 1.获取用户认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = null;
        String username = "游客";
        if (authentication != null && authentication.getPrincipal() instanceof  UserInfo) {
            userInfo = (UserInfo) authentication.getPrincipal();
            username = userInfo.getUsername();
        }


        // 2.判断参数中是否含有异常信息-主要针对登录失败
        for (Object obj:args) {
            if (obj instanceof HttpServletRequest) {
                Map<String, String[]> parameterMap = ((HttpServletRequest) obj).getParameterMap();
                params = objectMapper.writeValueAsString(parameterMap);
            }
        }

        // 3.方法执行中报错处理
        SimpleResponse ret = null;
        try {
            ret = (SimpleResponse)pjp.proceed();
        } catch (Throwable e) {
            saveErrorLog(request, method, loginIp, action, requestUri, params, username);
            return new SimpleResponse(e.getMessage());
        }

        // 4.方法正常运行日志记录
        saveInfoLog(request, method, loginIp, action, requestUri, params, username);
        return ret;
    }

    private void saveInfoLog(HttpServletRequest request, String method, String loginIp, String action, String requestUri, String params, String username) {
        log.info("type----> {}", "info");
        log.info("requestUri----> {}", requestUri);
        log.info("params----> {}", params);
        log.info("method----> {}", method);

        if ( "authentication/form".equals(requestUri)) {
            log.info("action----> {}", action+"-表单登录");
        } else {
            log.info("action----> {}", action+"-未知类型");
        }

        log.info("userName----> {}", username);
        log.info("browser----> {}", WebUtil.getBrowserName(request));
        log.info("loginIp----> {}", loginIp);
    }

    private void saveErrorLog(HttpServletRequest request, String method, String loginIp, String action, String requestUri, String params, String username) {
        log.error("type----> {}", "error");
        log.error("requestUri----> {}", requestUri);
        log.error("params----> {}", params);
        log.error("method----> {}", method);

        if ( "authentication/form".equals(requestUri)) {
            log.error("action----> {}", action+"-表单登录");
        } else {
            log.error("action----> {}", action+"-未知类型");
        }

        log.error("userName----> {}", username);
        log.error("browser----> {}", WebUtil.getBrowserName(request));
        log.error("loginIp----> {}", loginIp);
    }


    /**
     * 获取请求的uri
     * @author yingxs
     * @date 2019-10-15 10:36:40
     * @param request
     * @return
     */
    public String getRequestUri(HttpServletRequest request){
        String ctx = request.getContextPath();
        String requestUri = request.getRequestURI();
        requestUri = requestUri.substring(ctx.length()+1);
        return requestUri;
    }

    /**
     * 通过注释获取方法的描述信息
     * @author yingxs
     * @date 2019-10-15 10:37:09
     * @param joinPoint 切点
     * @return 方法描述
     */
    public String getMethodDescription(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if(syslog != null){
            return syslog.value();
        }
        return null;
    }



}