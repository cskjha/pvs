package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.pvs.service.write.ProductValidationSystemWriteService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;

public class CompanyRegistrationProcessor {
	public static String getHTML(Request request) {
		String htmlOutput = null;
		
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(CompanyRegistrationProcessor.class, "/");
		try {
			Template template = configuration.getTemplate("com/pvs/freemarker/templates/registration/companyRegistrationGET.ftl");
			StringWriter stringWriter = new StringWriter();
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
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
		String companyName = request.queryParams("companyName");
		String companyEmail = request.queryParams("email");
		String companyPassword = request.queryParams("password");
		Document companyDocument = new Document();
		companyDocument.append("companyName", companyName);
		companyDocument.append("companyEmail", companyEmail);
		companyDocument.append("companyPassword", companyPassword);
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(CompanyRegistrationProcessor.class, "/");
		if(ProductValidationSystemWriteService.registerCompany(companyDocument)) {
			Template template;
			try {
				template = configuration.getTemplate("com/pvs/freemarker/templates/registration/companyRegistrationPOST.ftl");
				StringWriter stringWriter = new StringWriter();
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				template.process(dynamicValues, stringWriter);
				htmlOutput = stringWriter.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch(TemplateException e) {
				e.printStackTrace();
			}
		}		
		return htmlOutput;
	}

}
