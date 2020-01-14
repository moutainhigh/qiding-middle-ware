package com.qiding.demo.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class DelCallBack implements AsyncCallback.VoidCallback{
    @Override
    public void processResult(int rs, String pathName, Object contxt) {
        System.out.println("["+rs+pathName+contxt+"]");
    }
}


public class DelNode implements Watcher {

    protected static final CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        DelCallBack delCallBack=new DelCallBack();

        ZooKeeper zooKeeper=new ZooKeeper("127.0.0.1:2181",100, new DelNode());
        Stat stat= zooKeeper.setData("/qiding-async","789654".getBytes(),2);
        System.out.println(stat);

        zooKeeper.delete("/qiding-async",3,delCallBack,"i am contxt");
        TimeUnit.SECONDS.sleep(10);
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        if(Event.KeeperState.SyncConnected==watchedEvent.getState()){
            countDownLatch.countDown();
        }
    }
}
