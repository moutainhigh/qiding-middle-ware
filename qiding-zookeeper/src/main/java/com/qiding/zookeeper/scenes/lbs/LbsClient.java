package com.qiding.demo.scenes.lbs;

import java.net.Inet4Address;
import java.util.concurrent.CountDownLatch;

public class LbsClient {
    public static void main(String[] args) throws Exception {
        String zkServer="127.0.0.1:2181";
        Register register=new RegisterImpl(zkServer,"qiding");
        Dispatcher dispatcher=new DispatcherImpl(zkServer,"qiding");

        CountDownLatch countDownLatch=new CountDownLatch(3);

     new Thread(()->{
         try {
             register.register("/www.qiding.com", Inet4Address.getLocalHost().getHostAddress());
             countDownLatch.countDown();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }).start();

        new Thread(()->{
            try {
                register.register("/www.qiding.com", "212121212");
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

     new Thread(()->{
         try {
             register.register("/www.qiding2.com", Inet4Address.getLocalHost().getHostAddress());
             countDownLatch.countDown();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }).start();


        countDownLatch.await();

        System.out.println(dispatcher.dispatcher("qiding","/www.qiding.com"));
    }
}
