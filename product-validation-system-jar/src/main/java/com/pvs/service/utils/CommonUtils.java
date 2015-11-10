package com.pvs.service.utils;

import org.apache.log4j.Logger;

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
}
