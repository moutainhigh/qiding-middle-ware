package com.qiding.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.net.SocketTimeoutException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class DistributeBarrier {
    static final String zkServer="127.0.0.1:2181";
    static  final String barrierPath="/curator_barrier_path";
   static  DistributedBarrier barrier;



    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .sessionTimeoutMs(300)
                .connectString(zkServer)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .build();
        client.start();
//        barrier(client);
        barrier(client,10);
    }


    public static void barrier(CuratorFramework client) throws Exception {

        for (int i=0;i<10;i++){
            new Thread(()->{
                 barrier=new DistributedBarrier(client,barrierPath);
                try {
                    barrier.setBarrier();
                    System.out.println("设置成功"+Thread.currentThread().getName()+":"+ LocalDateTime.now().toString());
                    barrier.waitOnBarrier();
                    System.out.println("....启动"+LocalDateTime.now().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(20);
        barrier.removeBarrier();
        System.out.println(Instant.now().toEpochMilli());
    }

    public static void barrier(CuratorFramework client,int count){
        DistributedDoubleBarrier doubleBarrier;

        for(int i=0;i<count/2;i++){
            doubleBarrier=new DistributedDoubleBarrier(client,barrierPath,count);
            DistributedDoubleBarrier finalDoubleBarrier = doubleBarrier;
            new Thread(()->{
                try {
                    finalDoubleBarrier.enter();
                    System.out.println("enter for signal");
                    TimeUnit.SECONDS.sleep(5);
                    finalDoubleBarrier.leave();
                    System.out.println("start process: end");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
