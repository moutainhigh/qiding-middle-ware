package com.qiding.demo.scenes.coordinator.heat;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkClientFactory {
    private String zkServer;
    private String nameSpace;

    public ZkClientFactory(String zkServer, String nameSpace) {
        this.zkServer = zkServer;
        this.nameSpace = nameSpace;
    }

    public CuratorFramework create(){
        CuratorFramework client=  CuratorFrameworkFactory.builder()
                .connectString(zkServer)
                .namespace(nameSpace)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .build();
        client.start();
        return client;
    }
}
