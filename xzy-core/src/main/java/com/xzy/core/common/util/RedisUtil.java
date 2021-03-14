package com.xzy.core.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.xzy.core.common.exception.AppException;
import com.xzy.core.common.redis.ObjectStringKeyRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * redis操作工具类 所有项目操作redis使用该工具类进行
 * 使用redisTemplate操作所有的value 都只能是 JSON格式 或者 数值类型 或者会报错
 * @author xzy
 */
@Component
@ConditionalOnClass(RedisTemplate.class)
@Slf4j
public class RedisUtil {
    private RedisTemplate redisTemplate;
    private RedisTemplate<String, String> redisStringTemplate;
    private HashOperations hashOperations;
    private ValueOperations valueOperations;
    private ListOperations listOperations;
    private SetOperations setOperations;
    private ZSetOperations zSetOperations;

    private static String executeSucessStr = "OK";

    private static Long DEFAULT_TIME_OUT = 60 * 60 * 24 * 1000L;

    @Autowired
    public RedisUtil(RedisTemplate redisTemplate, HashOperations hashOperations, ValueOperations valueOperations, ListOperations listOperations, SetOperations setOperations, ZSetOperations zSetOperations,StringRedisTemplate redisStringTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = hashOperations;
        this.valueOperations = valueOperations;
        this.listOperations = listOperations;
        this.setOperations = setOperations;
        this.zSetOperations = zSetOperations;
        this.redisStringTemplate = redisStringTemplate;
    }

    /**
     *  ==========================================================================================================
     *  ================================================***操作字符串封装*****=====================================
     *  ==========================================================================================================
     */

    /**
     * set key value 操作 设置默认超时时间一天
     * @param key KEY
     * @param value VALUE
     * @return true/false
     */
    public Boolean setValue(String key,Serializable value){
        return setValueExpire(key,value,DEFAULT_TIME_OUT);
    }

    /**
     * set key value px time 操作
     * @param key KEY
     * @param value VALUE
     * @param timeout 超时时间
     * @return true/false
     */

    public Boolean setValueExpire(String key, Serializable value, long timeout){
        return setValueExpire(key,value,timeout,TimeUnit.MILLISECONDS);
    }

    public Boolean setValueExpire(String key, Serializable value, long timeout, TimeUnit timeUnit){
        valueOperations.set(key,value,timeout,timeUnit);
        return true;
    }

    /**
     * set key value offset set偏移量操作
     * @param key KEY
     * @param value VALUE
     * @param offset 偏移量
     * @return true/false
     */
    public Boolean setValue(String key,Serializable value,long offset){
        valueOperations.set(key,value,offset);
        return true;
    }

    /**
     * getset 操作
     * @param key KEY
     * @param value VALUE
     * @return true/false
     */
    public Object getAndSet(String key,Serializable value){
        Object andSet = valueOperations.getAndSet(key, value);
        return andSet;
    }

    /**
     * get 操作
     * @param key KEY
     * @return Object
     */
    public Object get(String key){
        try {
            return valueOperations.get(key);
        }catch (Exception e){
            throw new AppException("只能获取json形式的格式化数据或数值类型！！！");
        }
    }
    /**
     * multiGet 操作
     * @param collection key的集合
     * @return List 只能获取json形式的格式化数据或数值类型 其余类型报错
     */
    public List multiGet(List collection){
        if(CollectionUtil.isEmpty(collection)){
            return null;
        }
        try {
            return valueOperations.multiGet(collection);
        }catch (Exception e){
            throw new AppException("只能获取json形式的格式化数据或数值类型！！！");
        }
    }

    /**
     * set key value 设置字符串 操作 设置默认超时时间一天
     * @param key KEY
     * @param value VALUE
     * @return true/false
     */
    public Boolean setStr(String key,String value){
        return setStrExpire(key,value,DEFAULT_TIME_OUT);
    }

    /**
     * set key value timeout 设置字符串 操作 设置默认时间单位为毫秒
     * @param key KEY
     * @param value VALUE
     * @return true/false
     */
    public Boolean setStrExpire(String key,String value,long timeout){
        return setStrExpire(key, value, timeout,TimeUnit.MILLISECONDS);
    }

    /**
     * set key value timeout 设置字符串 操作
     * @param key KEY
     * @param value VALUE
     * @return true/false
     */
    public Boolean setStrExpire(String key,String value,long timeout,TimeUnit timeUnit){
        redisStringTemplate.opsForValue().set(key,value,timeout,timeUnit);
        return true;
    }

    /**
     * get key 操作 获取字符串
     * @param key KEY
     * @return string
     */
    public String getStr(String key){
        return redisStringTemplate.opsForValue().get(key);
    }

    /**
     * mget key 操作 获取字符串集合
     *
     * @param collection KEY集合
     * @return string集合
     */
    public List<String> getMultiStr(Collection<String> collection){
        return redisStringTemplate.opsForValue().multiGet(collection);
    }

    /**
     * set key value nx px timeout 操作 使用redis原生连接实现
     * @param key KEY
     * @param value VALUE
     * @param expire 超时时间
     * @return true/false
     */
    public Boolean setAndExpireIfAbsent(final String key, final Serializable value, final long expire){
        if(expire < 0 ){
            return setIfAbsent(key,value);
        }
        Object execute = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                ObjectStringKeyRedisSerializer serializer = new ObjectStringKeyRedisSerializer();
                return connection.execute("set", serializer.serialize(key)
                        , serializer.serialize(value), "NX".getBytes(Charset.defaultCharset()), "PX".getBytes(Charset.defaultCharset()),
                        new Long(expire).toString().getBytes(Charset.defaultCharset()));
            }
        });

        return Objects.equals((String) execute, executeSucessStr);
    }

    /**
     * set key value nx 操作
     * @param key KEY
     * @param value VALUE
     * @return true/false
     */
    public Boolean setIfAbsent(String key, Serializable value){
        return valueOperations.setIfAbsent(key, value);
    }

    /**
     *  ==========================================================================================================
     *  ================================================***操作哈希结构封装*****=====================================
     *  ==========================================================================================================
     */


    /**
     *  ==========================================================================================================
     *  ================================================***操作list封装*****=====================================
     *  ==========================================================================================================
     */


    /**
     *  ==========================================================================================================
     *  ================================================***操作set封装*****=====================================
     *  ==========================================================================================================
     */



    /**
     *  ==========================================================================================================
     *  ================================================***操作zSet封装*****=====================================
     *  ==========================================================================================================
     */
}
