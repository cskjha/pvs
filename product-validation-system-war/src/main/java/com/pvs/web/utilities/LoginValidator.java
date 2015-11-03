package com.pvs.web.utilities;

import org.slf4j.Logger;

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
			String userName = request.queryParams("email");
			String password = request.queryParams("password");
			if(ProductValidationSystemReadService.validateUser(userName, password)){
				session = request.session(true);
				session.attribute("user",userName);
				return true;
			}
		}
		return false;
	}
	public static String getLoggedInUserName(Request request) {
		Session session = request.session(false);
		String userName = null;
		if(session != null) {
			userName = session.attribute("user");
		}
		return userName;
	}
	
	public static void invalidateSession(Request request) {
		Session session = request.session(false);
		if(session != null) {
			session.invalidate();
		}	
	}
}
