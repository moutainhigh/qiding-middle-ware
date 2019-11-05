package com.qiding.demo.scenes.coordinator.heat;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

public class HeatHandler {
    private Integer sequenceId;
    private CuratorFramework zkClient;
    private String parentNode;

    public Integer getSequenceId() {
        return sequenceId;
    }

    public HeatHandler(String parentNode, CuratorFramework zkClient) throws Exception {
        this.zkClient = zkClient;
        String realPath= zkClient.create().creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(parentNode);
        this.sequenceId=Integer.valueOf(realPath.substring(realPath.lastIndexOf("/")+1));
    }
    public void hand(){
        System.out.println("111111");
    }
}
