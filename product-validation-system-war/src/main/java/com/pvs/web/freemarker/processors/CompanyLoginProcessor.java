package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.LoginValidator;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;

public class CompanyLoginProcessor {
	public static String getHTML(Request request) {
		String htmlOutput = null;
		try {
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.COMPANY_LOGIN, dynamicValues, CompanyLoginProcessor.class);	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		return htmlOutput;
	}
	public static String postHTML(Request request, Response response) {
		String htmlOutput = null;
		try {
			boolean validateLogin = LoginValidator.validateLogin(request);
			if(validateLogin) {
				response.redirect(RedirectPaths.DISPLAY_PLAN);
			}			
			else {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				dynamicValues.put("errorMessage", ProductValidationSystemWebConstants.LOGIN_FAILURE_MESSAGE);
				htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.COMPANY_LOGIN, dynamicValues, CompanyLoginProcessor.class);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
		
		return htmlOutput;
	}

}
