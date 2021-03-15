package com.xzy.core.common.demo;

import cn.hutool.aop.interceptor.JdkInterceptor;
import com.xzy.core.common.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper配置类
 * @author xzy
 */
@Configuration
@Slf4j
public class ZKConfig {

    @Value("${zk.hostname:localhost}")
    private String zkHostName;

    @Bean
    public CuratorFramework curatorFramework(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                //连接ip 端口号 ，集群使用逗号隔开
                .connectString("192.168.239.128:2181")
                //连接会话超时时间
                .sessionTimeoutMs(5000)
                //zk连接失效后重连机制 一直重新连接
                .retryPolicy(new RetryForever(5000))
                .build();
        curatorFramework.start();
        return curatorFramework;
    }
}
