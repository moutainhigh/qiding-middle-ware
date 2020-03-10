package com.qiding.demo;

import com.qiding.demo.client.TransportClientFactory;
import com.qiding.demo.search.SearchService;
import org.elasticsearch.client.transport.TransportClient;

import java.net.UnknownHostException;

public class SearchMain {
	static  TransportClient client;

	static {
		try {
			client = TransportClientFactory.instance.client("qiding-cluster");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static void baseSearch(){
		SearchService searchService=new SearchService(client);
		searchService.baseQuery();
	}

	public static void multiSearch(){
		SearchService searchService=new SearchService(client);
		searchService.multiQuery();
	}

	public static void termainateAfter(){
		SearchService searchService=new SearchService(client);
		searchService.terminated();
	}

	public static void useTemplate(){
		SearchService searchService=new SearchService(client);
		baseSearch();
//		searchService.useTemplate();
	}





	public static void main(String[] args) {
		useTemplate();
		//termainateAfter();
//		multiSearch();
		//baseSearch();

	}
}
