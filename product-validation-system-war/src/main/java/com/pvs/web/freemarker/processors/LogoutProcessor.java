package com.pvs.web.freemarker.processors;

import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.utilities.LoginValidator;

import spark.Request;
import spark.Response;

public class LogoutProcessor {
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		LoginValidator.invalidateSession(request);
		response.redirect(RedirectPaths.COMPANY_LOGIN);			
		return htmlOutput;
	}
}
