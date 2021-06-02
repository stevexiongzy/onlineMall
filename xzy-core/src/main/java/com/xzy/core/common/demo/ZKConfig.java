package com.xzy.core.common.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zookeeper配置类
 * @author xzy
 */
@Configuration
@Slf4j
public class ZKConfig {

    @Value("${zk.hostname:localhost}")
    private String zkHostName;

//    @Bean
    public CuratorFramework curatorFramework(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                //连接ip 端口号 ，集群使用逗号隔开
                .connectString("192.168.183.186:2181")
                //连接会话超时时间
                .sessionTimeoutMs(5000)
                //zk连接失效后重连机制 一直重新连接
                .retryPolicy(new RetryOneTime(30))
                .build();
        curatorFramework.start();
        return curatorFramework;
    }
}
