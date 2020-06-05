package com.qiding.redis;

import com.qiding.redis.pojo.RedisNode;
import com.qiding.redis.api.IRedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

public abstract class AbstractClusterRedisService implements IRedis {

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

    public AbstractClusterRedisService(RedisNode[] redisNodes, String password) {
        this.redisNodes = redisNodes;
        this.password=password;
    }


    @Override
    public String getValue(String key) {
        JedisCluster jedis=getRedisClient();
        String value= jedis.get(key);
        return value;
    }

    @Override
    public void setValue(String key, String value, Duration duration) {
        JedisCluster jedis=getRedisClient();
        jedis.setex(key,Long.valueOf(duration.getSeconds()).intValue(),value);
        return;
    }
    public abstract JedisCluster getRedisClient();


}
