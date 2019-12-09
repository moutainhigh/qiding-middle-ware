package com.qiding.demo.client;

import com.qiding.demo.document.DocumentParam;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Log4j2
public class DocumentFun {

	private TransportClient client;

	public DocumentFun(TransportClient client) {
		this.client = client;
	}

	public void indexDocument() throws IOException {
		//XContent生成生成json对象
		XContentBuilder builder=jsonBuilder().startObject()
			.field("username","qiding")
			.field("sex",1)
			.field("age",28)
			.endObject();

		String xjson= Strings.toString(builder);
		//索引文档
		IndexResponse response=client.prepareIndex("user","_doc","5")
			.setSource(xjson, XContentType.JSON).get();
		//索引名字
		String _index=response.getIndex();
		//type
		String _type=response.getType();
		//
		RestStatus result=response.status();
	}


	public void getDocument(){
		GetResponse response= client.prepareGet("user","_doc","1").get();
		System.out.println(response.getSource());
		log.info("get source{}",response);
	}

	public void getDocument(String index){
		GetResponse response= client.prepareGet("user","_doc",index).get();
		System.out.println(response.getSource());
		log.info("get source{}",response);
	}

	public void delDocument(){
		DeleteResponse response= client.prepareDelete("user","_doc","1").get();
		log.info("删除的返回值{}",response);
	}

	public void delByQuery(){
		BulkByScrollResponse response = new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE)
			.filter(QueryBuilders.matchQuery("username","qiding"))
			.source("user")
			.get();
		log.info("delete by filter result={}",response);
	}

	public void asyncDelByQuery() throws InterruptedException {
		CountDownLatch latch=new CountDownLatch(1);
		 new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE)
			.filter(QueryBuilders.matchQuery("username","qiding"))
			.source("user")
			.execute(new ActionListener<BulkByScrollResponse>() {
				@Override
				public void onResponse(BulkByScrollResponse response) {
					log.info("删除返回值={}.删除数量={}",response,response.getDeleted());
					latch.countDown();
				}

				@Override
				public void onFailure(Exception e) {
					log.error("删除失败",e);
				}
			});
		latch.await();
	}


	public  void updateDoc() throws IOException, ExecutionException, InterruptedException {
         //使用请求变量
		UpdateRequest request=new UpdateRequest();
		request.index("user")
			    .type("_doc")
			    .id("5")
			    .doc(jsonBuilder().startObject()
			     .field("username","qiding2")
				.endObject()
			);
	   UpdateResponse response1= client.update(request).get();
	   log.info("{}",response1);
		//
		getDocument("5");
		//使用脚本
		response1=client.prepareUpdate("user","_doc","5")
			//.setDoc(jsonBuilder().startObject().field("username","qiding3").endObject())
			.setScript(new Script("ctx._source.username=\"qiding4\""))
			.get();
		log.info("{}",response1);

		//更新插入
		IndexRequest indexRequest=new IndexRequest("index","type","10");
		indexRequest.source(jsonBuilder()
				.startObject()
					.field("key1","value1")
					.field("key2","value2")
			    .endObject()
		);

		UpdateRequest updateRequest=new UpdateRequest("index","type","10");
		updateRequest.doc(jsonBuilder()
			.startObject()
			.field("key2","value2")
			.endObject()
		);
		updateRequest.upsert(indexRequest);
		UpdateResponse response3=client.update(updateRequest).get();
		log.info("{}",response3);
	}


	public void batchGet(){
	 MultiGetResponse responses=	client.prepareMultiGet()
			.add("user","_doc","1","2","5")
			.add("index","type","10")
			.get();

	 for(MultiGetItemResponse response:responses){
	 	 GetResponse itemResponse=response.getResponse();
	 	 log.info("{}",itemResponse);
	 	 if(itemResponse.isExists()){
			 log.info("response source={}",itemResponse.getSource());
		 }
	 }
	}

	public void bulkFun(){
		BulkRequestBuilder bulkBuilder=client.prepareBulk();
		bulkBuilder
			.add(client.prepareIndex("user","_doc","1")
		    	.setSource(DocumentParam.getInstance(),XContentType.JSON))
			.add(client.prepareIndex("user","_doc","2")
				.setSource(DocumentParam.getInstance(),XContentType.JSON))
			.add(client.prepareIndex("user","_doc","3")
				.setSource(DocumentParam.getInstance(),XContentType.JSON))
	      	.add(client.prepareUpdate("user","_doc","3")
				.setDoc(DocumentParam.getInstance(),XContentType.JSON))
		 	.add(client.prepareIndex("user","_doc","4")
			    .setSource(DocumentParam.getInstance(),XContentType.JSON))
			.add(client.prepareDelete("user","_doc","4"));
		BulkResponse responses=bulkBuilder.get();
		for(BulkItemResponse itemResponse:responses){
			log.info("{}",itemResponse.getResponse());
			if(!itemResponse.isFailed()){
				log.info("response source={}",itemResponse.getResponse());
			}
		}
	}



	public void bulkProcessor() throws InterruptedException {
		//声明一个listener
		BulkProcessor.Listener listener=new BulkProcessor.Listener(){
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
               log.info("executionId={},request={}",executionId,request);
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				log.info("executionId={},request={},response={}",executionId,request,response);
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				log.info("executionId={},request={},failure={}",executionId,request,failure);
			}
		};


      //声明一个bulkProcessor （定时定量 任务 ）
		BulkProcessor processor=BulkProcessor.builder(client,listener)
			.setBulkActions(100)
			.setConcurrentRequests(0)
			.setFlushInterval(TimeValue.timeValueSeconds(5))
			.build();
		processor
			.add(new IndexRequest().index("user").id("6").source(DocumentParam.getInstance(),XContentType.JSON))
			.add(new IndexRequest().index("blogs").id("1").source(DocumentParam.getInstance(),XContentType.JSON))
			.add(new IndexRequest().index("blogs").id("2").source(DocumentParam.getInstance(),XContentType.JSON))
			.add(new IndexRequest().index("blogs").id("3").source(DocumentParam.getInstance(),XContentType.JSON));


