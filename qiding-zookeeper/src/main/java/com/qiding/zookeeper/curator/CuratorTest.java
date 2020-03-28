package com.qiding.test.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingServer;
import org.apache.curator.test.TestingZooKeeperServer;
import org.apache.zookeeper.server.quorum.QuorumStats;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class CuratorTest {
    public static void main(String[] args) throws Exception {
        standCluster();
    }

    public static void standAlone() throws Exception {
        String path="/temp/zookeeper";
        TestingServer testingServer=new TestingServer(2181,new File(path));
        testingServer.start();
       CuratorFramework client= CuratorFrameworkFactory.builder()
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .connectString(testingServer.getConnectString())
                .connectionTimeoutMs(3000)
                .build();
        client.start();
        testingServer.close();
    }

    public static void standCluster() throws Exception {
         TestingCluster cluster=new TestingCluster(3);
         System.out.println(cluster.getConnectString());
         cluster.start();
         TimeUnit.SECONDS.sleep(10);

         TestingZooKeeperServer leader=null;

         for(TestingZooKeeperServer server:cluster.getServers()){
             System.out.print(server.getInstanceSpec().getServerId()+'-');
             System.out.println(server.getQuorumPeer().getServerState());
             if(server.getQuorumPeer().getServerState()== QuorumStats.Provider.LEADING_STATE){
                 leader=server;
             }
         }

        leader.stop();


        for(TestingZooKeeperServer server:cluster.getServers()){
            System.out.print(server.getInstanceSpec().getServerId()+'-');
            System.out.println(server.getQuorumPeer().getServerState());
        }




        TimeUnit.SECONDS.sleep(10);
        leader.start();
        for(TestingZooKeeperServer server:cluster.getServers()){
            System.out.print(server.getInstanceSpec().getServerId()+'-');
            System.out.println(server.getQuorumPeer().getServerState());
            if(server.getQuorumPeer().getServerState()== QuorumStats.Provider.LEADING_STATE){
                leader=server;
            }
        }



        TimeUnit.SECONDS.sleep(5);
        for(TestingZooKeeperServer server:cluster.getServers()){
            System.out.print(server.getInstanceSpec().getServerId()+'-');
            System.out.println(server.getQuorumPeer().getServerState());
            if(server.getQuorumPeer().getServerState()== QuorumStats.Provider.LEADING_STATE){
                leader=server;
            }
        }

        cluster.stop();

















    }


}
