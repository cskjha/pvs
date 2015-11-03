package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.utilities.LoginValidator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;

public class CompanyLoginProcessor {
	public static String getHTML(Request request) {
		String htmlOutput = null;
		
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(CompanyLoginProcessor.class, "/");
		try {
			Template template = configuration.getTemplate("com/pvs/freemarker/templates/login/companyLogin.ftl");
			StringWriter stringWriter = new StringWriter();
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			dynamicValues.put("contextRoot", ProductValidationSystemWebConstants.CONTEXT_ROOT);
			template.process(dynamicValues, stringWriter);
			htmlOutput = stringWriter.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		return htmlOutput;
	}
	public static String postHTML(Request request, Response response) {
		String htmlOutput = null;
		
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(CompanyLoginProcessor.class, "/");
		try {
			boolean validateLogin = LoginValidator.validateLogin(request);
			if(validateLogin) {
				response.redirect("displayplan");
			}			
			else {
				Template template = configuration.getTemplate("com/pvs/freemarker/templates/login/companyLogin.ftl");
				StringWriter stringWriter = new StringWriter();
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				dynamicValues.put("errorMessage", ProductValidationSystemWebConstants.LOGIN_FAILURE_MESSAGE);
				template.process(dynamicValues, stringWriter);
				htmlOutput = stringWriter.toString();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
		
		return htmlOutput;
	}

}
