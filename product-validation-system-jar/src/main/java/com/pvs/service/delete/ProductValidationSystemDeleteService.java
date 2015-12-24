package com.pvs.service.delete;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
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

	@SuppressWarnings("deprecation")
	public static boolean removeProductTemplate(String productType, String productName, String companyName) {
		
		if(productName ==null || productType == null || companyName == null) {
			//log.debug("Any parameter is null : productType : "+productType+" : productModel :"+companyName+" : productName :"+productName);
			return false;
		}
		MongoClient mongoClient =new MongoClient();		
		try {
			String collectionName = CommonUtils.getProductTemplateCollectionName(productType);
			//mongoClient = ConnectionManagerFactory.getMongoClient();
			DB db = mongoClient.getDB(DatabaseConstants.DATABASE_NAME);
			DBCollection collection = db.getCollection(collectionName);
			BasicDBObject query = new BasicDBObject();
			query.append("productName", productName).append("companyName", companyName);
			collection.remove(query);
			
			log.debug("Product Template is removed successfully");
			return true;
		} catch (Exception e) {
			
			log.error("PVS Exception occured : Message :  "+e.getMessage());
			log.error("PVS Exception occured : Stack Trace : "+e.getStackTrace());
			return false;
		}
		finally {
			mongoClient.close();
		}
		
	}
}
