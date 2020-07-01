package com.qiding.test.zookeeper;

import com.google.gson.Gson;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


class CallBack implements AsyncCallback.Children2Callback{
    private Stat stat;

    public CallBack(Stat stat) {
        this.stat = stat;
    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        System.out.println("["+rc+path+ctx+new Gson().toJson(children)+"]");
        System.out.println("old stat"+this.stat);
        System.out.println("new stat"+stat);
    }
}


public class ReadWatchNode implements Watcher {

    public static final CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper=new ZooKeeper("127.0.0.1:2181",1000,new ReadWatchNode());
        countDownLatch.await();
        //使用默认watch
        List<String> chilrens = zooKeeper.getChildren("/zk-book",true);

        //使用指定watch
        zooKeeper.getChildren("/zk-book",(event)->{
            System.out.println("独立变化"+event);
        });

        //使用stat
        Stat stat=new Stat();
        zooKeeper.getChildren("/zk-book",(event -> {
            System.out.println("独立变化-stat-"+event+stat);
        }),stat);

        //使用回调
        Stat stat2=new Stat();
        zooKeeper.getChildren("/zk-book",event -> {
            System.out.println("节点变化");
        },new CallBack(stat2),"i am context");
        TimeUnit.SECONDS.sleep(200);
        System.out.println(new Gson().toJson(chilrens));
    }






    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
        if (Event.KeeperState.SyncConnected==event.getState()&&event.getType()== Event.EventType.None){
            countDownLatch.countDown();
        }
        if(Event.KeeperState.SyncConnected==event.getState()&&event.getType()== Event.EventType.NodeDataChanged){
            System.out.println("节点变化");
        }


    }
}
