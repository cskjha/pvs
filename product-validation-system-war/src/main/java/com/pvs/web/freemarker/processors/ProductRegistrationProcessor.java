package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.write.ProductValidationSystemWriteService;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class ProductRegistrationProcessor {
	final static Logger log = Logger.getLogger(ProductRegistrationProcessor.class);
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String productTemplateId = request.queryParams("productTemplateId");
		String productType = request.queryParams("productType");
		log.debug("productTemplateId : "+productTemplateId);
		String userName = request.session().attribute("companyName");
		String companyId = request.session().attribute("companyId");
		Long remainingRecordCount = ProductValidationSystemReadService.getRemainingRecordCount(companyId);
		if(remainingRecordCount==null || (long)remainingRecordCount <= 0L ) {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				dynamicValues.put("companyName", userName);
				try {
					return ProcessorUtil.populateTemplate(TemplatePaths.RECORD_BALANCE_UNAVAILABLE, dynamicValues, ProductRegistrationProcessor.class);
				} catch (TemplateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else {
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			dynamicValues.put("companyName", userName);
			Document productTemplateDocument = ProductValidationSystemReadService.getProductTemplateRecord(productTemplateId, productType);
			log.debug("productTemplateDocument : "+productTemplateDocument);
			if(productTemplateDocument != null) {
				String productName = productTemplateDocument.getString("productName");
				int fieldCount = 1;
				String fieldName = productTemplateDocument.getString("field1");
				Map<String, String> fieldMap = new HashMap<String, String>();
				while(fieldName != null) {
					fieldMap.put("field"+fieldCount, fieldName);
					fieldCount++;
					fieldName = productTemplateDocument.getString("field"+fieldCount);
				}
				dynamicValues.put("productName", productName);
				dynamicValues.put("productType", productType);
				dynamicValues.put("productTemplateId", productTemplateId);
				dynamicValues.put("companyId", companyId);
				dynamicValues.put("fieldMap", fieldMap);			
				try {
					htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.PRODUCT_REGISTRATION_GET,
							dynamicValues, ProductRegistrationProcessor.class);
				} catch (TemplateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				response.redirect(RedirectPaths.GENERIC_ERROR_PAGE);
				return null;
			}
		}			
		return htmlOutput;
	}
	
	
	public static String postHTML(Request request, Response response) {
		String htmlOutput = null;
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String companyEmail = request.session().attribute("companyEmail");
		Long remainingRecordCount = ProductValidationSystemReadService.getRemainingRecordCount(companyEmail);
		String userName = request.session().attribute("companyName");
		if((long)remainingRecordCount <= 0L ) {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				dynamicValues.put("companyName", userName);
				try {
					return ProcessorUtil.populateTemplate(TemplatePaths.RECORD_BALANCE_UNAVAILABLE, dynamicValues, ProductRegistrationProcessor.class);
				} catch (TemplateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		Set<String> queryParams = request.queryParams();
		Document productDocument = new Document();
		for(String param: queryParams) {
			if(param.equals("productName") || param.startsWith("field")) {
				productDocument.append(param, request.queryParams(param));
			}
		}
		String productType = request.queryParams("productType");
		productDocument.append("productType", productType);
		ProductValidationSystemWriteService.registerProduct(productDocument, productType, companyEmail);	
		try {
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			dynamicValues.put("companyName", userName);
			htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.PRODUCT_REGISTRATION_POST,
					dynamicValues, ProductRegistrationProcessor.class);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
}
