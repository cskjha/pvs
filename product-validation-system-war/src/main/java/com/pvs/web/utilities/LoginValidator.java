package com.pvs.web.utilities;

import org.bson.Document;

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
			String userEmail = request.queryParams("email");
			String password = request.queryParams("password");
			if(ProductValidationSystemReadService.validateUser(userEmail, password)){
				session = request.session(true);
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
//		Document planRecord =  ProductValidationSystemReadService.getCompanyPlanRecord(companyEmail);
//		String companyPlanId = null;
//		if(planRecord != null) {
//			companyPlanId = planRecord.getString("companyPlanId");
//		}
//		String companyPlanName = ProductValidationSystemReadService.getCompanyPlanName(companyPlanId);
		session.attribute("companyName", companyName);
		session.attribute("companyEmail", companyEmail);
		
	}
}
