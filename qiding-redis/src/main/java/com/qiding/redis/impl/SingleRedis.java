package com.qiding.redis.impl;

import com.qiding.redis.AbstractRedisService;
import com.qiding.redis.pojo.RedisNode;
import redis.clients.jedis.*;

public class SingleRedis extends AbstractRedisService {
    private  JedisPool pool;
    public SingleRedis(RedisNode[] redisNodes, String password) {
        super(redisNodes,password);
    }

    @Override
    public Jedis getRedisClient() {
        if(pool==null){
            synchronized (this){
                if(pool==null){
                    RedisNode redisNode=getRedisNodes()[0];
                    JedisPool pool=new JedisPool(jedisPoolConfig,redisNode.getHost(),redisNode.getPort(), Protocol.DEFAULT_TIMEOUT);
                }
            }
        }
        return pool.getResource();
    }
}
