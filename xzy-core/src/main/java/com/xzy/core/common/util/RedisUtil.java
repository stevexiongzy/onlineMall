package com.xzy.core.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis操作工具类 所有项目操作redis使用该工具类进行
 * @author xzy
 */
@Component
@ConditionalOnClass(RedisTemplate.class)
@Slf4j
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;


}
