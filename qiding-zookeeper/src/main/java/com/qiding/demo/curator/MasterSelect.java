package com.qiding.demo.curator;

import com.google.gson.Gson;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class MasterSelect {

    static final String zkServer="127.0.0.1:2181";

    static  final String masterPath="/curator_master_select";

    static CyclicBarrier cyclicBarrier=new CyclicBarrier(4);
    static CuratorFramework getClient(){
           return CuratorFrameworkFactory.builder()
                    .connectString(zkServer)
                    .retryPolicy(new ExponentialBackoffRetry(1000,3))
                    .sessionTimeoutMs(5000)
                    .build();};

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        //1。声明4个线程
        Thread threada=new Thread(MasterSelect::getMaster,"client-a");
        Thread threadb=new Thread(MasterSelect::getMaster,"client-b");
        Thread threadc=new Thread(MasterSelect::getMaster,"client-c");

        threada.start();
        System.out.println("thread a start");
        threadb.start();
        System.out.println("thread b start");
        threadc.start();
        System.out.println("thread c start");

        cyclicBarrier.await();

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    static void  getMaster() {

        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        String threadName=Thread.currentThread().getName();
        System.out.println("开始选举");
        CuratorFramework client=getClient();
        client.start();
        LeaderSelector leaderSelector=new LeaderSelector(client, masterPath, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                System.out.println("master==current=="+threadName);
                Thread.sleep(50000);
                System.out.println("release master");

            }
        });
        leaderSelector.autoRequeue();
        leaderSelector.start();
        try {
            System.out.println(new Gson().toJson(leaderSelector.getParticipants()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
