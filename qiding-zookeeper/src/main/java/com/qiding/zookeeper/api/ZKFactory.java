package com.qiding.zookeeper.api;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ZKFactory {

	private String zkHost;

	private ZooKeeper zooKeeper;

	public ZKFactory(String zkHost) {
		this.zkHost = zkHost;
	}

	public ZooKeeper getZookeeper(Watcher watcher) throws IOException {
		zooKeeper= new ZooKeeper(zkHost, 15000, watcher);
		return zooKeeper;
	}

	public void closeZK() throws InterruptedException {
		zooKeeper.close();
	}

	public static void main(String[] args) throws InterruptedException {
		ZKFactory zkFactory = new ZKFactory("127.0.0.1:2181");
		try {
			ZooKeeper zooKeeper = zkFactory.getZookeeper(((event) -> System.out.println(event)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		TimeUnit.SECONDS.sleep(10);
		zkFactory.closeZK();
	}
}



