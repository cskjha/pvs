package com.pvs.service.delete;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pvs.db.connection.ConnectionManagerFactory;
import com.pvs.db.connection.DBCollectionManagerFactory;
import com.pvs.db.connection.DatabaseManagerFactory;
import com.pvs.db.connection.utils.DatabaseConstants;
import com.pvs.service.update.ProductValidationSystemUpdateService;
import com.pvs.service.utils.CommonUtils;
import com.pvs.service.write.ProductValidationSystemWriteService;

public class ProductValidationSystemDeleteService {

	static final Logger log = Logger.getLogger(ProductValidationSystemDeleteService.class);


	public static boolean removeProductTemplate(String productTemplateId,String productType) {
		
		if(productTemplateId ==null || productType == null) {
			//log.debug("Any parameter is null : productType : "+productType+" : productModel :"+companyName+" : productName :"+productName);
			return false;
		}
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			String collectionName = CommonUtils.getProductTemplateCollectionName(productType);
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, collectionName);		
			Document searchCriteria = new Document().append(DatabaseConstants._ID, new ObjectId(productTemplateId));
			mongoCollection.deleteOne(searchCriteria);
			
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

	public static boolean removeUser(String companyName, String userName) {
		
		if(companyName ==null || userName == null ) {
			return false;
		}
		
		MongoClient mongoClient = null;
		try {
			mongoClient = ConnectionManagerFactory.getMongoClient();
			MongoDatabase mongoDb = DatabaseManagerFactory.getDatabase(mongoClient, DatabaseConstants.DATABASE_NAME);
			MongoCollection<Document> mongoCollection = DBCollectionManagerFactory.getOrCreateCollection(mongoDb, DatabaseConstants.COMPANY_COLLECTION_NAME);		
			Document searchCriteria = new Document().append("companyEmail", userName).append("companyName", companyName);
			mongoCollection.deleteOne(searchCriteria);
			log.debug("User is removed successfully");
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
