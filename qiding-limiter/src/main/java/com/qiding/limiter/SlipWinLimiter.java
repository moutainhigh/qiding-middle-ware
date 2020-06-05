package com.qiding.limiter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sun.tools.javac.util.Log;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class SlipWinLimiter {

	private static Logger logger= LogManager.getLogManager().getLogger("xxx");

	private static ScheduledExecutorService executorService=  new ScheduledThreadPoolExecutor(5);

	private static LoadingCache<Long, AtomicInteger> counter=
		CacheBuilder.newBuilder()
		.expireAfterAccess(10, TimeUnit.SECONDS)
		.build(new CacheLoader<Long, AtomicInteger>() {
			@Override
			public AtomicInteger load(Long key) throws Exception {
				return new AtomicInteger(0);
			}
		});
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		 long limit = 15;

//		 int i=5;
//		 do {
			 excute(limit);
		// }while (i<0);


//		counter.put(100L,new AtomicInteger(100));
//		System.out.println(counter.get(100L));
//		TimeUnit.SECONDS.sleep(11);
//		System.out.println(counter.get(100L));
	}

	private static void excute(long limit) {
		executorService.scheduleWithFixedDelay(()->{
			Long time=System.currentTimeMillis()/1000;
			int reqs=(int)(Math.random()*5+1);
			try {
				counter.get(time).getAndAdd(reqs);
				Integer nums=0;
				for(int i=0;i<5;i++){
					nums=nums+counter.get(time-i).get();
				}
				if(nums>limit){
					System.out.println("超过限制:"+nums+limit);
				}
				System.out.println("没有限流:"+nums);
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		},1000,1000, TimeUnit.MILLISECONDS);
	}

}
