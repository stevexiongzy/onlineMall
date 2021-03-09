package com.xzy.core.common.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
                if(Objects.equals(watchedEvent.getState(), Event.KeeperState.SyncConnected)){
                    log.info("zookeeper对象客户端连接成功"+ watchedEvent);
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await(6, TimeUnit.SECONDS);
        return zooKeeper;
    }
}
