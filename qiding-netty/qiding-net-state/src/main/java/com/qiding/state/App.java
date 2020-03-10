package com.qiding.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World!" );

		CountDownLatch count=new CountDownLatch(1);
		//count.countDown();
		System.out.println(count.await(10, TimeUnit.SECONDS));


    }
}
