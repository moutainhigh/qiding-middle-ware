package com.qiding.search.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("ALL")
public class TransportClientFactory {
	Map<String, TransportClient> clientMap = new ConcurrentHashMap<>();

	public static TransportClientFactory instance=new TransportClientFactory();

	private TransportClientFactory() {
	}

	public TransportClient client(String clusterName) throws UnknownHostException {
		Settings settings = Settings.builder()
			.put("cluster.name", "qiding-cluster")
//			.put("cluster.name", "hello-world")
			.put("client.transport.sniff",true)
			.build();
		TransportClient client = new PreBuiltTransportClient(settings)
			.addTransportAddress(new TransportAddress(InetAddress.getByName("master"), 9300));
//			.addTransportAddress(new TransportAddress(InetAddress.getByName("node2"), 9300))
//			.addTransportAddress(new TransportAddress(InetAddress.getByName("node1"),9300));
//			.addTransportAddress(new TransportAddress(InetAddress.getLocalHost(),9300));


		clientMap.put(clusterName,client);
		return client;
	}

	public void close(String clusterName){
		clientMap.get(clusterName).close();
	}




}
