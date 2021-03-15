package com.xzy.core.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.xzy.core.common.exception.AppException;
import com.xzy.core.common.redis.ObjectStringKeyRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis操作工具类 所有项目操作redis使用该工具类进行
 * 使用redisTemplate操作所有的value 都只能是 JSON格式 或者 数值类型 或者会报错
 * @author xzy
 */
@Component
@ConditionalOnBean(RedisTemplate.class)
@Slf4j
public class RedisUtil {
    private RedisTemplate<String, Object> redisTemplate;
    private RedisTemplate<String, String> redisStringTemplate;
    private HashOperations<String, Object, Object> hashOperations;
    private ValueOperations<String, Object> valueOperations;
    private ListOperations<String, Object> listOperations;
    private SetOperations<String, Object> setOperations;
    private ZSetOperations<String, Object> zSetOperations;
    private RedissonClient redissonClient;

    private static String executeSucessStr = "OK";

    private static Long DEFAULT_TIME_OUT = 60 * 60 * 24 * 1000L;

    private static Long DEFAULT_LOCK_WAITTIME = 5 * 1000L;

    @Autowired
    public RedisUtil(RedissonClient redissonClient, RedisTemplate<String,Object> redisTemplate, HashOperations<String, Object, Object> hashOperations,
                     ValueOperations<String, Object> valueOperations, ListOperations<String,Object> listOperations,
                     SetOperations<String,Object> setOperations, ZSetOperations<String,Object> zSetOperations, StringRedisTemplate redisStringTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = hashOperations;
        this.valueOperations = valueOperations;
        this.listOperations = listOperations;
        this.setOperations = setOperations;
        this.zSetOperations = zSetOperations;
        this.redisStringTemplate = redisStringTemplate;
        this.redissonClient = redissonClient;
    }

    /**
     *  ==========================================================================================================
     *  ================================================***基本操作封装*****=====================================
     *  ==========================================================================================================
     */

    /**
     * 删除 key 集合
     * @param keyList KEY集合
     * @return 删除的条数
     */
    public Long delete(Collection<String> keyList){
        return redisTemplate.delete(keyList);
    }

    /**
     * 删除 key
     * @param key KEY
     * @return true/false
     */
    public Boolean delete(String key){
        return redisTemplate.delete(key);
    }

    /**
     * 设置 ttl
     * @param key KEY
     * @param timeout ttl
     * @param timeunit 时间单位
     * @return true/false
     */
    public Boolean expire(String key,Long timeout,TimeUnit timeunit){
        return redisTemplate.expire(key, timeout, timeunit);
    }

