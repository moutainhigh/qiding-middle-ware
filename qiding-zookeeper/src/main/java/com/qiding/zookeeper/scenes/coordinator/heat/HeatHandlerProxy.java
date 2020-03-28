package com.qiding.test.scenes.coordinator.heat;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import java.util.List;
import java.util.stream.Collectors;

public class HeatHandlerProxy {
    HeatHandler heatHandler;

    PathChildrenCache pathChildrenCache;

    public HeatHandlerProxy(HeatHandler heatHandler,PathChildrenCache pathChildrenCache) {
        this.heatHandler = heatHandler;
        this.pathChildrenCache=pathChildrenCache;
    }

    public void hand(){
     List list=   pathChildrenCache.getCurrentData().parallelStream()
                .filter(childData -> {

                   String path= childData.getPath();

                   Integer sequeceId=Integer.valueOf(path.substring(path.lastIndexOf("/"))+1);

                   return sequeceId<heatHandler.getSequenceId();
                }).collect(Collectors.toList());

     if(list.isEmpty()){
         heatHandler.hand();
     }
    }



}
