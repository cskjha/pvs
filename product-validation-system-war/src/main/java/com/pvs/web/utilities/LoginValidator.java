package com.pvs.web.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bson.Document;

import com.pvs.db.connection.utils.DatabaseConstants;
import com.pvs.service.read.ProductValidationSystemReadService;

import spark.Request;
import spark.Session;

public class LoginValidator {
	public static boolean validateLogin(Request request) {
		Session session = request.session(false);
		if(session != null) {
			return true;
		}
		else {
			String password=null;
			String language = request.queryParams("language");
			String userEmail = request.queryParams("email");
			String originalcompanyPassword = request.queryParams("password");
			try {
				MessageDigest m = MessageDigest.getInstance("MD5");
				m.reset();
				m.update(originalcompanyPassword.getBytes());
				byte[] digest = m.digest();
				BigInteger bigInt = new BigInteger(1,digest);
				password = bigInt.toString(16);
				// Now we need to zero pad it if you actually want the full 32 chars.
				while(password.length() < 32 ){
					password = "0"+password;
				}
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(ProductValidationSystemReadService.validateUser(userEmail, password)){
				session = request.session(true);
				if(language != null && language.length() == 2) {
					session.attribute("language", language);
				}
				LoginValidator loginValidator = new LoginValidator();
				loginValidator.initializeSessionData(session, userEmail);
				return true;
			}
		}
		return false;
	}
	public static String getLoggedInUserName(Request request) {
		Session session = request.session(false);
		String userName = null;
		if(session != null) {
			userName = session.attribute("companyName");
		}
		return userName;
	}
	
	public static void invalidateSession(Request request) {
		Session session = request.session(false);
		if(session != null) {
			session.invalidate();
		}	
	}
	public void initializeSessionData(Session session, String userEmail) {
		Document companyRecord = ProductValidationSystemReadService.getCompanyRecord(userEmail);
		String companyName = companyRecord.getString("companyName");
		String companyEmail = companyRecord.getString("companyEmail");
		String companyId = companyRecord.getObjectId(DatabaseConstants._ID).toHexString();
		String category = companyRecord.getString("category");
//		Document planRecord =  ProductValidationSystemReadService.getCompanyPlanRecord(companyEmail);
//		String companyPlanId = null;
//		if(planRecord != null) {
//			companyPlanId = planRecord.getString("companyPlanId");
//		}
//		String companyPlanName = ProductValidationSystemReadService.getCompanyPlanName(companyPlanId);
		session.attribute("companyName", companyName);
		session.attribute("companyEmail", companyEmail);
		session.attribute("companyId", companyId);
		session.attribute("category",category );
	}
}
