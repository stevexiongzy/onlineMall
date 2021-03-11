package com.xzy.core.common.redis;

import com.xzy.core.common.annotation.RedisLockAnnotation;
import com.xzy.core.common.constant.RedisLockDefinitionHolder;
import com.xzy.core.common.constant.RedisLockTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.internals.TransactionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import com.xzy.core.common.exception.AppException;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author xzy
 * @Description: redis锁切面类 使用redission实现
 */
@Aspect
@Slf4j
@Component
public class RedisLockAspect {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedissonClient redissonClient;

    // 扫描的任务队列
//    private static ConcurrentLinkedQueue<RedisLockDefinitionHolder> holderList = new ConcurrentLinkedQueue();
//
//    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();
//    {
//        SCHEDULER.scheduleAtFixedRate(() -> {
//        },0,2,TimeUnit.SECONDS);
//    }

    /**
     * @annotation 中的路径表示拦截特定注解
     */
    @Pointcut("@annotation(com.xzy.core.common.annotation.RedisLockAnnotation)")
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

        long lockTime = annotation.lockTime();
        RLock lock = redissonClient.getLock(businessKey);

        boolean tryLock = lock.tryLock(annotation.tryTime(), lockTime, TimeUnit.SECONDS);
        if(!tryLock){
            throw new AppException("redis锁已被占用");
        }
        Object result = null;
        try {
            result = pjp.proceed();
        }catch (Exception e){
            throw e;
        }finally {
            lock.unlock();
        }
        return result;
    }

}
