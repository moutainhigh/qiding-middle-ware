package com.qiding.zookeeper.api;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

public class JobWorker {

	private Boolean isMaster=false;

	private String masterName;

	public void runForMaster(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {
		zooKeeper.create("/master",masterName.getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
	}

	public Boolean checkMaster(ZooKeeper zooKeeper){
		while (true){
			Stat stat=new Stat();
			try {
				byte[]bytes=zooKeeper.getData("/master",false,stat);

			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


		}
	}


}
