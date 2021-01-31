package com.mall.itemcenter.aspect;

import com.mall.itemcenter.annotation.RedisLockAnnotation;
import com.mall.itemcenter.common.RedisLockTypeEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@Component
public class RedisLockAspect {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @annotation 中的路径表示拦截特定注解
     */
    @Pointcut("@annotation(com.mall.itemcenter.annotation.RedisLockAnnotation)")
    public void redisLockPC() {
    }

    @Around(value = "redisLockPC()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 解析参数
        Method method = ((MethodSignature)pjp.getSignature()).getMethod();
        RedisLockAnnotation annotation = method.getAnnotation(RedisLockAnnotation.class);
        RedisLockTypeEnum redisLockTypeEnum = annotation.typeEnum();
        Object[] params = pjp.getArgs();
        String ukString = params[annotation.lockFiled()].toString();
        // 省略很多参数校验和判空
        String businessKey = redisLockTypeEnum.getUniqueKey(ukString);
        String uniqueValue = UUID.randomUUID().toString();
        // 加锁
        Object result = null;
        boolean isSuccess = stringRedisTemplate.opsForValue().setIfAbsent(businessKey,uniqueValue);
        if(!isSuccess){
            throw new Exception("another get lock");
        }

        return null;
    }
}
