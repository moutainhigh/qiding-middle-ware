package com.qiding.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World!" );

        KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer =kieServices.getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("all-rules");
	   final HelloWorld helloWorld=	HelloWorld.builder().age(100).name("qi").gone(false).build();
		BlockingQueue<HelloWorld> blockingQueue=new ArrayBlockingQueue<HelloWorld>(3);

		new Thread(()->{
//			HelloWorld input= null;
//			try {
//				input = blockingQueue.take();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			kieSession.insert(helloWorld);
			kieSession.fireAllRules();
		}).start();

		TimeUnit.SECONDS.sleep(5);
		helloWorld.setGone(false);
		kieSession.fireAllRules();
		System.out.println("更新状态成功");


		TimeUnit.SECONDS.sleep(6);

		HelloWorld	helloWorld2=	HelloWorld.builder().age(100).name("ding").gone(false).build();
		kieSession.insert(helloWorld2);
		kieSession.fireAllRules();

		TimeUnit.SECONDS.sleep(7);


    }
}
