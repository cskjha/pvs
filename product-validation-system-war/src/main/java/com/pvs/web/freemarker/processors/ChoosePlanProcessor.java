package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;

public class ChoosePlanProcessor {
	public static String getHTML(Request request) {
		String htmlOutput = null;
		
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(CompanyLoginProcessor.class, "/");
		try {
			String planName = request.queryParams("plan");
			String userName = request.session().attribute("user");
			Template template = configuration.getTemplate("com/pvs/freemarker/templates/chooseplan/choosePlan.ftl");
			StringWriter stringWriter = new StringWriter();
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			dynamicValues.put("planName", planName);
			dynamicValues.put("user", userName);
			template.process(dynamicValues, stringWriter);
			htmlOutput = stringWriter.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
		
		return htmlOutput;
	}
}
