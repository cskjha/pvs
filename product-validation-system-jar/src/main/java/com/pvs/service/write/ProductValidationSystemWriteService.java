package com.pvs.service.write;

import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.pvs.db.connection.ConnectionManagerFactory;
import com.pvs.db.connection.DBCollectionManagerFactory;
import com.pvs.db.connection.DatabaseManagerFactory;
import com.pvs.db.connection.utils.DatabaseConstants;
import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.update.ProductValidationSystemUpdateService;
import com.pvs.service.utils.CommonUtils;

public class ProductValidationSystemWriteService {
	public static boolean registerCompany(Document companyModel) {
		MongoClient mongoClient = null;
		
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_COLLECTION_NAME).insertOne(companyModel);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			mongoClient.close();
		}
		return false;
	}
	
	
	public static boolean registerProduct(Document productModel, String productType, String companyEmail) {
		if(productModel ==null || productType == null || companyEmail == null) {
			return false;
		}
		MongoClient mongoClient = null;		
		try {
			String collectionName = CommonUtils.getProductCollectionName(productType);
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			DBCollectionManagerFactory.getOrCreateCollection(mongoDb, collectionName).insertOne(productModel);
			ProductValidationSystemUpdateService.updateRemainingRecordCount(companyEmail);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			mongoClient.close();
		}
		return false;
	}
	
	public static boolean replaceCompanyRecord(String companyEmail, Map<String, String> properties) {
		MongoClient mongoClient = null;
		try {
			Document replaceDocument = ProductValidationSystemReadService.getCompanyRecord(companyEmail);
			for(String property: properties.keySet()) {
				replaceDocument.append(property, properties.get(property));
			}
			Document searchCriteria = new Document();
			searchCriteria.append(DatabaseConstants.PRIMARY_KEY_COMPANY_COLLECTION, companyEmail);
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_COLLECTION_NAME);
			UpdateResult updateResult = mongoCollection.replaceOne(searchCriteria, replaceDocument);
			long modifiedDocumentCount = updateResult.getModifiedCount();
			if(modifiedDocumentCount == 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			mongoClient.close();
		}
		return false;
	}
	
	public static boolean registerPlan(Document planModel) {
		if(planModel ==null) {
			return false;
		}
		MongoClient mongoClient = null;
		try {
			String collectionName = DatabaseConstants.PLAN_COLLECTION_NAME;
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			DBCollectionManagerFactory.getOrCreateCollection(mongoDb, collectionName).insertOne(planModel);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			mongoClient.close();
		}
		return false;
	}
	
	public static boolean registerCompanyPlan(String companyEmail, String companyPlanId) {
		if(companyEmail ==null || companyPlanId == null) {
			return false;
		}
		MongoClient mongoClient = null;		
		try {
			String collectionName = DatabaseConstants.COMPANY_PLAN_COLLECTION_NAME;
			Document planDocument = ProductValidationSystemReadService.getPlanRecord(companyPlanId);
			String remainingRecordCount = planDocument.getString("allowedRecordCount");
			String remainingScanCount = planDocument.getString("allowedScanCount");
			mongoClient = ConnectionManagerFactory.getMongoClient();
			Document planModel = new Document();
			planModel.append("companyEmail", companyEmail);
			planModel.append("companyPlanId", companyPlanId);
			planModel.append("remainingRecordCount", remainingRecordCount);
			planModel.append("remainingScanCount", remainingScanCount);
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			DBCollectionManagerFactory.getOrCreateCollection(mongoDb, collectionName).insertOne(planModel);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			mongoClient.close();
		}
		return false;
	}
}
