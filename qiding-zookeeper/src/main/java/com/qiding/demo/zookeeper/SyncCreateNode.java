package com.qiding.demo.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class SyncCreateNode implements Watcher {

    public static final CountDownLatch countDown=new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper=new ZooKeeper("127.0.0.1:2181",1000,new SyncCreateNode());
        System.out.println(zooKeeper.getState());
        countDown.await();

        //
       String node1=zooKeeper.create("/qiding","hell".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
       System.out.println("create path"+node1);

       String node2=zooKeeper.create("/qiding","hell".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
       System.out.println("create path"+node2);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
       if(Event.KeeperState.SyncConnected==watchedEvent.getState()){
           countDown.countDown();
       }
    }
}
