package com.qiding.demo;

import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.qiding.demo.api.IConnection;
import com.qiding.demo.impl.MongoConnection;
import org.bson.Document;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {

        IConnection connection=new MongoConnection();

        //ServerAddress[]addresses={new ServerAddress("127.0.0.1",27017),new ServerAddress("127.0.0.1",27027),new ServerAddress("127.0.0.1",27037)};
        ServerAddress[]addresses={new ServerAddress("172.28.61.27",27077),new ServerAddress("172.28.61.26",27077),new ServerAddress("172.28.61.25",27077)};

        MongoClient mongoClient= connection.getConnection("miyou-a",addresses);

        MongoDatabase database= mongoClient.getDatabase("cdtp-dns");

        MongoCollection<Document> collection= database.getCollection("hello-world");

        while (true){
            TimeUnit.SECONDS.sleep(10);
            Document document=new Document("name","qiding").append("age",100);

            collection.insertOne(document);

            FindIterable<Document> result= collection.find(document);

            MongoCursor<Document> iterator= result.iterator();

            while (iterator.hasNext()){

                Document resut= iterator.next();

                System.out.println(resut.toJson());
            }
        }



    }
}