/*
		processor.flush();

		processor.close();

		client.admin().indices().prepareRefresh().get();

		client.prepareSearch().get();*/
		TimeUnit.SECONDS.sleep(20);

        //全局变量设置

	}

	public void updateByQuery(){
		UpdateByQueryRequestBuilder builder=new UpdateByQueryRequestBuilder(client,UpdateByQueryAction.INSTANCE);
		builder.source("user")
			.filter(QueryBuilders.termQuery("username","qiding10"))
			.maxDocs(100)
			.script(new Script(ScriptType.INLINE,
				"painless",
				"ctx._source.awesome = 'absolutely'",
				Collections.emptyMap()));
		BulkByScrollResponse response = builder.get();
		log.info("response={}",response);

		//只更新version
		UpdateByQueryRequestBuilder builder2=new UpdateByQueryRequestBuilder(client,UpdateByQueryAction.INSTANCE);
		builder2.source("user")
			.source();
		response=builder2.get();
		log.info("response={}",response);

		//脚本执行
		UpdateByQueryRequestBuilder builder3 = new UpdateByQueryRequestBuilder(client, UpdateByQueryAction.INSTANCE);
		builder3.source("blogs")
			.script(new Script(ScriptType.INLINE,"painless",
				  " if(ctx._source.awesome == 'absolutely'){"+
				       "ctx.op='noop'"+
		           "}else if(ctx._source.awesome == 'lame'){"+
				    " ctx.op='delete'"+
		          "}else{"+
					"ctx._source.awesome == 'absolutely'}",Collections.emptyMap()));
		builder3.get();

	}


	/**
	 * 重新索引
	 */
	public void reindex(){
		BulkByScrollResponse response=new ReindexRequestBuilder(client,ReindexAction.INSTANCE)
			.source("user")
			.destination("qiding")
			.get();

		log.info("return {}",response);
	}




}
