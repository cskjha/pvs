package com.pvs.db.connection;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DatabaseManagerFactory {
	public static MongoDatabase getDatabase(MongoClient mongoClient, String dbName) {
		if(mongoClient == null || dbName == null) {
			return null;
		}
		else {
			return getDatabase(mongoClient);
		}
	}
	private static MongoDatabase getDatabase(MongoClient mongoClient) {
		return mongoClient.getDatabase("test");
	}
}
