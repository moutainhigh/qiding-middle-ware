package com.qiding.demo.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class AclControl implements Watcher {

    public static final CountDownLatch countDownLatch=new CountDownLatch(1);
    public static final String server="127.0.0.1:2181";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException, BrokenBarrierException {

        String path="/zk-book2";

        //1. start session
        ZooKeeper zooKeeper=new ZooKeeper(server,200,new AclControl());
        countDownLatch.countDown();
        //2. auth
        zooKeeper.addAuthInfo("digest","qiding".getBytes());
        //3. create node
       String realPath=zooKeeper.create(path,"qiding hello".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
       String path2=zooKeeper.create(realPath+"/1","qiding hello".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL_SEQUENTIAL);
       System.out.println(path2);
        //4.new session
        CyclicBarrier cyclicBarrier=new CyclicBarrier(2);
        ZooKeeper  zooKeeper2=new ZooKeeper(server,200,event -> {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
       // zooKeeper2.addAuthInfo();
        zooKeeper2.addAuthInfo("digest","qiding".getBytes());

        cyclicBarrier.await();
        //5. visist
        Stat stat=new Stat();
        byte[]data= zooKeeper2.getData(path,(event -> {
            System.out.println(event);
        }),stat);
        System.out.println(new String(data));

        TimeUnit.SECONDS.sleep(300);

    }








    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }
}
