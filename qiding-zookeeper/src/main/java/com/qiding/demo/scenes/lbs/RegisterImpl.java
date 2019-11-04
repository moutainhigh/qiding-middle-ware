package com.qiding.demo.scenes.lbs;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class RegisterImpl  extends AbstractZkClient implements Register {

    public RegisterImpl(String zkServer, String appName) throws Exception {
        super(zkServer, appName);
    }

    @Override
    public void register(String domainName, String ipAddress) throws Exception {
        //使用锁实现
        InterProcessMutex interProcessMutex=new InterProcessMutex(zkClient,domainName);
        interProcessMutex.acquire();
        Stat stat= zkClient.checkExists().forPath(domainName);
        if(stat==null){
            zkClient.create().withMode(CreateMode.PERSISTENT).
        }
        interProcessMutex.release();


    }
}
