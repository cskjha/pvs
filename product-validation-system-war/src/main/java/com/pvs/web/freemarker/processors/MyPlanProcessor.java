package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class MyPlanProcessor {
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(CompanyLoginProcessor.class, "/");
		try {
			Session session = request.session(false);
			if(session == null) {
				response.redirect("companylogin");
				return null;
			}
			else {
				String planName = request.session().attribute("companyPlan");
				String userName = request.session().attribute("user");
				Template template = configuration.getTemplate("com/pvs/freemarker/templates/chooseplan/myPlan.ftl");
				StringWriter stringWriter = new StringWriter();
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				dynamicValues.put("planName", planName);
				dynamicValues.put("user", userName);
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
