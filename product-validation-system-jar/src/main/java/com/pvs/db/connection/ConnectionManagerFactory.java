package com.pvs.db.connection;

import com.mongodb.MongoClient;

public class ConnectionManagerFactory {
	public static MongoClient getMongoClient() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		return mongoClient;
	}
}
