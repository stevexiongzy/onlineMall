package com.mall.itemcenter.aspect;

import com.mall.itemcenter.annotation.RedisLockAnnotation;
import com.mall.itemcenter.common.RedisLockDefinitionHolder;
import com.mall.itemcenter.common.RedisLockTypeEnum;
import lombok.extern.slf4j.Slf4j;
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
import java.util.concurrent.*;

@Aspect
@Slf4j
@Component
public class RedisLockAspect {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    // 扫描的任务队列
    private static ConcurrentLinkedQueue<RedisLockDefinitionHolder> holderList = new ConcurrentLinkedQueue();

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();
    {
        SCHEDULER.scheduleAtFixedRate(() -> {

        },0,2,TimeUnit.SECONDS);
    }

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

        long lockTime = annotation.lockTime();
        boolean isSuccess = stringRedisTemplate.opsForValue().setIfAbsent(businessKey,uniqueValue,lockTime, TimeUnit.SECONDS);
        if(!isSuccess){
            throw new Exception("another get lock");
        }

        result = pjp.proceed();
        //开启一个线程检查超时状态
        new Thread(new UpdateLockTimeoutTask(uniqueValue,businessKey,stringRedisTemplate));

        return result;
    }

    class UpdateLockTimeoutTask implements Runnable {
        private String uuid;
        private String key;
        private StringRedisTemplate stringRedisTemplate;

        public UpdateLockTimeoutTask(String uuid, String key, StringRedisTemplate stringRedisTemplate) {
            this.uuid = uuid;
            this.key = key;
            this.stringRedisTemplate = stringRedisTemplate;
        }

        @Override
        public void run() {

        }
    }
}
