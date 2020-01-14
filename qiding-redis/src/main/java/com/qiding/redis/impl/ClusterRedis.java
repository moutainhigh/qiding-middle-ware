package com.qiding.test.impl;

import com.qiding.test.AbstractClusterRedisService;
import com.qiding.test.AbstractRedisService;
import com.qiding.test.pojo.RedisNode;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ClusterRedis extends AbstractClusterRedisService {
    public ClusterRedis(RedisNode[] redisNodes,String password) {
        super(redisNodes,password);
    }

    @Override
    public JedisCluster getRedisClient() {
        Set<HostAndPort> clusterNodes= Arrays.asList(getRedisNodes()).parallelStream().map(redisNode -> new HostAndPort(redisNode.getHost(),redisNode.getPort())).collect(Collectors.toSet());
        JedisCluster jedisCluster=new JedisCluster(clusterNodes, Protocol.DEFAULT_TIMEOUT,jedisPoolConfig);
//        if(getPassword()!=null){
//            jedisCluster.auth(getPassword());
//        }
        return jedisCluster;
    }
}
