package com.qiding.demo.scenes.subpub;

import com.google.gson.Gson;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Publish implements PublishMBean {

    private CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("127.0.0.1:2181")
            .sessionTimeoutMs(3000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    PathChildrenCache childrenCache;

    PublishBean publishBean = new PublishBean();

    Field[] fields = PublishBean.class.getDeclaredFields();
    Method[] methods = PublishBean.class.getDeclaredMethods();

    private String pathName;

    public Publish(String pathName) throws Exception {
        client.start();

        publishBean.setAge("1111");
        publishBean.setName("qiding");
        this.pathName = pathName;

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteStream);
        outputStream.writeObject(publishBean);

        Stat stat = client.checkExists().forPath(pathName);
        if (stat == null) {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(pathName, this.getData());
        }

        //监控节点
        childrenCache = new PathChildrenCache(client, pathName, true);
        childrenCache.start(true);
    }

    @Override
    public void publish(String key, String value) throws Exception {
        for (Field field : fields) {
            if (field.getName().equals(key)) {
                for (Method method : methods) {
                    if (method.getName().toLowerCase().equals("set" + key)) {
                        method.invoke(publishBean, value);
                    }
                }
            }
        }
        client.setData().forPath(pathName, getData());
    }

    @Override
    public String getValue(String key) throws Exception {
        for (Field field : fields) {
            if (field.getName().equals(key)) {
                for (Method method : methods) {
                    if (method.getName().toLowerCase().equals("get" + key)) {
                        return (String) method.invoke(publishBean);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String childrenNames() {
        final List<ChildData> childList = childrenCache.getCurrentData();
        if (childList == null || childList.isEmpty()) {
            return "";
        }
        return new Gson().toJson(childList.parallelStream().map(childData -> new String(childData.getData())).collect(Collectors.toList()));
    }

    public byte[] getData() {
        try {
            try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                 ObjectOutputStream outputStream = new ObjectOutputStream(byteStream);) {
                outputStream.writeObject(publishBean);
                return byteStream.toByteArray();
            } finally {

            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
