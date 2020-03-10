package com.qiding.demo.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class IStringCallBack implements AsyncCallback.StringCallback{
    @Override
    public void processResult(int rs, String path, Object ctx, String name) {
        System.out.println("["+rs+path+ctx+name+"]");
    }
}


public class AsyncCreateNode implements Watcher {
    public static final CountDownLatch downLatch=new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {

        IStringCallBack stringCallBack=new IStringCallBack();

        ZooKeeper zooKeeper=new ZooKeeper("127.0.0.1:2181",100,new AsyncCreateNode());
        System.out.println("开始链接"+zooKeeper.getState());
        downLatch.await();

        zooKeeper.create("/qiding-async","hello-world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,stringCallBack,"i am context");
        zooKeeper.create("/qiding-async","hello-world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,stringCallBack,"i am context");
        zooKeeper.create("/qiding-async","hello-world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,stringCallBack,"i am context");

        TimeUnit.SECONDS.sleep(10);




    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
        if(Event.KeeperState.SyncConnected==watchedEvent.getState()){
            downLatch.countDown();
        }
    }
}
