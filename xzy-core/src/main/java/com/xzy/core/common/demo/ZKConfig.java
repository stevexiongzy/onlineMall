package com.xzy.core.common.demo;

import cn.hutool.aop.interceptor.JdkInterceptor;
import com.xzy.core.common.exception.AppException;
import lombok.extern.slf4j.Slf4j;
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
    public ZooKeeper zooKeeper() throws InterruptedException, IOException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("192.168.183.186:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                log.info("触发watch" + watchedEvent);
                if(Objects.equals(watchedEvent.getType(), Event.EventType.None)) {
                //事件类型为空，只判断客户端之间的连接状态信息
                    if (Objects.equals(watchedEvent.getState(), Event.KeeperState.SyncConnected)) {
                        log.info("zookeeper客户端连接成功:" + watchedEvent);
                        countDownLatch.countDown();
                    }else if(Objects.equals(watchedEvent.getState(), Event.KeeperState.AuthFailed)){
                        throw new AppException("zookeeper客户端连接认证失败"+watchedEvent);
                    }else if(Objects.equals(watchedEvent.getState(), Event.KeeperState.Expired)){

                    }
                }
            }
        });
        countDownLatch.await(6, TimeUnit.SECONDS);
        return zooKeeper;
    }
}
