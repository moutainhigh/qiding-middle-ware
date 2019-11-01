package com.qiding.demo.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

//创建会话
public class CreateSession implements Watcher {

   public static final CountDownLatch countDownLatch=new CountDownLatch(1);

    public static final Semaphore semaphore=new Semaphore(1);

    public static final CyclicBarrier cyclicBarrier=new CyclicBarrier(2);


    public static void main(String[] args) throws IOException, InterruptedException, BrokenBarrierException {
        //semaphore.acquire();
        ZooKeeper zooKeeper=new ZooKeeper("127.0.0.1:2181",100,new CreateSession());
        System.out.println(zooKeeper.getState());

        cyclicBarrier.await();


        //semaphore.acquire();
       // cyclicBarrier.await()
        System.out.println("session 链接成功");


      //  countDownLatch.await();



        Long sessionId=zooKeeper.getSessionId();
        byte[]password=zooKeeper.getSessionPasswd();

        zooKeeper=new ZooKeeper("127.0.0.1:2181",100,new CreateSession(),sessionId,password);
        System.out.println(zooKeeper.getState());

        cyclicBarrier.reset();
        cyclicBarrier.await();
        //semaphore.acquire();

        //countDownLatch.await();
        System.out.println("链接成功");
        System.out.println(zooKeeper.getState());
    }







    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
        if(Event.KeeperState.SyncConnected==watchedEvent.getState()){
           // countDownLatch.countDown();
           // cyclicBarrier.await();
           // semaphore.release();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
