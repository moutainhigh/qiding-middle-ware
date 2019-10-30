package com.qiding.demo.api;

import com.mongodb.client.MongoDatabase;

public interface IDBConnection {

    MongoDatabase getDataBase(String dataBase);

    Boolean delDataBase(String dataBase);

}
