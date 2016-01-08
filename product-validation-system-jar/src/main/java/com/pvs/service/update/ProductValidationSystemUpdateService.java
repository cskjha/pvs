package com.pvs.service.update;

import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.pvs.db.connection.ConnectionManagerFactory;
import com.pvs.db.connection.DBCollectionManagerFactory;
import com.pvs.db.connection.DatabaseManagerFactory;
import com.pvs.db.connection.utils.DatabaseConstants;
import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.utils.CommonUtils;

public class ProductValidationSystemUpdateService {
	static final Logger log = Logger.getLogger(ProductValidationSystemUpdateService.class);
	public static boolean updateCompanyRecord(String companyEmail, Map<String, String> properties) {
			MongoClient mongoClient = null;
			try {
				mongoClient = ConnectionManagerFactory.getMongoClient();
				MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
				MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_COLLECTION_NAME);
				Document searchCriteria = new Document().append(DatabaseConstants.PRIMARY_KEY_COMPANY_COLLECTION, companyEmail);
				Document updateDocument = new Document();
				for(String property: properties.keySet()) {
					updateDocument.append(property, properties.get(property));
				}
				updateDocument = new Document("$set", updateDocument);
				UpdateResult updateResult = mongoCollection.updateOne(searchCriteria, updateDocument);
				updateResult.getModifiedCount();
				return true;
			} catch (Exception e) {
				log.error("PVS Exception occured : Message :  "+e.getMessage());
				log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
			}
			finally {
				mongoClient.close();
			}
	return false;
	}
	public static synchronized boolean updateRemainingRecordCount(String companyId) {
				MongoClient mongoClient = null;
				try {
					mongoClient = ConnectionManagerFactory.getMongoClient();
					MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
					MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_PLAN_COLLECTION_NAME);
					Document searchCriteria = new Document().append(DatabaseConstants.COMPANY_ID, companyId);
					Document companyPlanRecord = ProductValidationSystemReadService.getCompanyPlanRecord(companyId);
					log.debug("companyPlanRecord : "+companyPlanRecord);
					if(companyPlanRecord != null) {
						Long remainingRecordCount = Long.parseLong(companyPlanRecord.getString("remainingRecordCount"));
						log.debug("remainingRecordCount : "+remainingRecordCount);
						remainingRecordCount--;
						Document updateDocument = new Document();
						updateDocument.append("$set",new Document("remainingRecordCount", remainingRecordCount.toString()));
						UpdateResult updateResult = mongoCollection.updateOne(searchCriteria, updateDocument);
						log.debug("updateResult.getModifiedCount() : "+updateResult.getModifiedCount());
					}
					return true;
				} catch (Exception e) {
					log.error("PVS Exception occured : Message :  "+e.getMessage());
					log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
				}
				finally {
					mongoClient.close();
				}
		return false;
		}
	
	public static synchronized boolean updateRemainingRecordCount(String companyId, int totalDecrement) {
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_PLAN_COLLECTION_NAME);
			Document searchCriteria = new Document().append(DatabaseConstants.COMPANY_ID, companyId);
			Document companyPlanRecord = ProductValidationSystemReadService.getCompanyPlanRecord(companyId);
			log.debug("companyPlanRecord : "+companyPlanRecord);
			if(companyPlanRecord != null) {
				Long remainingRecordCount = Long.parseLong(companyPlanRecord.getString("remainingRecordCount"));
				log.debug("remainingRecordCount : "+remainingRecordCount);
				remainingRecordCount = remainingRecordCount - totalDecrement ;
				Document updateDocument = new Document();
				updateDocument.append("$set",new Document("remainingRecordCount", remainingRecordCount.toString()));
				UpdateResult updateResult = mongoCollection.updateOne(searchCriteria, updateDocument);
				log.debug("updateResult.getModifiedCount() : "+updateResult.getModifiedCount());
			}
			return true;
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally {
			mongoClient.close();
		}
	return false;
	}
	
	
	public static synchronized boolean updateRemainingScanCount(String companyId) {
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_PLAN_COLLECTION_NAME);
			Document searchCriteria = new Document().append(DatabaseConstants.COMPANY_ID, companyId);
			Document companyPlanRecord = ProductValidationSystemReadService.getCompanyPlanRecord(companyId);
			log.debug("companyPlanRecord : "+companyPlanRecord);
			if(companyPlanRecord != null) {
				Long remainingScanCount = Long.parseLong(companyPlanRecord.getString("remainingScanCount"));
				log.debug("remainingScanCount : "+remainingScanCount);
				remainingScanCount--;
				Document updateDocument = new Document();
				updateDocument.append("$set",new Document("remainingRecordCount", remainingScanCount.toString()));
				UpdateResult updateResult = mongoCollection.updateOne(searchCriteria, updateDocument);
				log.debug("updateResult.getModifiedCount() : "+updateResult.getModifiedCount());
			}
			return true;
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally {
			mongoClient.close();
		}
		return false;
	}

	public static boolean rechargeCompanyPlan(String companyId, String companyPlanId) {
		if(companyId ==null || companyPlanId == null) {
			return false;
		}
		MongoClient mongoClient = null;		
		try {
			String collectionName = DatabaseConstants.COMPANY_PLAN_COLLECTION_NAME;
			Document planDocument = ProductValidationSystemReadService.getPlanRecord(companyPlanId);
			String remainingRecordCount = planDocument.getString("allowedRecordCount");
			String remainingScanCount = planDocument.getString("allowedScanCount");
			mongoClient = ConnectionManagerFactory.getMongoClient();
			Document searchCriteria = new Document();
			searchCriteria.append(DatabaseConstants.COMPANY_ID, companyId).append("companyPlanId", companyPlanId);
			Document planModel = new Document();
			planModel.append("remainingRecordCount", remainingRecordCount);
			planModel.append("remainingScanCount", remainingScanCount);
			Document updateDocument = new Document().append("$set", planModel);
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			DBCollectionManagerFactory.getOrCreateCollection(mongoDb, collectionName).updateOne(searchCriteria, updateDocument);
			return true;
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally {
			mongoClient.close();
		}
		return false;
	}
	
	public static boolean updateStatus(String companyName,String companyEmail,String status){
		if(companyName ==null || companyEmail == null || status == null ) {
			return false;
		}
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			Document searchCriteria = new Document();
			DBCollection collection = db.getCollection(DatabaseConstants.COMPANY_COLLECTION_NAME);			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("status", status);
		
			searchCriteria.append("companyName", companyName).append("companyEmail", companyEmail);
		
			Document companyModel = new Document();
			companyModel.append("status", status);
			Document updateDocument = new Document().append("$set", companyModel);
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_COLLECTION_NAME).updateOne(searchCriteria, updateDocument);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally{
			mongoClient.close();
		}
		
	}


	public static boolean updateProductResponseCode(String productId,String responseCode,String productType){
		if(productId ==null || responseCode == null ) {
			return false;
		}
		MongoClient mongoClient = null;
		try {
						
			String collectionName =CommonUtils.getProductCollectionName(productType);
			
			mongoClient = ConnectionManagerFactory.getMongoClient();
			Document searchCriteria = new Document();
			searchCriteria.append(DatabaseConstants._ID, new ObjectId(productId));
			Document productModel = new Document();
			productModel.append("ResponseCode", responseCode);
			Document updateDocument = new Document().append("$set", productModel);
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			DBCollectionManagerFactory.getOrCreateCollection(mongoDb, collectionName).updateOne(searchCriteria, updateDocument);
			return true;
			
			
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally{
			mongoClient.close();
		}
		
	}

}
