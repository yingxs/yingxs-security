package com.yingxs.security.aop;

import com.yingxs.security.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspects   {



    public LogAspects() {
        super();
//			System.out.println("aop的午餐构造....");
    }


    @Pointcut("@annotation(com.yingxs.security.exception.Logger)")
    public void pointCut() {};



    @Around(value="pointCut()")
    public SimpleResponse aroundLog(ProceedingJoinPoint pjp) throws ClassNotFoundException   {
        try {
            return  (SimpleResponse) pjp.proceed();
        } catch (Throwable t) {
            String classType = pjp.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            String methodName = pjp.getSignature().getName();
            log.info("["+clazzName+"]"+"--"+"["+methodName+"]:"+t.getMessage());
            t.printStackTrace();
            return new SimpleResponse(t.getMessage());
        }

    }





}