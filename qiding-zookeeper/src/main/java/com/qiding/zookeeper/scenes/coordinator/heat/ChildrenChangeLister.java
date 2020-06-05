package com.qiding.test.scenes.coordinator.heat;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

public class ChildrenChangeLister implements PathChildrenCacheListener {

    private HeatHandlerProxy heatHandler;

    public ChildrenChangeLister(HeatHandlerProxy heatHandler) {
        this.heatHandler = heatHandler;
    }

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
         if(event.getType()== PathChildrenCacheEvent.Type.CHILD_REMOVED){
             heatHandler.hand();
         }
    }



}
