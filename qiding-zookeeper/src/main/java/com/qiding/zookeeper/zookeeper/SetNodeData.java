package com.qiding.test.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

//设置 更新节点 值  version -1 基于最新的version修改值
public class SetNodeData implements Watcher {
    @Override
    public void process(WatchedEvent event) {

    }
}
