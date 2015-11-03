package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.pvs.web.utilities.LoginValidator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;

public class LogoutProcessor {
	public static String getHTML(Request request) {
		String htmlOutput = null;
		LoginValidator.invalidateSession(request);
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(CompanyRegistrationProcessor.class, "/");
		try {
			Template template = configuration.getTemplate("com/pvs/freemarker/templates/login/companyLogin.ftl");
			StringWriter stringWriter = new StringWriter();
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			template.process(dynamicValues, stringWriter);
			htmlOutput = stringWriter.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return htmlOutput;
	}
}
