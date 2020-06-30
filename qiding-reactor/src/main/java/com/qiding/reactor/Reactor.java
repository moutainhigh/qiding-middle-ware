package com.qiding.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Reactor {


	private final MessageDequeue messageDequeue;

	public Reactor(MessageDequeue messageDequeue) {
		this.messageDequeue = messageDequeue;
	}

	public void start(){
		Scheduler remoteDeliveryScheduler = Schedulers.newParallel("");
		Flux.from(messageDequeue.getFlux())
			.publishOn(remoteDeliveryScheduler)
			.flatMap(this::runStep)
			.onErrorContinue(((throwable, nothing) -> System.out.println("Exception caught in RemoteDelivery")))
			.subscribeOn(remoteDeliveryScheduler)
			.subscribe();
	}

	private Mono<Void> runStep(UserMessage userMessage) {
		try {
			System.out.println(Thread.currentThread());
			TimeUnit.SECONDS.sleep(3);
//			if(((ReactorMessage)userMessage).getObject()%2==0){
//				System.out.println("服务异常");
//				throw new RuntimeException("error exception");
//			}
			return Mono.empty();
		}catch (Exception e){
			e.printStackTrace();
			return Mono.error(e);
		}
	}

	public static void main(String[] args) {

		ReactorMessageDecorateFactory decorate=new ReactorMessageDecorate();
		MessageDequeue messageDequeue=new MessageDequeue(decorate);


		int i=0;
		while(i<10){
			messageDequeue.addData(new Random(10).nextInt());
			i++;
		}


//
//		new Thread(()->{
//			while (true){
//				try {
//					TimeUnit.SECONDS.sleep(3);
//
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();

		Reactor reactor=new Reactor(messageDequeue);
		Long start=System.currentTimeMillis();
		reactor.start();
		Long end=System.currentTimeMillis();
		System.out.println(start-end);
	}
}
