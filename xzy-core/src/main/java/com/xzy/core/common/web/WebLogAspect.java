package com.xzy.core.common.web;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @Description: controller层日志打印 方便查询错误
 * @author xzy
 * @date Date:2021.1.31
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

//    @Pointcut("execution(* com.xzy.*.*.api.rest..*.*(..))")
    @Pointcut("execution(* com.xzy.*.*.demo.api..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = Calendar.getInstance().getTimeInMillis();
        log.info("================请求开始================");
        //获取当前请求对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        //获取method
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        log.info("[请求IP]:"+request.getRemoteAddr());
        log.info("[请求URL]:"+request.getRequestURL());
        log.info("[请求METHOD]:"+request.getMethod());
        log.info("[请求类名]:"+methodSignature.getDeclaringTypeName()+"[请求方法名]:"+methodSignature.getName());
        log.info("[请求参数]:"+getReqParams(methodSignature.getMethod(),joinPoint.getArgs()));

        Object proceed = null;
        try {
            proceed = joinPoint.proceed(joinPoint.getArgs());
        }catch (Exception e){
            log.info("================请求异常================");
            log.error(e.getMessage(),e);
            long endTime = Calendar.getInstance().getTimeInMillis();
            log.info("请求耗时"+(endTime-startTime)+"毫秒");
            log.info("================请求结束================");
            throw e;
        }
        long endTime = Calendar.getInstance().getTimeInMillis();
        log.info("请求耗时"+(endTime-startTime)+"毫秒");
        return proceed;
    }

    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturn(Object ret){
        //处理完请求后 返回内容
        log.info("================返回内容================");
        log.info("返回RESPONSE内容："+ JSON.toJSONString(ret));
        log.info("================请求结束================");
        //todo 清除请求头处理线程
    }

    public Object getReqParams(Method method,Object[] args){
        List<Object> argList = new ArrayList<>(args.length);
        Parameter[] parameters = method.getParameters();
        for(int i = 0 ;i < parameters.length;i++){
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if(requestBody!=null){
                argList.add(args[i]);
                continue;
            }
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if(requestParam!=null){
                Map<String,Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
                continue;
            }
        }
        return argList;
    }
}
