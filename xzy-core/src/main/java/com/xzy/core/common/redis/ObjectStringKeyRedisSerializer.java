package com.xzy.core.common.redis;

import com.alibaba.fastjson.JSON;
import com.xzy.core.common.exception.AppException;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * 自定义redis key 字符串类型序列化
 * @author xzy
 */
public class ObjectStringKeyRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    private final String target = "\"";

    private final String replacement = "";

    public ObjectStringKeyRedisSerializer(){
        //默认使用utf-8
        this(Charset.defaultCharset());
    }

    public ObjectStringKeyRedisSerializer(Charset charset) {
        Optional.ofNullable(charset).orElseThrow(() -> new AppException("redis key Charset can not be Null!!!"));
        this.charset = charset;
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        String string = JSON.toJSONString(o);
        if (string == null) {
            return null;
        }
        string = string.replace(target, replacement);
        return string.getBytes(charset);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : new String(bytes,charset));
    }
}
