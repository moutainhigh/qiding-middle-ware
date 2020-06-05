package com.qiding.test.scenes.lbs;


import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicValue;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class RegisterImpl  extends AbstractZkClient implements Register {

    public RegisterImpl(String zkServer, String appName) throws Exception {
        super(zkServer, appName);
    }

    @Override
    public void register(String domainName, String ipAddress) throws Exception {
        //基于锁实现
        //registerWithLock(domainName,ipAddress);
        //cas实现
         registerWithCAS(domainName,ipAddress);
    }

    public void registerWithLock(String domainName, String ipAddress) throws Exception {
        //使用锁实现
        InterProcessMutex interProcessMutex=new InterProcessMutex(zkClient,domainName+"lock");
        interProcessMutex.acquire();
        Stat stat= zkClient.checkExists().forPath(domainName);

        if(stat==null){
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(domainName,ipAddress.getBytes());
        }else{
            String oldData= new String(zkClient.getData().forPath(domainName));
            zkClient.setData().forPath(domainName,((oldData+","+ipAddress).getBytes()));
        }
        interProcessMutex.release();
    }

    //使用cas
    public void registerWithCAS(String domainName, String ipAddress) throws Exception {
        DistributedAtomicValue atomicValue=new DistributedAtomicValue(zkClient,domainName,new ExponentialBackoffRetry(1000,3));
        while (true){
            StringBuilder sb=new StringBuilder();
            byte[]oldValue= atomicValue.get().postValue();
            sb.append(ipAddress);

            if(oldValue!=null){
                sb.append(",").append(new String(oldValue));
                AtomicValue<byte[]> result= atomicValue.compareAndSet(oldValue,sb.toString().getBytes());
                if(result.succeeded()){
                    return;
                }
                continue;
            }

            if(atomicValue.initialize(sb.toString().getBytes())){
                return;
            }
        }
    }

}
