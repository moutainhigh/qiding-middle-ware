package com.qiding.test.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class CreateSession {
    public static void main(String[] args) {
        String zkServer = "127.0.0.1:2181";
        //创建链接
        ZkClient zkClient = new ZkClient(zkServer, 500);


        System.out.println("链接成功");
        //创建 临时节点
        String path = "/zk-book-temp";
        createEphemeral(zkServer, path);
        //临时 持久节点
        path="/zk-client/2000";
        createPersistent(zkServer,path);
    }

    public static void createEphemeral(String zkServer, String path) {
        ZkClient zkClient = new ZkClient(zkServer, 500);
        zkClient.createEphemeral(path, 200);
        zkClient.createEphemeralSequential(path, 300);
        String pathResult = zkClient.createEphemeralSequential(path, 200);
        System.out.println(pathResult);
    }

    public static void createPersistent(String zkServer, String path) {
        ZkClient zkClient = new ZkClient(zkServer, 500);
        zkClient.createPersistent(path, true);
        String path2 = zkClient.createEphemeralSequential(path, true);
        System.out.println(path2);
    }


}
