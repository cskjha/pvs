package com.pvs.web.freemarker.processors;

import com.pvs.web.constants.RedirectPaths;

import spark.Request;
import spark.Response;
import spark.Session;

public class LanguageChangeProcessor {
	public static String getHTML(Request request, Response response) {
		    String language = request.queryParams("language");
		    if(language != null && language.length() == 2) {
		    	Session session = request.session(false);
		    	if(session != null) {
		    		request.session().attribute("language", language);
		    	}		    	
		    }
		    response.redirect(RedirectPaths.COMPANY_LOGIN+"?language="+language);
			return null;
	}
}
