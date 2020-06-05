package com.qiding.test.scenes.lbs;

import com.google.gson.Gson;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class AbstractZkClient {

    protected  Gson gson=new Gson();
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
