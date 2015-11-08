package com.pvs.db.connection;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBCollectionManagerFactory {
	public static MongoCollection<Document> getOrCreateCollection(MongoDatabase mongoDb, String collectionName) {
		MongoCollection<Document> mongoCollection =  mongoDb.getCollection(collectionName);
		if(mongoCollection == null) {
			mongoDb.createCollection(collectionName);
		}		
		return mongoDb.getCollection(collectionName);
	}
}
