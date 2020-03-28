package com.qiding.mongodb.impl;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ClusterType;
import com.qiding.mongodb.api.IConnection;
import com.qiding.mongodb.api.IDBConnection;

import java.util.Arrays;

public class MongoConnection implements IConnection, IDBConnection {

    private MongoClient mongoClient;

    @Override
    public MongoClient getConnection(String setName,ServerAddress... address) {
        MongoCredential credentials = MongoCredential.createCredential("cdtp-dns", "cdtp-dns", "RzGxIBnj".toCharArray());
        mongoClient = MongoClients.create(MongoClientSettings.builder()
                 .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(address)))
                 .applyToClusterSettings(builder -> builder.requiredClusterType(ClusterType.REPLICA_SET).requiredReplicaSetName(setName))
                 .credential(credentials)
                  .build());
        return mongoClient;
    }

    @Override
    public Boolean closeConnection() {
        mongoClient.close();
        return true;
    }


    @Override
    public MongoDatabase getDataBase(String dataBase) {
        return  mongoClient.getDatabase(dataBase);
    }

    @Override
    public Boolean delDataBase(String dataBase) {
        mongoClient.close();
        return true;
    }
}
