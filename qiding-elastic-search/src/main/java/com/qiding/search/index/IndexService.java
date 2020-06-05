package com.qiding.search.index;

import com.qiding.search.client.TransportClientFactory;
import lombok.extern.log4j.Log4j2;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.net.UnknownHostException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Log4j2
public class IndexService {
	TransportClient client= TransportClientFactory.instance.client("qiding-cluster");

	public IndexService() throws UnknownHostException {

	}

	public void createIndex() throws IOException {

		XContentBuilder analysis= jsonBuilder().startObject()
		     .startObject("analysis")
		          .startObject("analyzer")
		                  .startObject("content")
		                      .field("type","custom")
		                      .field("tokenizer","whitespace")
		                  .endObject()
		          .endObject()
		     .endObject()
		.endObject();

	 Settings settings=Settings.builder().put("number_of_shards",3)
			.put("number_of_replicas",2)
		    .put("analysis", StringUtils.toString(analysis))
			.build();

		CreateIndexResponse response= client.admin().indices().prepareCreate("qiding-index")
			.setSettings(settings)
			.addAlias(new Alias("qiding").filter("xx"))//给查询起一个别名
			.addMapping("my-log",
				           jsonBuilder().startObject()
					                    .startObject("properties")
										     .startObject("username")
					                               .field("type","text")
					                               .field("index","false")
					                          .endObject()
										 .endObject()
				             .endObject())
           .get();

		log.info(response);

//	  IndexResponse response= client.prepareIndex().setIndex("qiding-index")
//		  .setSource(
//		  	jsonBuilder().startObject()
//								.startObject("settings")
//										 .field("number_of_shards",3)
//										 .field("number_of_replicas",2)
//				                 .endObject()
//				                 .startObject("mappings")
//									 .startObject("my-log")
//										 .startObject("properties")
//											  .startObject("username").field("type","string").field("index","not_analyzed")
//										 .endObject()
//									 .endObject()
//				                  .endObject()
//				        .endObject()
//		, XContentType.JSON).get();
	//  log.info("{}",response);
	}


	public static void main(String[] args) throws IOException {
		IndexService indexService=new IndexService();
		indexService.createIndex();
	}


}
