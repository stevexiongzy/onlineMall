package com.xzy.core.common.web;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description: controller层日志打印 方便查询错误
 * @author xzy
 * @date Date:2021.1.31
 * @Company: 上海比升互联网技术
 * @Version V1.0
 */
@Aspect
@Order(1)
@Component
public class WebLogAspect {
    /**
     * 日志
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

//    @Autowired(required = false)
//    private AppExceptionHandler appExceptionHandler;

    @Pointcut("execution(* com.xzy.*.*.api.rest..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        //获取当前请求对象
        return null;
    }
}
