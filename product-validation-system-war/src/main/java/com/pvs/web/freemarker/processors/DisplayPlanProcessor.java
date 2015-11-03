package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Session;

public class DisplayPlanProcessor {
	public static String getHTML(Request request) {
		String htmlOutput = null;
		
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(CompanyRegistrationProcessor.class, "/");
		try {			
				Template template = configuration.getTemplate("com/pvs/freemarker/templates/displayplan/displayPlan.ftl");
				StringWriter stringWriter = new StringWriter();
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				Session session = request.session(false);
				if(session != null) {
					dynamicValues.put("user", session.attribute("user"));
				}
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
