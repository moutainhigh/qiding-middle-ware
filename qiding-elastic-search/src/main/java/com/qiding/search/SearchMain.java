package com.qiding.search;

import com.qiding.search.client.TransportClientFactory;
import com.qiding.search.search.SearchService;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

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
		//QueryBuilders queryBuilders=QueryBuilders.wildcardQuery("",)


		//useTemplate();
		//termainateAfter();
//		multiSearch();
		//baseSearch();
	}
}
