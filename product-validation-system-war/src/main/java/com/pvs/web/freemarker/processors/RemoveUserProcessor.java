package com.pvs.web.freemarker.processors;

import org.apache.log4j.Logger;

import com.pvs.service.delete.ProductValidationSystemDeleteService;
import com.pvs.web.constants.RedirectPaths;

import spark.Request;
import spark.Response;
import spark.Session;

public class RemoveUserProcessor {
	
	final static Logger log = Logger.getLogger(RemoveUserProcessor.class);
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String userName = request.queryParams("username");
		String companyName = request.queryParams("companyname");
		ProductValidationSystemDeleteService.removeUser(companyName,userName);			
		response.redirect(RedirectPaths.DISPLAY_USERLIST);
		return htmlOutput;
	}

}
