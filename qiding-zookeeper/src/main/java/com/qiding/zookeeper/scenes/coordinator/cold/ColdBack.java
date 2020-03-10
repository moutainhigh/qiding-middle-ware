package com.qiding.demo.scenes.coordinator.cold;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import java.util.function.Consumer;

public class ColdBack implements Consumer {
    PathChildrenCache childrenCache;

    @Override
    public void accept(Object o) {

    }
}
