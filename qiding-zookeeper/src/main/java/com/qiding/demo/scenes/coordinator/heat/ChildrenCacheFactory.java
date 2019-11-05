package com.qiding.demo.scenes.coordinator.heat;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.listen.StandardListenerManager;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;

@SuppressWarnings("ALL")
public class ChildrenCacheFactory {
    private ChildrenChangeLister childrenChangeLister;
    private CuratorFramework zkClient;
    private String watchNode;


    public ChildrenCacheFactory(ChildrenChangeLister childrenChangeLister, CuratorFramework zkClient, String watchNode) {
        this.childrenChangeLister = childrenChangeLister;
        this.zkClient = zkClient;
        this.watchNode = watchNode;
    }


    public PathChildrenCache create() throws Exception {
        PathChildrenCache pathChildrenCache=new PathChildrenCache(zkClient,watchNode,true);
        StandardListenerManager<ChildrenChangeLister> listenerManager=   StandardListenerManager.standard();
        listenerManager.addListener(childrenChangeLister);
        pathChildrenCache.getListenable().addListener(childrenChangeLister);
        pathChildrenCache.start();
        return pathChildrenCache;
    }
}
