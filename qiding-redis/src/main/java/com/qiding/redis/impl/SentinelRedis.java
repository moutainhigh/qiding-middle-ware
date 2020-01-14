package com.qiding.test.impl;

import com.qiding.test.AbstractRedisService;
import com.qiding.test.pojo.RedisNode;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SentinelRedis  extends AbstractRedisService {
    private JedisSentinelPool pool;
    private String masterName;
    public SentinelRedis(String masterName,RedisNode[] redisNodes,String password) {
        super(redisNodes,password);
        this.masterName=masterName;
    }

    @Override
    public Jedis getRedisClient() {
       if(pool==null){
           synchronized (this){
               if(pool==null){
                   Set<String> sentinelSet= Arrays.asList(getRedisNodes()).parallelStream().map(redisNode -> redisNode.getHost()+":"+redisNode.getPort()).collect(Collectors.toSet());
                    pool=new JedisSentinelPool(masterName,sentinelSet,jedisPoolConfig, Protocol.DEFAULT_TIMEOUT,getPassword());
               }
           }
       }
       return pool.getResource();
    }
}
