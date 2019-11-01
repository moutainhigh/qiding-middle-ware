package com.qiding.demo.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class ExistsCallBack implements AsyncCallback.StatCallback{
    @Override
    public void processResult(int rs, String path, Object tx, Stat stat) {
        System.out.println("["+rs+path+tx+stat+"]");
    }
}

// 判断节点是否存在
public class NodeExist implements Watcher {


    public static final CountDownLatch countLatch=new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        String server="127.0.0.1:2181";
        int connectionTime=200;
        String path="/zk-book";
        ExistsCallBack callBack=new ExistsCallBack();



        ZooKeeper zooKeeper=new ZooKeeper(server,connectionTime,new NodeExist());
        countLatch.await();

        //sync使用watch
        Stat stat=zooKeeper.exists(path,true);
        if(stat==null){
            System.out.println("not exists");
        }

        Optional<Stat> optionalStat = Optional.ofNullable(zooKeeper.exists(path, watchedEvent -> {
            System.out.println(watchedEvent);
        }));
        optionalStat.ifPresent(stat1 -> {
            System.out.println("exists");
        });

        //使用异步
        zooKeeper.exists(path,true,callBack,"~i am context~");


        syncExists(zooKeeper,path);
        asyncExists(zooKeeper,path,callBack);


        TimeUnit.SECONDS.sleep(100);
    }


    public  static void  syncExists(ZooKeeper zooKeeper,String path) {
        try {
            Stat stat=zooKeeper.exists(path,event -> {
                System.out.println(event);
                syncExists(zooKeeper,path);
            });
        }catch (Exception e){
            System.out.println("失败");
        }
    }


    public static void asyncExists(ZooKeeper zooKeeper, String path, AsyncCallback.StatCallback callBack){
        zooKeeper.exists(path,event -> {
            System.out.println(event);
            asyncExists(zooKeeper,path,callBack);
        },callBack,"~i am context~");

    }




    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
        if(Event.KeeperState.SyncConnected==watchedEvent.getState()){
            switch (watchedEvent.getType()){
                case None:
                    countLatch.countDown();
                break;
            }
        }

    }
}
