package com.qiding.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NodeCacheDemo {
    public static void main(String[] args) throws Exception {
        String connectString="127.0.0.1:2181";
        AtomicInteger count=new AtomicInteger();
        CuratorFramework client= CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(100,3))
                .namespace("qiding")
                .build();

        client.start();

       String path=client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/3333/002","qiuding-hello-world".getBytes());

        System.out.println(new String(client.getData().forPath(path)));

        //监控节点
         final NodeCache nodeCache=new NodeCache(client,path,false);
         nodeCache.start(true);
         System.out.println("旧数据："+new String(nodeCache.getCurrentData().getData()));
         nodeCache.getListenable().addListener(()->{
             System.out.println("new数据："+new String(nodeCache.getCurrentData().getData()));
             System.out.println("count:"+count.incrementAndGet());
             System.out.println("thread:"+Thread.currentThread().getName());
         });


        Stat stat=new Stat();
        byte[]nodeData= client.getData().storingStatIn(stat).forPath(path);
        System.out.println("now:"+new String(nodeData));

        client.setData().withVersion(stat.getVersion()).forPath(path,"1000000".getBytes());

        client.delete().deletingChildrenIfNeeded().forPath(path);


        path=client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path,"qiuding-hello-world".getBytes());


        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);

    }
}
