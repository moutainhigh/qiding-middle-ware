package com.qiding.test.scenes.subpub;

public interface PublishMBean {
     void publish(String key,String value) throws Exception;
     String getValue(String key) throws Exception;
     String childrenNames();
}
