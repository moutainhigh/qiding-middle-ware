package com.qiding.search;

import com.qiding.search.client.TransportClientFactory;
import com.qiding.search.search.SearchService;
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
//		searchService.useTemplate();
	}





	public static void main(String[] args) {
		useTemplate();
		//termainateAfter();
//		multiSearch();
		//baseSearch();

	}
}
