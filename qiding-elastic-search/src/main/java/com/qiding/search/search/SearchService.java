package com.qiding.search.search;

import lombok.extern.log4j.Log4j2;
import org.apache.lucene.util.CollectionUtil;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.persistent.StartPersistentTaskAction;
import org.elasticsearch.script.ScriptService;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;

import javax.naming.event.ObjectChangeListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class SearchService {
	private TransportClient client;

	public SearchService(TransportClient client) {
		this.client = client;
	}

	public void baseQuery(){
		SearchResponse response=client.prepareSearch("user","qiding")
			.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			.setQuery(QueryBuilders.termQuery("username","qiding"))
			.setPostFilter(QueryBuilders.rangeQuery("age").from("28").to("29"))
			.setFrom(0).setSize(60).setExplain(false)
			.get();

		log.info("response ={}",response);
	}

	//游标查询
	public void scrollQuery(){

	}

	//批量查询
	public  void multiQuery(){
		SearchRequestBuilder sb1=client.prepareSearch().setQuery(QueryBuilders.queryStringQuery("qiding"));
		SearchRequestBuilder sb2=client.prepareSearch().setQuery(QueryBuilders.matchQuery("age","100"));

		MultiSearchResponse responses=client.prepareMultiSearch()
			.add(sb2)
			.add(sb1)
			.get();

		long hits=0L;

		for(MultiSearchResponse.Item item:responses){
 	 		SearchResponse response= item.getResponse();
			log.info("{}",response);
 	 		hits+=response.getHits().getTotalHits().value;
		}
		log.info("hits={}",hits);
	}

	//判断时间是否提前结束
	public void terminated(){
		SearchResponse response=client.prepareSearch("qiding").setTerminateAfter(100)
			.get();
			log.info("{}",response);
			if(response.isTerminatedEarly()){
				System.out.println("1111");
			}
	}

	public void useTemplate() throws IOException {
//		Map<String,Object> params= new HashMap<>();
//		params.put("my_field","username");
//		params.put("my_value","qiding");
//		params.put("my_size","10");

//TODO 未知错误
//		SearchResponse response=new SearchTemplateRequestBuilder(client)
//			.setScript("qiding_search")
//			.setScriptType(ScriptService.ScriptType.FILE)
//			.setScriptParams(params)
//			.setRequest(new SearchRequest())
//			.get()
//			.getResponse();


//		Map<String,String> kv=new HashMap<>();
//		kv.put("{{my_field}}","{{my_value}}");
//
//		Map<String, Object> query=new HashMap<>();
//		query.put("match",kv);
//
//
//		Map<String,Object> options=new HashMap<>();
//		options.put("query",query);
//
//		Map<String,Object> option2=new HashMap<>();
//		options.put("query",query);
//
//
//
//
//
//
//
//
//
//
//
//		XContentBuilder.builder(XContentType.JSON.xContent())
//			.startObject()
//			.field("")
//
//
//
//		client.admin().cluster().preparePutStoredScript()
//			.setId("qiding_search")
//			//.setContent()
//			.setContent(new BytesArray(
//				"{\n" +
//					"    \"query\" : {\n" +
//					"        \"match\" : {\n" +
//					               "\"query\": { \"match\" : { \"{{my_field}}\" : \"{{my_value}}\" } },\n" +
//					               "\"size\" : \"{{my_size}}\""+
//					"        }\n" +
//					"    }\n" +
//					"}"), XContentType.JSON).get();
//
//		SearchResponse response=new SearchTemplateRequestBuilder(client)
//			.setScript("qiding_search")
//			.setScriptType(ScriptType.STORED)
//			.setScriptParams(params)
//			.setRequest(new SearchRequest())
//			.get().getResponse();
//
//		log.info(response);
	}



}
