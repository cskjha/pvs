package com.pvs.service.read;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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
	public static Document getCompanyEmail(String companyName, String companyId) {
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_COLLECTION_NAME);		
			Document searchCriteria = new Document().append(DatabaseConstants._ID, new ObjectId(companyId)).append("companyName", companyName);
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents != null) {
				return documents.first();
			}
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
			
		}
		finally {
			mongoClient.close();
		}
		return null;
	}
	
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
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally {
			mongoClient.close();
		}
		return false;
	}
	public static boolean validateCategory(String username) {
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_COLLECTION_NAME);
			Document searchCriteria = new Document().append(DatabaseConstants.PRIMARY_KEY_COMPANY_COLLECTION, username).append("category", "company");
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents.first() != null) {
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
			return false;
		}
		finally {
			mongoClient.close();
		}
		
	}
	public static boolean userStatus(String username){
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_COLLECTION_NAME);
			Document searchCriteria = new Document().append(DatabaseConstants.PRIMARY_KEY_COMPANY_COLLECTION, username).append("status", "enabled");
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents.first() != null) {
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
			return false;
		}
		finally {
			mongoClient.close();
		}
		
	}
	
	public static Document getProductDetails(String productId, String productType) {
		System.out.println("Pratyay print: productId-" + productId + "__productType-" + productType);
		log.debug("Pratyay print: productId-" + productId + "__productType-" + productType);
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			String collectionName = CommonUtils.getProductCollectionName(productType);
			System.out.println("Pratyay print: collectionName-" + collectionName);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, collectionName);		
			Document searchCriteria = new Document().append(DatabaseConstants._ID, new ObjectId(productId));
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents != null) {
				return documents.first();
			}
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
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
			return null;
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
			return null;
		}
		finally {
			mongoClient.close();
		}
		
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
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally {
			mongoClient.close();
		}
		return null;
	}
	
	public static Document getCompanyPlanRecord(String companyId) {
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_PLAN_COLLECTION_NAME);		
			Document searchCriteria = new Document().append(DatabaseConstants.COMPANY_ID, companyId);
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents != null) {
				return documents.first();
			}
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally {
			mongoClient.close();
		}
		return null;
	}
	
	public static List<Document> getCompanyTemplateRecords(String companyId) {
		MongoClient mongoClient = null;
		List<Document> companyTemplates = new ArrayList<Document>();
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_TEMPLATE_COLLECTION);		
			Document searchCriteria = new Document().append(DatabaseConstants.COMPANY_ID, companyId);
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			MongoCursor<Document> documentsIterator = documents.iterator();
			while(documents != null && documentsIterator.hasNext()) {
				Document companyTemplate =  documentsIterator.next();
				String productTemplateId = companyTemplate.getString(DatabaseConstants.PRODUCT_TEMPLATE_ID);
				String productType = companyTemplate.getString(DatabaseConstants.PRODUCT_TYPE);
				MongoCollection<Document> productTemplateCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, CommonUtils.getProductTemplateCollectionName(productType));		
				searchCriteria = new Document().append(DatabaseConstants._ID, new ObjectId(productTemplateId));
				//pratyay
				FindIterable<Document> productTemplateDocuments = productTemplateCollection.find(searchCriteria);
				MongoCursor<Document> productTemplateDocumentIterator = productTemplateDocuments.iterator();
				while(productTemplateDocumentIterator != null && productTemplateDocumentIterator.hasNext()) {
					Document companyTemplateDocument = productTemplateDocumentIterator.next();
					companyTemplateDocument.append("productType", productType);
					companyTemplates.add(companyTemplateDocument);
				}				
			}
			return companyTemplates;
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally {
			mongoClient.close();
		}
		return companyTemplates;
	}
	
	
	public static String getCompanyPlanName(String companyPlanId) {
		String companyPlanName = null;
		Document planRecord = getPlanRecord(companyPlanId);
		if(planRecord == null)
			return null;
		companyPlanName = planRecord.getString("planName");
		return companyPlanName;
	}
	
	public static String getCompanyId(String companyEmail) {
		String companyId = null;
		Document companyRecord = getCompanyRecord(companyEmail);
		if(companyRecord == null)
			return null;
		ObjectId companyObjectId = companyRecord.getObjectId(DatabaseConstants._ID);
		companyId = companyObjectId.toHexString();
		log.debug("getCompanyId() : input : companyEmail "+companyEmail+" : Output : companyId :"+companyId);
		return companyId;
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
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally {
			mongoClient.close();
		}
		return planList;
	}
	
	public static Long getRemainingScanCount(String companyEmail) {
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
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		return remainingScanCount;
	}
	
	public static Long getRemainingRecordCount(String companyId) {
		Long remainingRecordCount = null;
		try {
			Document companyPlanRecord = ProductValidationSystemReadService.getCompanyPlanRecord(companyId);
			log.debug("companyPlanRecord : "+companyPlanRecord);
			if(companyPlanRecord != null) {
				remainingRecordCount = Long.parseLong(companyPlanRecord.getString("remainingRecordCount"));
				log.debug("remainingRecordCount : "+remainingRecordCount);
			}
			return remainingRecordCount;
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		return remainingRecordCount;
	}
	
	public static Document getProductTemplateRecord(String productTemplateId, String productType) {
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			String collectionName = CommonUtils.getProductTemplateCollectionName(productType);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, collectionName);		
			Document searchCriteria = new Document().append(DatabaseConstants._ID, new ObjectId(productTemplateId));
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents != null) {
				return documents.first();
			}
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally {
			mongoClient.close();
		}
		return null;
	}
	public static List<Document> getProductListViewRecord(String productTemplateId,String productType){
		MongoClient mongoClient =null;
		List<Document> productList = new ArrayList<Document>();
		try{
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			String collectionName =CommonUtils.getProductCollectionName(productType);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, collectionName);		
			Document searchCriteria = new Document().append(DatabaseConstants.PRODUCT_TEMPLATE_ID, productTemplateId);
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			MongoCursor<Document> productListViewDocumentIterator = documents.iterator();
			while(productListViewDocumentIterator != null && productListViewDocumentIterator.hasNext()) {
				Document productListViewTemplateDocument = productListViewDocumentIterator.next();
				productListViewTemplateDocument.append("productType", productType);
				productList.add(productListViewTemplateDocument);
			}
			return productList;
		}
		catch(Exception e){
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally{
			mongoClient.close();
		}
		return productList;
	}
	
	public static List<Document> getAllCompanyUserStatus() {
		MongoClient mongoClient =null;
		List<Document> userList = new ArrayList<Document>();
		try{
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_COLLECTION_NAME);		
			Document searchCriteria = new Document().append(DatabaseConstants.CATEGORY_ID, "company");
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			MongoCursor<Document> userListViewDocumentIterator = documents.iterator();
			while(userListViewDocumentIterator != null && userListViewDocumentIterator.hasNext()) {
				Document productListViewTemplateDocument = userListViewDocumentIterator.next();
				userList.add(productListViewTemplateDocument);
			}
			return userList;
		}
		catch(Exception e){
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally{
			mongoClient.close();
		}
		return userList;
	}

	public static String getProductType(String productTemplateId) {
		
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_TEMPLATE_COLLECTION);		
			Document searchCriteria = new Document().append("productTemplateId", productTemplateId);
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			if(documents != null) {
				return documents.first().getString("productType");
			}
			
		}
		catch(Exception e){
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally{
			mongoClient.close();
		}
		return null;
	}
	
	public static int getNumberofRegisteredProduct(String productTemplateId, String productType){
		MongoClient mongoClient = null;
		int c=0;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			String collectionName = CommonUtils.getProductCollectionName(productType);
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, collectionName);		
			Document searchCriteria = new Document().append("productTemplateId", productTemplateId);
			c= (int) mongoCollection.count(searchCriteria);
			
			return c;
			
		}
		catch(Exception e){
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
		}
		finally{
			mongoClient.close();
		}
		return c;
	}
	public static List<Document> getAppUserDetails(String productTemplateId) {
		MongoClient mongoClient = null;
		List<Document> userDetails = new ArrayList<Document>();
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.USERDETAILS_COLLECTION_NAME);		
			Document searchCriteria = new Document().append("productTemplateId", productTemplateId);
			FindIterable<Document> documents = mongoCollection.find(searchCriteria);
			MongoCursor<Document> userDetailDocumentIterator = documents.iterator();
			while(userDetailDocumentIterator != null && userDetailDocumentIterator.hasNext()) {
				Document companyTemplateDocument = userDetailDocumentIterator.next();
				userDetails.add(companyTemplateDocument);
			}	
			return userDetails;
		} catch (Exception e) {
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
			
		}
		finally {
			mongoClient.close();
		}
		return null;
	}
	
 
}
