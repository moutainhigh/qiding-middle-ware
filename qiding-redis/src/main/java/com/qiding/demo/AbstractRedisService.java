package com.qiding.demo;

import com.qiding.demo.api.IRedis;
import com.qiding.demo.pojo.RedisNode;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

public abstract class  AbstractRedisService implements IRedis {

    private RedisNode[] redisNodes;
    private String password;
    protected final  JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();

    public RedisNode[] getRedisNodes() {
        return redisNodes;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AbstractRedisService(RedisNode[] redisNodes, String password) {
        this.redisNodes = redisNodes;
        this.password=password;
    }

    @Override
    public String getValue(String key) {
        Jedis jedis=getRedisClient();
        String value= jedis.get(key);
        jedis.close();
        return value;
    }

    @Override
    public void setValue(String key, String value, Duration duration) {
        Jedis jedis=getRedisClient();
        jedis.setex(key,Long.valueOf(duration.getSeconds()).intValue(),value);
        jedis.close();
        return;
    }
    public abstract Jedis getRedisClient();


}
