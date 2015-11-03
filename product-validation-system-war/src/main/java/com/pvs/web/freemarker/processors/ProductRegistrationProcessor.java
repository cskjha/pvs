package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.Document;

import com.pvs.service.write.ProductValidationSystemWriteService;
import com.pvs.web.utilities.ProductRegistrationUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;

public class ProductRegistrationProcessor {
	public static String getHTML(Request request) {
		String htmlOutput = null;
		String userName = request.session().attribute("user");
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(CompanyRegistrationProcessor.class, "/");
		try {
			Template template = configuration.getTemplate("com/pvs/freemarker/templates/productregistration/productRegistrationGET.ftl");
			StringWriter stringWriter = new StringWriter();
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
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
	
	
	public static String postHTML(Request request) {
		String htmlOutput = null;
		
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(CompanyRegistrationProcessor.class, "/");
		Set<String> queryParams = request.queryParams();
		Document productDocument = new Document();
		for(String param: queryParams) {
			if(param.equals("productName") || param.startsWith("field")) {
				productDocument.append(param, request.queryParams(param));
			}
		}
		String QRCode = ProductRegistrationUtil.generateQRCode();
		productDocument.append("QRCode", QRCode);
		ProductValidationSystemWriteService.registerProduct(productDocument);	
		try {
			String userName = request.session().attribute("user");
			Template template = configuration.getTemplate("com/pvs/freemarker/templates/productregistration/productRegistrationPOST.ftl");
			StringWriter stringWriter = new StringWriter();
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
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
