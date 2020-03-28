package com.qiding.search.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.transport.TransportClient;

public class ScrollSearch {
	private TransportClient client;

	public ScrollSearch(TransportClient client) {
		this.client = client;
	}
	public void scrollSearch(String index,Long timeout,Integer limit){



		client.prepareSearch("index");
	}

	public void scrollSearch(String scrollId,Long timeout){

	}

}
