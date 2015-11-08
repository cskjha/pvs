package com.pvs.db.connection.utils;

public class DatabaseConstants {
	public static final String DATABASE_NAME ="pvs";
	public static final String COMPANY_COLLECTION_NAME = "companycollection";                 // Stores company information
	public static final String FOOD_PRODUCT_COLLECTION_NAME = "fproductcollection";			  // Stores food products
	public static final String NON_FOOD_PRODUCT_COLLECTION_NAME = "nfproductcollection";	  // Stores non food products
	public static final String PRIMARY_KEY_COMPANY_COLLECTION = "companyEmail";				  
	public static final String PLAN_COLLECTION_NAME = "plancollection";						  // Stores plan information
	public static final String COMPANY_PLAN_COLLECTION_NAME = "companyplancollection";		  // Stores plan selected by company and remaining record and scan count
	public static final String PRIMARY_KEY_PLAN_COLLECTION = "_id";
	public static final String PLAN_STATE = "planState";

}
