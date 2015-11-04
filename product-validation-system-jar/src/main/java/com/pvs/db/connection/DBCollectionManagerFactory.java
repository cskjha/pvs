package com.pvs.db.connection;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBCollectionManagerFactory {
	public static MongoCollection<Document> getCollection(MongoDatabase mongoDb, String companyEmail) {
		return mongoDb.getCollection(companyEmail);
	}
}
