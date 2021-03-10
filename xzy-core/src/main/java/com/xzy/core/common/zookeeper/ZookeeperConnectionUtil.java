package com.xzy.core.common.zookeeper;

import com.xzy.core.common.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author 🐻子渊
 * zk连接工具类
 */
@Slf4j
public class ZookeeperConnectionUtil {

    private static String HOST;
    private static int ZKTimeOut;

    private static ZooKeeper zooKeeper;

    static {
        getZooKeeper();
    }
    private static CountDownLatch countDownLatch ;


    /**
     * 获取zookeeper连接 使用饿汉式单例模式实现
     * 连接超时会进行自动重连
     * @return ZooKeeper
     */
    public static ZooKeeper getZooKeeper(){
        try {
           if(zooKeeper == null || !zooKeeper.getState().isAlive() || !zooKeeper.getState().isConnected()){
               countDownLatch = new CountDownLatch(1);
               zooKeeper = new ZooKeeper(HOST, ZKTimeOut, new ZkConnectionWatcher());
               countDownLatch.await(5, TimeUnit.SECONDS);
           }
        }catch (Exception e){
            throw new AppException("zookeeper连接失败："+e);
        }
        return zooKeeper;
    }

    /**
     * zookeeper连接watcher静态内部类
     */
    static class ZkConnectionWatcher implements Watcher{

        @Override
        public void process(WatchedEvent watchedEvent) {
            log.info("zookeeper状态发生变化" + watchedEvent);
            if(Objects.equals(watchedEvent.getType(),Event.EventType.None)){
                //连接状态变化
                switch (watchedEvent.getState()){
                    case SyncConnected:
                        log.info("zookeeper连接成功");
                        countDownLatch.countDown();
                    case Expired:
                        log.error("zookeeper连接超时");
                        ZookeeperConnectionUtil.getZooKeeper();
                    case AuthFailed:log.error("zookeeper连接认证失败");
                    default:log.info("zookeeper连接未成功");
                }
            }
        }
    }

    public static void closeZookeeper(){
        closeZookeeper(0L);
    }

    public static void closeZookeeper(long waitTime){
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            throw new AppException("zookeeper关闭失败："+e);
        }
    }
}
