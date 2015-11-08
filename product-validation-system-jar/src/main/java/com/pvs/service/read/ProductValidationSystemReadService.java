package com.pvs.service.read;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pvs.db.connection.ConnectionManagerFactory;
import com.pvs.db.connection.DBCollectionManagerFactory;
import com.pvs.db.connection.DatabaseManagerFactory;
import com.pvs.db.connection.utils.DatabaseConstants;
import com.pvs.enums.PlanStates;
import com.pvs.service.utils.CommonUtils;
import com.pvs.service.valueobjects.PlanVO;

public class ProductValidationSystemReadService {
	final static Logger log = Logger.getLogger(ProductValidationSystemReadService.class);
	public static boolean validateUser(String userid, String password) {
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_COLLECTION_NAME);
			Document searchCriteria = new Document().append(DatabaseConstants.PRIMARY_KEY_COMPANY_COLLECTION, userid).append("companyPassword", password);
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents.first() != null) {
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
	
	
	public static Document getProductDetails(String productId, String instanceId, String productType) {
		
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			String collectionName = CommonUtils.getProductCollectionName(productType);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, collectionName);		
			Document searchCriteria = new Document().append(DatabaseConstants.PRIMARY_KEY_COMPANY_COLLECTION, productId).append("instanceId", instanceId);
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents != null) {
				return documents.first();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			mongoClient.close();
		}
		return null;
	}
	
	public static Document getCompanyRecord(String companyEmail) {
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_COLLECTION_NAME);		
			Document searchCriteria = new Document().append(DatabaseConstants.PRIMARY_KEY_COMPANY_COLLECTION, companyEmail);
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents != null) {
				return documents.first();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			mongoClient.close();
		}
		return null;
	}
	
	public static Document getPlanRecord(String planId) {
		MongoClient mongoClient = null;
		try {
			ObjectId planObjectId = new ObjectId(planId);
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.PLAN_COLLECTION_NAME);		
			Document searchCriteria = new Document().append(DatabaseConstants.PRIMARY_KEY_PLAN_COLLECTION, planObjectId);
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents != null) {
				return documents.first();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			mongoClient.close();
		}
		return null;
	}
	
	public static Document getCompanyPlanRecord(String companyEmail) {
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_PLAN_COLLECTION_NAME);		
			Document searchCriteria = new Document().append(DatabaseConstants.PRIMARY_KEY_COMPANY_COLLECTION, companyEmail);
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents != null) {
				return documents.first();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			mongoClient.close();
		}
		return null;
	}
	
	
	public static String getCompanyPlanName(String companyPlanId) {
		String companyPlanName = null;
		Document planRecord = getPlanRecord(companyPlanId);
		if(planRecord == null)
			return null;
		companyPlanName = planRecord.getString("planName");
		return companyPlanName;
	}
	
	public static List<PlanVO> getAllPlans() {
		MongoClient mongoClient = null;
		List<PlanVO> planList = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.PLAN_COLLECTION_NAME);		
			Document searchCriteria = new Document().append(DatabaseConstants.PLAN_STATE, PlanStates.ACTIVE.getPlanState());
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents != null) {
				planList = new LinkedList<PlanVO> ();
				for(Document plan : documents) {
					PlanVO planVO = new PlanVO();
					String planId = plan.get(DatabaseConstants.PRIMARY_KEY_PLAN_COLLECTION).toString();
					String planName = plan.getString("planName");
					String allowedRecordCount = plan.getString("allowedRecordCount");
					String allowedScanCount = plan.getString("allowedScanCount");
					String planPrice = plan.getString("planPrice");
					String planState = plan.getString("planState");
					String currencyCode = plan.getString("currencyCode");
					
					planVO.setPlanId(planId);
					planVO.setPlanName(planName);
					planVO.setAllowedRecordCount(allowedRecordCount);
					planVO.setAllowedScanCount(allowedScanCount);
					planVO.setPlanPrice(planPrice);
					planVO.setPlanState(planState);
					planVO.setCurrencyCode(currencyCode);
					
					planList.add(planVO);					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			mongoClient.close();
		}
		return planList;
	}
	
	public static synchronized Long getRemainingScanCount(String companyEmail) {
		Long remainingScanCount = null;
		try {
			Document companyPlanRecord = ProductValidationSystemReadService.getCompanyPlanRecord(companyEmail);
			log.debug("companyPlanRecord : "+companyPlanRecord);
			if(companyPlanRecord != null) {
				remainingScanCount = Long.parseLong(companyPlanRecord.getString("remainingScanCount"));
				log.debug("remainingScanCount : "+remainingScanCount);
			}
			return remainingScanCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return remainingScanCount;
	}
	
	public static synchronized Long getRemainingRecordCount(String companyEmail) {
		Long remainingRecordCount = null;
		try {
			Document companyPlanRecord = ProductValidationSystemReadService.getCompanyPlanRecord(companyEmail);
			log.debug("companyPlanRecord : "+companyPlanRecord);
			if(companyPlanRecord != null) {
				remainingRecordCount = Long.parseLong(companyPlanRecord.getString("remainingRecordCount"));
				log.debug("remainingRecordCount : "+remainingRecordCount);
			}
			return remainingRecordCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return remainingRecordCount;
	}
 
}
