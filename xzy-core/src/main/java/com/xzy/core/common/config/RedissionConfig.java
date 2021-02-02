package com.xzy.core.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xzy
 */
@Configuration
public class RedissionConfig {
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("192.168.183.186:6379").setDatabase(0);
        return Redisson.create(config);
    }
}
