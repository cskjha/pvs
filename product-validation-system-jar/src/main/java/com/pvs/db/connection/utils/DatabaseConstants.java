package com.pvs.db.connection.utils;

public class DatabaseConstants {
	public static final String DATABASE_NAME ="pvs";
	public static final String COMPANY_COLLECTION_NAME = "companycollection";                 // Stores company information
	public static final String FOOD_PRODUCT_COLLECTION_NAME = "fproductcollection";			  // Stores food products
	public static final String NON_FOOD_PRODUCT_COLLECTION_NAME = "nfproductcollection";	  // Stores non food products
	public static final String FOOD_PRODUCT_TEMPLATE_COLLECTION_NAME = "fproducttemplatecollection";			  // Stores food product templates
	public static final String NON_FOOD_PRODUCT_TEMPLATE_COLLECTION_NAME = "nfproducttemplatecollection";	  	  // Stores non food product templates
	public static final String PRIMARY_KEY_COMPANY_COLLECTION = "companyEmail";				  
	public static final String PLAN_COLLECTION_NAME = "plancollection";						  // Stores plan information
	public static final String COMPANY_PLAN_COLLECTION_NAME = "companyplancollection";		  // Stores plan selected by company and remaining record and scan count
	public static final String PRIMARY_KEY_PLAN_COLLECTION = "_id";
	public static final String PLAN_STATE = "planState";
	public static final String PRODUCT_TEMPLATE_ID = "productTemplateId";
	public static final String PRODUCT_RECORD_COUNT_FOR_TEMPLATE = "productRecordCountForTemplate";
	public static final String PRODUCT_TEMPLATE_ID_START_VALUE = "100001";
	public static final String PRODUCT_TEMPLATES = "productTemplates";
	public static final String _ID = "_id";
	public static final String COMPANY_TEMPLATE_COLLECTION = "companytemplatecollection";		// Stores companyEmail, productTemplateId (key from f/nf product template collection)
	public static final String PRODUCT_TYPE = "productType";
	public static final String COMPANY_ID = "companyId";
	public static final String RATING_COLLECTION_NAME="ratingcollection";
	public static final String COMPANY_AUDIT_COLLECTION_NAME="companyauditcollection";
	public static final String CATEGORY_ID="category";
	public static final String USERDETAILS_COLLECTION_NAME="userdetailscollection";
}
