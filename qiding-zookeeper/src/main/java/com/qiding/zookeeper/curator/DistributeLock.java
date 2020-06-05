package com.qiding.test.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;

public class DistributeLock {
    static final String zkServer="127.0.0.1:2181";
    static  final String lockPath="/curator_lock_path";
    public static void main(String[] args) {
        CuratorFramework client= CuratorFrameworkFactory.builder()
                .connectString(zkServer)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .build();

        client.start();



        CountDownLatch countDownLatch=new CountDownLatch(1);
        final InterProcessMutex lock=new InterProcessMutex(client,lockPath);


        for(int i=0;i<10;i++){
            new Thread(()->{
                try {
                    countDownLatch.await();
                    lock.acquire();
                    System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS").format(LocalDateTime.now()));
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
        countDownLatch.countDown();
    }
}
