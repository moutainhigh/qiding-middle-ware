package com.qiding.redis.impl;

import com.qiding.redis.pojo.RedisNode;
import com.qiding.redis.AbstractClusterRedisService;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ClusterRedis extends AbstractClusterRedisService {
    public ClusterRedis(RedisNode[] redisNodes, String password) {
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
