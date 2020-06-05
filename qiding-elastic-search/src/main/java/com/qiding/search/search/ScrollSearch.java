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



<<<<<<< HEAD
//		client.prepareSearch("index")
=======
		client.prepareSearch("index");
>>>>>>> 61c6944fab5799998ec06a5e332f3348ecc28024
	}

	public void scrollSearch(String scrollId,Long timeout){

	}

}
