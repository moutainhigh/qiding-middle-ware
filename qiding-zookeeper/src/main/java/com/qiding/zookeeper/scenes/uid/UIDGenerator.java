package com.qiding.test.scenes.uid;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

public class UIDGenerator {
    private CuratorFramework curatorFramework;
    private String prefix;
    public UIDGenerator(String address,String appName,String prefix){
        curatorFramework= CuratorFrameworkFactory.builder()
        .connectString(address)
        .namespace(appName)
        .retryPolicy(new ExponentialBackoffRetry(1000,3))
        .sessionTimeoutMs(5000)
        .build();
        curatorFramework.start();
        this.prefix=prefix;
    }

    public String generator() throws Exception {
        return curatorFramework.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/"+prefix);
    }

    public static void main(String[] args) throws Exception {
        String address="127.0.0.1:2181";
        String appName="qiding-generator";
        String prefix="job2";
        UIDGenerator uidGenerator=new UIDGenerator(address,appName,prefix);
        for(int i=0;i<10;i++){
            System.out.println(uidGenerator.generator());
        }
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
