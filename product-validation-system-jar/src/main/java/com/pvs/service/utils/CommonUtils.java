package com.pvs.service.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.pvs.db.connection.utils.DatabaseConstants;
import com.pvs.enums.ProductTypes;

public class CommonUtils {
	static final Logger log = Logger.getLogger(CommonUtils.class);
	public static String getProductCollectionName(String productType) {
		String collectionName=null;
		if(ProductTypes.FOOD_PRODUCTS.getProductType().equals(productType)) {
			collectionName = DatabaseConstants.FOOD_PRODUCT_COLLECTION_NAME;
		}
		else {
			collectionName = DatabaseConstants.NON_FOOD_PRODUCT_COLLECTION_NAME;
		}
		return collectionName;
	}
	public static String getProductTemplateCollectionName(String productType) {
		String collectionName=null;
		if(ProductTypes.FOOD_PRODUCTS.getProductType().equals(productType)) {
			collectionName = DatabaseConstants.FOOD_PRODUCT_TEMPLATE_COLLECTION_NAME;
		}
		else {
			collectionName = DatabaseConstants.NON_FOOD_PRODUCT_TEMPLATE_COLLECTION_NAME;
		}
		return collectionName;
	}
	
	
	public void addHistoryFields(Document productDocument) {
		String pattern = "dd-MM-yyyy";
        String dateInString =new SimpleDateFormat(pattern).format(new Date());
        productDocument.append("creationDate", dateInString);
	}
}
