package com.qiding.test;

import com.qiding.test.api.IRedis;
import com.qiding.test.impl.ClusterRedis;
import com.qiding.test.impl.SentinelRedis;
import com.qiding.test.pojo.RedisNode;

import java.sql.Time;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App
{


    public static void sentinel()throws InterruptedException{
        String masterName="email-test";
        RedisNode[] redisNodes={new RedisNode("172.28.61.27",26379),
                new RedisNode("172.28.61.26",26379),
                new RedisNode("172.28.61.25",26379)};
        IRedis redis=new SentinelRedis(masterName,redisNodes, "b840b7be6c52");
        System.out.println(redis.getValue("temail:auth:user-temail:master@t.cm"));

        redis.setValue("qiding","111111", Duration.ofSeconds(20));

        TimeUnit.SECONDS.sleep(5);
        redis=new SentinelRedis(masterName,redisNodes, "b840b7be6c52");
        System.out.println(redis.getValue("qiding"));


        TimeUnit.SECONDS.sleep(10);
        redis=new SentinelRedis(masterName,redisNodes, "b840b7be6c52");
        System.out.println(redis.getValue("qiding"));


        TimeUnit.SECONDS.sleep(5);
        redis=new SentinelRedis(masterName,redisNodes, "b840b7be6c52");
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
        IRedis redis=new ClusterRedis(redisNodes, "b840b7be6c52");

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
