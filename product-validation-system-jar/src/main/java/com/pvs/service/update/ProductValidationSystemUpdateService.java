package com.pvs.service.update;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pvs.db.connection.ConnectionManagerFactory;
import com.pvs.db.connection.DatabaseManagerFactory;

public class ProductValidationSystemUpdateService {
	public static Document updateCompanyRecord(String companyEmail) {
		MongoClient mongoClient = ConnectionManagerFactory.getMongoClient();
		String dbName = "test";
		MongoDatabase db = DatabaseManagerFactory.getDatabase(mongoClient, dbName);
		MongoCollection<Document> mongoCollection = db.getCollection("testCollection");
		Document searchCriteria = new Document().append("email", companyEmail);
		FindIterable<Document> documents = mongoCollection.find(searchCriteria);
		if(documents != null) {
			return documents.first();
		}
		return null;
	}
	
	public static Document updateProductRecord(String productId) {
		return null;
	}
}
