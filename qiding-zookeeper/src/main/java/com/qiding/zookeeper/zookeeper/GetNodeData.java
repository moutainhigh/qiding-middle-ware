package com.qiding.test.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

//异步获取数据
//数据变更获取新数据


class NodeDataCallBack implements AsyncCallback.DataCallback{
    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        System.out.println("异步:["+rc+path+ctx+"]");
        System.out.print("异步:状态值:"+stat);
        System.out.println("异步:服务器值:"+new String(data));
    }
}


public class GetNodeData implements Watcher {

    public static final CountDownLatch countDownLath=new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper=new ZooKeeper("127.0.0.1:2181",100,new GetNodeData());
        //同步操作
        Stat stat=new Stat();
        syncGetData(zooKeeper,stat);

        //异步操作
        AsyncCallback.DataCallback dataCallback=new NodeDataCallBack();
        asyncGetData(zooKeeper,dataCallback);

        TimeUnit.SECONDS.sleep(200);
    }

    public static String syncGetData(ZooKeeper zooKeeper,Stat stat) {
        try {
            byte[]bytes= zooKeeper.getData("/zk-book",event -> {
                if(event.getState()== Event.KeeperState.SyncConnected&&event.getType()== Event.EventType.NodeDataChanged){
                    syncGetData(zooKeeper,stat);
                }
            },stat);
            System.out.print("状态值:"+stat);
            System.out.println("服务器值:"+new String(bytes));
            return new String(bytes);
        }catch (Exception e){
           return null;
        }
    }


    public static void asyncGetData(ZooKeeper zooKeeper, AsyncCallback.DataCallback dataCallback){
        zooKeeper.getData("/zk-book",event -> {
            asyncGetData(zooKeeper,dataCallback);
        },dataCallback,"hello world context");

    }






    @Override
    public void process(WatchedEvent event) {

    }
}
