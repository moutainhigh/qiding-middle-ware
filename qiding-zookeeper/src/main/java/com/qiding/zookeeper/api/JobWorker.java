package com.qiding.zookeeper.api;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.Random;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

public class JobWorker {

	private Boolean isMaster=false;

	private String masterName;

	boolean isLeader = false;
	String serverId=Long.toString(new Random().nextLong());

	public void runForMaster(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {
		try {
			zooKeeper.create("/master",serverId.getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            isLeader=true;
		}catch (KeeperException.NodeExistsException e){
             isLeader=false;
		}
	}

	public Boolean checkMaster(ZooKeeper zooKeeper){
		while (true){
			Stat stat=new Stat();
			try {
				byte[]bytes=zooKeeper.getData("/master",false,stat);
				isLeader= serverId.equals(new String(bytes));
				return true;
			} catch (KeeperException.NoNodeException e) {
				isLeader=false;
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (KeeperException e) {
				e.printStackTrace();
			}


		}
		return null;
	}


}
