package com.qiding.test.scenes.subpub;

import java.io.IOException;

public interface SubscribeMBean {
     void watcher(String node) throws Exception;
     String getData(String key);
     public void close() throws IOException;
}
