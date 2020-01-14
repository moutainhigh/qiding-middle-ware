package com.qiding.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void testNotify() throws InterruptedException {

        Integer mutix=Integer.valueOf(-1);
        Thread threadA=new Thread(()->{
            synchronized (mutix){
                try {
                    mutix.wait();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"thread-a");

        threadA.start();
        Thread threadB=new Thread(()->{
            synchronized (mutix){
                try {
                    mutix.wait();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"thread-b"
        );
        threadB.start();

        TimeUnit.SECONDS.sleep(300);


        synchronized (mutix){
             mutix.notifyAll();
        }


    }




}
