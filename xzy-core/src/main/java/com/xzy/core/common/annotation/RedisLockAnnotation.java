package com.xzy.core.common.annotation;


import com.xzy.core.common.constant.RedisLockTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xzy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RedisLockAnnotation {
    /**
     * 特定参数识别，默认取第 0 个下标
     */
    int lockFiled() default 0;
    /**
     * 超时重试时间
     */
    long tryTime() default 3;

    /**
     * 自定义加锁类型
     */
    RedisLockTypeEnum typeEnum();
    /**
     * 释放时间，秒 s 单位
     */
    long lockTime() default 5;
}
