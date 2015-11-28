package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.LoginValidator;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class CompanyLoginProcessor {
	static final Logger log = Logger.getLogger(CompanyLoginProcessor.class);
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		try {
			Session session = request.session(false);
			if(session != null) {
				response.redirect(RedirectPaths.DISPLAY_PLAN);
				return null;
			}
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			ProcessorUtil.populateDynamicValues(dynamicValues);
			Locale locale = request.raw().getLocale();
			htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.COMPANY_LOGIN, dynamicValues, CompanyLoginProcessor.class, locale);	
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
			Session session = request.session(false);
			if(session != null) {
				response.redirect(RedirectPaths.DISPLAY_PLAN);
				return null;
			}
			boolean validateLogin = LoginValidator.validateLogin(request);
			if(validateLogin) {
				response.redirect(RedirectPaths.DISPLAY_PLAN);
				return null;
			}			
			else {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				dynamicValues.put("errorMessage", ProductValidationSystemWebConstants.LOGIN_FAILURE_MESSAGE);
				Locale locale = request.raw().getLocale();
				htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.COMPANY_LOGIN, dynamicValues, CompanyLoginProcessor.class, locale);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
		
		return htmlOutput;
	}

}
