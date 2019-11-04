package com.qiding.demo.scenes.lbs;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class AbstractZkClient {

    CuratorFramework zkClient;

    public AbstractZkClient(String zkServer,String appName){
        zkClient= CuratorFrameworkFactory.builder()
                .sessionTimeoutMs(4000)
                .connectString(zkServer)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .namespace(appName)
                .build();
        zkClient.start();
    }

}