    /**
     * 返回ttl
     * @param key
     * @return ttl
     */
    public Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断是否包含key
     * @param key
     * @return true/false
     */
    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据表达式查询key
     * @param pattern 表达式
     * @return key 集合
     */
    public Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
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
        return valueOperations.getAndSet(key, value);
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
    public List<Object> multiGet(List<String> collection){
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
        Object execute = redisTemplate.execute((RedisCallback<Object>) connection -> {
            ObjectStringKeyRedisSerializer serializer = new ObjectStringKeyRedisSerializer();
            return connection.execute("set", serializer.serialize(key)
                    , serializer.serialize(value), "NX".getBytes(Charset.defaultCharset()), "PX".getBytes(Charset.defaultCharset()),
                    Long.toString(expire).getBytes(Charset.defaultCharset()));
        });

        return Objects.equals(execute, executeSucessStr);
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
     * increment key value 操作
     * @param key KEY
     * @param value VALUE
     * @return Long 自增后返回值
     */
    public Long increment(String key,long value){
        return valueOperations.increment(key, value);
    }

    /**
     * increment key value 操作
     * @param key KEY
     * @param value VALUE
     * @return Double 自增后返回值
     */
    public Double increment(String key,double value){
        return valueOperations.increment(key, value);
    }

    /**
     *  ==========================================================================================================
     *  ================================================***分布式锁*****=====================================
     *  redis锁使用redisson框架实现，不传入leaseTime时，会启用watchdog机制，给锁一直续时，
     *  续时到时间为 lockWatchdogTimeout 默认续到 30s,当 ttl 为 lockWatchdogTimeout / 3 时进行续时
     *  lockWatchdogTimeout可通过yml方式进行配置 ，注意 watchdog会一直续时 ，如果出现死循环死锁 ，lock key会一直存在。
     *  ==========================================================================================================
     */
    public Boolean lock(String businessKey){
        return lock(businessKey,DEFAULT_LOCK_WAITTIME,-1);
    }

    /**
     *
     * @param businessKey lock KEY
     * @param waitTime 获取锁等待的时间 默认等待5s
     * @return true/false
     * @Description: 使用 watchdog
     */
    public Boolean lock(String businessKey,long waitTime){
        return lock(businessKey, waitTime,-1);
    }

    /**
     *
     * @param businessKey lock KEY
     * @param waitTime 获取锁等待的时间 默认等待5s
     * @param leaseTime 锁持续的时间
     * @return true/false
     */
    public Boolean lock(String businessKey,long waitTime,long leaseTime){
        RLock lock = redissonClient.getLock(businessKey);
        boolean tryLock;
        try {
            tryLock = lock.tryLock(waitTime,leaseTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new AppException("redis 加锁异常"+e);
        }
        return tryLock;
    }

    /**
     * @Description: 解锁操作
     * @param businessKey lock KEY
     * @return true/false
     */
    public Boolean unLock(String businessKey){
        RLock lock = redissonClient.getLock(businessKey);
        lock.unlock();
        return true;
    }

    /**
     *  ==========================================================================================================
     *  ================================================***操作哈希结构封装*****=====================================
     *  ==========================================================================================================
     */

    /**
     * hPut key hashKey value 操作
     * @param key 大KEY
     * @param hashKey hashKEY
     * @param hashValue hashVALUE
     * @return true/false
     */
    public Boolean hPut(String key,Object hashKey,Object hashValue){
        hashOperations.put(key, hashKey, hashValue);
        return true;
    }

    /**
     * hPut key [hashKey value ... ] 操作
     * @param key 大KEY
     * @param map Map<hashKEY,hashVALUE>
     * @return true/false
     */
    public Boolean hPutAll(String key, Map<Object, Object> map){
        hashOperations.putAll(key, map);
        return true;
    }

    /**
     * hincrement key value 操作
     * @param key KEY
     * @param value VALUE
     * @return Long 自增后返回值
     */
    public Long hIncrement(String key,Object hashkey,long value){
        return hashOperations.increment(key, hashkey,value);
    }

    /**
     * hincrement key value 操作
     * @param key KEY
     * @param value VALUE
     * @return Double 自增后返回值
     */
    public Double hIncrement(String key,Object hashkey,double value){
        return hashOperations.increment(key,hashkey, value);
    }

    /**
     * hPut nx key hashKey value 操作
     * @param key 大KEY
     * @param hashKey hashKEY
     * @param hashValue hashVALUE
     * @return true/false
     */
    public Boolean hPutIfAbsent(String key,Object hashKey,Object hashValue){
        return hashOperations.putIfAbsent(key, hashKey, hashValue);
    }

    /**
     * hGet key hashKEY  操作
     * @param key 大KEY
     * @param hashKey hashKEY
     * @return Object hash值
     */
    public Object hGet(String key,Object hashKey){
        return hashOperations.get(key, hashKey);
    }

    /**
     * hdelete key [hashkey ...] 操作
     * @param key KEY
     * @param hashKeys hashKEYS
     * @return 删除的条数
     */
    public Long hDelete(String key,Object... hashKeys){
        Long delete = hashOperations.delete(key, hashKeys);
        return delete;
    }

    /**
     * 判断是否包含hashkey
     * @param key KEY
     * @param hashKeys hashKEY
     * @return true/false
     */
    public Boolean hasKey(String key,Object hashKeys){
        return hashOperations.hasKey(key, hashKeys);
    }

    /**
     * 获取KEY的Map结构
     * @param key KEY
     * @return 哈希Map结构
     */
    public Map<Object, Object> entries(String key){
        return hashOperations.entries(key);
    }

    /**
     * 获得hashkey的容量
     * @param key KEY
     * @return hashKEY集合
     */
    public Set<Object> hKeys(String key){
        return hashOperations.keys(key);
    }

    /**
     * 获得hashkey的容量
     * @param key KEY
     * @return key的hashKEY容量
     */
    public Long hSize(String key){
        return hashOperations.size(key);
    }

    /**
     * 获得所有的值
     * @param key KEY
     * @return key的值集合
     */
    public List<Object> hValues(String key){
        return hashOperations.values(key);
    }

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
