package com.qiding.test.curator;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.*;


public class Curator_session {
    public static void main(String[] args) throws Exception {
        asyncMethod();

    }

    public static  void syncMethod() throws Exception {
        String connectString="127.0.0.1:2181";

        RetryPolicy retryPolicy=new ExponentialBackoffRetry(10000,3);

        //1。创建会话
        CuratorFramework client=  CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();

        //创建节点
        String creatPath="/curator/003";
        creatPath=client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                .forPath(creatPath,"qiding".getBytes());


        Stat stat= client.checkExists()
                .forPath(creatPath);


        if(stat==null){
            return;
        }
        client.setData().withVersion(stat.getVersion()).forPath(creatPath,"qidingp-hello".getBytes());

        byte[]data= client.getData().storingStatIn(stat).forPath(creatPath);
        System.out.println(stat+new String(data));

        client.delete()
                .guaranteed()
                .deletingChildrenIfNeeded()
                .withVersion(stat.getVersion())
                .forPath(creatPath);


        stat= client.checkExists()
                .forPath(creatPath);

        if(stat==null){
            System.out.println(creatPath+"not exists");
        }


        //指定命名空间
        CuratorFramework client2=  CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("/qiding")
                .build();
        client.start();


        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void asyncMethod() throws Exception {
        String connectString="127.0.0.1:2181";
        CuratorFramework client=CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(100,3))
                .namespace("zk-book")
                .build();
        client.start();

        CountDownLatch countDownLatch=new CountDownLatch(1);
        ExecutorService tb= Executors.newFixedThreadPool(2);


        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        System.out.println(event.getResultCode());
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(event.getContext());
                        countDownLatch.countDown();
                    }
                },"qiding",tb)
                .forPath("/qiding/001","qiding-hello-world".getBytes());
        System.out.println("等待结果");
        countDownLatch.await();

        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        System.out.println(event.getResultCode());
                        System.out.println(Thread.currentThread().getName());
                        countDownLatch.countDown();
                    }
                })
                .forPath("/qiding/001","qiding-hello-world".getBytes());

      TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

}
