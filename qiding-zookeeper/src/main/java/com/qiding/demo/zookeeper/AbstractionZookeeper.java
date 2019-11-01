package com.qiding.demo.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

public abstract class  AbstractionZookeeper  implements Watcher {

    protected CountDownLatch countDownLatch=new CountDownLatch(1);

    @Override
    public void process(WatchedEvent watchedEvent) {
        countDownLatch.countDown();
    }
}
