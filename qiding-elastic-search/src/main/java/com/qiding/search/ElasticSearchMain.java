package com.qiding.search;

import com.qiding.search.client.DocumentFun;
import com.qiding.search.client.TransportClientFactory;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.transport.Transport;

import javax.print.Doc;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 *
 */
@Log4j2
public class ElasticSearchMain
{
    public static void main( String[] args ) throws IOException, InterruptedException, ExecutionException {
    	log.info("hello world");
        System.out.println( "Hello World!" );
		TransportClient client= TransportClientFactory.instance.client("qiding-cluster");
         createDocIndex(client);
	//	getDoc(client);

		//delDoc(client);

		//delByQuery(client);
		//asyncDelByQuery(client);
		//updateDoc(client);
		//batchGet(client);
		//bulkFun(client);
		//bulkProcessor(client);
		//updateByQuery(client);
		reindex(client);
    }



    public static void createDocIndex(TransportClient client) throws IOException {
		DocumentFun document=new DocumentFun(client);
		document.indexDocument();
	}


	public static void getDoc(TransportClient client){
		DocumentFun document=new DocumentFun(client);
		document.getDocument();
	}

	public static void delDoc(TransportClient client){
		DocumentFun document=new DocumentFun(client);
		document.delDocument();
	}

	public static void delByQuery(TransportClient client){
		DocumentFun document=new DocumentFun(client);
		document.delByQuery();
	}

	public static void asyncDelByQuery(TransportClient client) throws InterruptedException {
		DocumentFun document=new DocumentFun(client);
		document.asyncDelByQuery();
	}

    public static void updateDoc(TransportClient client) throws InterruptedException, ExecutionException, IOException {
		DocumentFun document=new DocumentFun(client);
		document.updateDoc();
	}

	public  static void batchGet(TransportClient client){
		DocumentFun document=new DocumentFun(client);
		document.batchGet();
	}

	public static void bulkFun(TransportClient client){
		DocumentFun document=new DocumentFun(client);
		document.bulkFun();
	}

    public static void bulkProcessor(TransportClient client) throws InterruptedException {
		DocumentFun document=new DocumentFun(client);
		document.bulkProcessor();
	}

	public static void updateByQuery(TransportClient client){
		DocumentFun document=new DocumentFun(client);
		document.updateByQuery();
	}

	public static void reindex(TransportClient client){
		DocumentFun document=new DocumentFun(client);
		document.reindex();
	}


}
