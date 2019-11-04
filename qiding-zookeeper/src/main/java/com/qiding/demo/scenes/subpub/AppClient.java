package com.qiding.demo.scenes.subpub;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class AppClient {
    public static void main(String[] args) throws Exception {
        String configPath="/qiding/app1";

        Publish publishClient=new Publish("/qiding/app1");

        Subscribe subClient1=new Subscribe("node1");
        Subscribe subClient2=new Subscribe("node2");


        subClient1.watcher(configPath);
        subClient2.watcher(configPath);

        MBeanServer beanServer= ManagementFactory.getPlatformMBeanServer();
        beanServer.registerMBean(publishClient,new ObjectName("publish:name=publish"));
        beanServer.registerMBean(subClient1,new ObjectName("subClient:name=subClient1"));
        beanServer.registerMBean(subClient2,new ObjectName("subClient:name=subClient2"));

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
