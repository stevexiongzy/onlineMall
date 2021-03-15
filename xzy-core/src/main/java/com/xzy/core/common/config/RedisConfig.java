package com.xzy.core.common.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.xzy.core.common.exception.AppException;
import com.xzy.core.common.redis.ObjectStringKeyRedisSerializer;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author xzy
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {
    @Bean
    public RedissonClient redissonClient(){
        Config config = null;
        try {
            config = Config.fromYAML(ResourceUtils.getFile("classpath:redission.yml"));
        }catch (IOException e){
            throw new AppException("redission 初始化出错"+e);
        }
        return Redisson.create(config);
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        ObjectStringKeyRedisSerializer objectStringKeyRedisSerializer = new ObjectStringKeyRedisSerializer();
        //全局开始autoType
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);

//         或者使用小范围白名单模式
//         ParserConfig.getGlobalInstance().addAccept("com.xxx");
        //设置连接对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置redis 序列化
        redisTemplate.setKeySerializer(objectStringKeyRedisSerializer);
        redisTemplate.setHashKeySerializer(objectStringKeyRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @DependsOn("redisTemplate")
    public HashOperations<String, Object, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    @DependsOn("redisTemplate")
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    @DependsOn("redisTemplate")
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    @DependsOn("redisTemplate")
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    @DependsOn("redisTemplate")
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }
}
