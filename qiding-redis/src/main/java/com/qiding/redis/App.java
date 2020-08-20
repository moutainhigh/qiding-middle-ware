package com.qiding.redis;

import com.qiding.redis.pojo.RedisNode;
import com.qiding.redis.api.IRedis;
import com.qiding.redis.impl.ClusterRedis;
import com.qiding.redis.impl.SentinelRedis;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App
{


    public static void sentinel()throws InterruptedException{
		String masterName="qiding";
		RedisNode[] redisNodes={new RedisNode("127.0.0.1",26301),
                new RedisNode("127.0.0.1",26302),
                new RedisNode("127.0.0.1",26303),
                new RedisNode("127.0.0.1",26304)};
		IRedis redis=new SentinelRedis(masterName,redisNodes, null);
		Boolean hello=true;
		while (hello){
			try {
				// System.out.println(redis.getValue("temail:auth:user-temail:master@t.cm"));
				redis.setValue("qiding","111111", Duration.ofSeconds(200));
				redis=new SentinelRedis(masterName,redisNodes, null);
				System.out.println(redis.getValue("qiding"));
				TimeUnit.SECONDS.sleep(10);
			}catch (Exception e){

			}
		}






        TimeUnit.SECONDS.sleep(10);
        redis=new SentinelRedis(masterName,redisNodes, null);
        System.out.println(redis.getValue("qiding"));


        TimeUnit.SECONDS.sleep(5);
        redis=new SentinelRedis(masterName,redisNodes, null);
        System.out.println(redis.getValue("qiding"));
    }

    public static void cluster() throws InterruptedException {
        RedisNode[] redisNodes={new RedisNode("127.0.0.1",7000),
                new RedisNode("127.0.0.1",7001)
//                new RedisNode("127.0.0.1",7002),
//                new RedisNode("127.0.0.1",7003),
//                new RedisNode("127.0.0.1",7004),
//                new RedisNode("127.0.0.1",7005),
        };
        IRedis redis=new ClusterRedis(redisNodes, null);

        while(true){
            redis.setValue("B","qiding",Duration.ofSeconds(1000));
            TimeUnit.SECONDS.sleep(2);
            System.out.println("获取到值"+redis.getValue("B"));
        }


    }


    public static void main( String[] args ) throws InterruptedException {
//        cluster();
        sentinel();
    }
}
