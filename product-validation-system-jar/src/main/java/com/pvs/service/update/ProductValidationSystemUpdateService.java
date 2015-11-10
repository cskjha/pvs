package com.pvs.service.update;

import java.util.Map;

import org.apache.log4j.Logger;
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


public static synchronized boolean updateRemainingScanCount(String companyEmail) {
	MongoClient mongoClient = null;
	try {
		mongoClient = ConnectionManagerFactory.getMongoClient();
		MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
		MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_PLAN_COLLECTION_NAME);
		Document searchCriteria = new Document().append(DatabaseConstants.PRIMARY_KEY_COMPANY_COLLECTION, companyEmail);
		Document companyPlanRecord = ProductValidationSystemReadService.getCompanyPlanRecord(companyEmail);
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

}
