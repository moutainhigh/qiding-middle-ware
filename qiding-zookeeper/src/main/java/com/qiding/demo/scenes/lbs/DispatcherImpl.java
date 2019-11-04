package com.qiding.demo.scenes.lbs;

import com.google.common.collect.ImmutableMultimap;
import org.apache.curator.framework.recipes.cache.NodeCache;

import java.io.File;
import java.util.Currency;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DispatcherImpl extends AbstractZkClient implements Dispatcher {


    Map<String,NodeCache> nodeCacheMap=new ConcurrentHashMap<>();

    public DispatcherImpl(String zkServer, String appName) {
        super(zkServer, appName);

    }

    @Override
    public String dispatcher(String appName, String domain) {
        if(nodeCacheMap.containsKey(appName+ File.separator+domain)){

        }
    }
}
