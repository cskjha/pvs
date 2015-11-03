package com.pvs.service.read;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pvs.db.connection.ConnectionManagerFactory;

public class ProductValidationSystemReadService {
	public static boolean validateUser(String userid, String password) {
		MongoClient mongoClient = ConnectionManagerFactory.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");
		MongoCollection<Document> mongoCollection = db.getCollection("testCollection");
		Document searchCriteria = new Document().append("companyEmail", userid).append("companyPassword", password);
		FindIterable<Document> documents = mongoCollection.find(searchCriteria);
		if(documents.first() != null) {
			return true;
		}
		return false;
	}
	
	
	public static Document getProductDetails(String QRCode) {
		MongoClient mongoClient = ConnectionManagerFactory.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");
		MongoCollection<Document> mongoCollection = db.getCollection("testCollection");
		Document searchCriteria = new Document().append("QRCode", QRCode);
		FindIterable<Document> documents = mongoCollection.find(searchCriteria);
		if(documents != null) {
			return documents.first();
		}
		return null;
	}

}
