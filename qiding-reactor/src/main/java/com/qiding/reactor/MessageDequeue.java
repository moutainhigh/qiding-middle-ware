package com.qiding.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageDequeue {
	public final BlockingQueue<ReactorMessage> blockingQueue = new ArrayBlockingQueue(10);

	public final Flux<UserMessage> flux;

	public Flux<UserMessage> getFlux() {
		return flux;
	}

	public void addData(Integer object){
		try {
			blockingQueue.put(new ReactorMessage("1",object));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}




	public MessageDequeue(ReactorMessageDecorateFactory decorate) {
		flux = Mono.fromCallable(blockingQueue::take)
			.repeat()
			.map(decorate::decorate);
	}



}
