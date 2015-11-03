package com.pvs.service.write;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.pvs.db.connection.ConnectionManagerFactory;

public class ProductValidationSystemWriteService {
	public static boolean registerCompany(Document companyModel) {
		MongoClient mongoClient = ConnectionManagerFactory.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");
		db.getCollection("testCollection").insertOne(companyModel);
		mongoClient.close();
		return true;
	}
	public static boolean registerProduct(Document productModel) {
		MongoClient mongoClient = ConnectionManagerFactory.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");
		db.getCollection("testCollection").insertOne(productModel);
		mongoClient.close();
		return true;
	}
}
