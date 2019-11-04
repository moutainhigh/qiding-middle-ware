package com.qiding.demo.scenes.subpub;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Subscribe implements SubscribeMBean {

    private String nodeName;
    private NodeCache nodeCache;

    CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("127.0.0.1:2181")
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .sessionTimeoutMs(1000)
            .build();

    public Subscribe(String nodeName) {
        this.nodeName = nodeName;
        this.client.start();
    }

    @Override
    public void watcher(String watchNode) throws Exception {
        //注册节点
        client.create().withMode(CreateMode.EPHEMERAL).forPath(watchNode+ File.separator+this.nodeName,nodeName.getBytes());
        //监控节点
        nodeCache = new NodeCache(client, watchNode, false);
//        nodeCache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//
//            }
//        });
        nodeCache.start(true);
    }

    @Override
    public String getData(String key) {
        byte[] node = nodeCache.getCurrentData().getData();
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(node);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteIn);
            PublishBean publishBean = (PublishBean) objectInputStream.readObject();
            System.out.println(publishBean.getAge());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        nodeCache.close();
        client.close();
    }
}
