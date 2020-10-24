package com.qiding.multi.thread;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {


        SettableFuture<Void> future = SettableFuture.create();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.cancel(true);

            System.out.println("任务1完成");

        }).start();

        SettableFuture<Void> future2 = SettableFuture.create();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.cancel(true);
            System.out.println("任务2完成");

        }).start();


        Futures.whenAllComplete(Arrays.asList(future,future2)).run(() -> {
            System.out.println("hello world");
            System.out.println("总任务完成");
        }, MoreExecutors.directExecutor());


    }
}
