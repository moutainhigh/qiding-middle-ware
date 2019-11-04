package com.qiding.demo.scenes.subpub;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;

public interface PublishMBean {
     void publish(String key,String value) throws Exception;
     String getValue(String key) throws Exception;
     String childrenNames();
}
