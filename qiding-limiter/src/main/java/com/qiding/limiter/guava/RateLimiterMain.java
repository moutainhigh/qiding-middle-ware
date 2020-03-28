package com.qiding.limiter.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class RateLimiterMain {
	public static void main(String[] args) {
        leaky();
		//rateLimit();
		return;
	}

	private static void rateLimit() {
		RateLimiter rateLimiter=RateLimiter.create(1	 );
		while (true){
			System.out.println("1等待时间："+rateLimiter.acquire(1));
			System.out.println("10等待时间："+rateLimiter.acquire(10));
			//System.out.println(Instant.now().toEpochMilli());
		}
	}

	public static void leaky(){
		RateLimiter rateLimiter=RateLimiter.create(1, Duration.ofSeconds(2));
		while (true){
			System.out.println("1等待时间："+rateLimiter.acquire(1));
			System.out.println("10等待时间："+rateLimiter.acquire(10));
			//System.out.println(Instant.now().toEpochMilli());
		}

	}


}
