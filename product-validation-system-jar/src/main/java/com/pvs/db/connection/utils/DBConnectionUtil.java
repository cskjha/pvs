package com.pvs.db.connection.utils;

public class DBConnectionUtil {
	public static String generateCompanyCollectionName(String companyEmail) {
		String collectionName = null;
		if(companyEmail != null) {
			collectionName = companyEmail.replace('@', '_').replace('.', '_');
		}
		return collectionName;
	}
	
	public static String generateProductCollectionName(String companyEmail, String productName) {
		String collectionName = null;
		if(companyEmail != null && productName != null) {
			
		}
		return collectionName;
	}
}
