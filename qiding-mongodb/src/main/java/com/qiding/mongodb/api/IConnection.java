package com.qiding.mongodb.api;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;

public interface IConnection {
    //getconnection
    MongoClient getConnection(String setName,ServerAddress... address);

    //close connection
    Boolean closeConnection();
}
