package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.utils.CommonUtils;
import com.pvs.service.write.ProductValidationSystemWriteService;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class ProductTemplateRegistrationProcessor {
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String locale = ProcessorUtil.getLanguage(request);
		String userName = request.session().attribute("companyName");
		String companyId = request.session().attribute("companyId");
		Long remainingRecordCount = ProductValidationSystemReadService.getRemainingRecordCount(companyId);
		if(remainingRecordCount!=null && (long)remainingRecordCount <= 0L ) {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				dynamicValues.put("companyName", userName);
				try {
					return ProcessorUtil.populateTemplate(TemplatePaths.RECORD_BALANCE_UNAVAILABLE, dynamicValues, ProductRegistrationProcessor.class, locale);
				} catch (TemplateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			ProcessorUtil.populateDynamicValues(dynamicValues);
			dynamicValues.put("companyName", userName);
			try {
				htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.PRODUCT_TEMPLATE_REGISTRATION_GET,
						dynamicValues, ProductRegistrationProcessor.class, locale);
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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
		String locale = ProcessorUtil.getLanguage(request);
		String companyId = request.session().attribute("companyId");
		Long remainingRecordCount = ProductValidationSystemReadService.getRemainingRecordCount(companyId);
		String userName = request.session().attribute("companyName");
		if(remainingRecordCount!=null && (long)remainingRecordCount <= 0L ) {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				dynamicValues.put("companyName", userName);
				try {
					return ProcessorUtil.populateTemplate(TemplatePaths.RECORD_BALANCE_UNAVAILABLE, dynamicValues, ProductRegistrationProcessor.class, locale);
				} catch (TemplateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		Set<String> queryParams = request.queryParams();
		Document productTemplateDocument = new Document();
		for(String param: queryParams) {
			if(param.equals("productName") || param.startsWith("field")) {
				productTemplateDocument.append(param, request.queryParams(param));
			}
		}
		String productType = request.queryParams("productType");
		new CommonUtils().addHistoryFields(productTemplateDocument);
		ProductValidationSystemWriteService.registerProductTemplate(productTemplateDocument, productType, companyId);	
		try {
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			ProcessorUtil.populateDynamicValues(dynamicValues);
			dynamicValues.put("companyName", userName);
			htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.PRODUCT_TEMPLATE_REGISTRATION_POST,
					dynamicValues, ProductRegistrationProcessor.class, locale);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
}
