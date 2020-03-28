package com.qiding.test.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.TimeUnit;

/**
 * 分布式计数器
 */
public class DistributeCount {
    static final String zkServer="127.0.0.1:2181";
    static  final String countPath="/curator_count_path";

    public static void main(String[] args) throws Exception {
        CuratorFramework client= CuratorFrameworkFactory.builder()
                .connectString(zkServer)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .sessionTimeoutMs(5000)
                .build();
        client.start();




        DistributedAtomicInteger atomicInteger=
                new DistributedAtomicInteger(client,countPath,new RetryNTimes(3,1000));

        AtomicValue<Integer> value=atomicInteger.add(7);

        if(value.succeeded()){
            System.out.println(value.postValue());
        }
        TimeUnit.SECONDS.sleep(100);
    }
}
