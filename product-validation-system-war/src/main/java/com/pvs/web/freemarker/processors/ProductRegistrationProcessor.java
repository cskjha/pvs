package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.utils.CommonUtils;
import com.pvs.service.write.ProductValidationSystemWriteService;
import com.pvs.web.constants.ProductValidationSystemWebConstants;
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
		String companyName = request.session().attribute("companyName");
		String companyId = request.session().attribute("companyId");
		String category = request.session().attribute("category");
		String companyEmail=null;
		Document companyResult= ProductValidationSystemReadService.getCompanyEmail(companyName,companyId);
		if(companyResult != null) {
			companyEmail=companyResult.getString("companyEmail");
		}
		String locale = ProcessorUtil.getLanguage(request);
		Long remainingRecordCount = ProductValidationSystemReadService.getRemainingRecordCount(companyId);
		boolean userStatus= ProductValidationSystemReadService.userStatus(companyEmail);
		if(userStatus == true){
			if(remainingRecordCount==null || (long)remainingRecordCount <= 0L ) {
					Map<String, Object> dynamicValues = new HashMap<String, Object>();
					ProcessorUtil.populateDynamicValues(dynamicValues);
					dynamicValues.put("companyName", companyName);
					dynamicValues.put("category", category);
					try {
						return ProcessorUtil.populateTemplate(TemplatePaths.RECORD_BALANCE_UNAVAILABLE, dynamicValues, ProductRegistrationProcessor.class, locale);
					} catch (TemplateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			else {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				dynamicValues.put("companyName", companyName);
				dynamicValues.put("category", category);
				Document productTemplateDocument = ProductValidationSystemReadService.getProductTemplateRecord(productTemplateId, productType);
				log.debug("productTemplateDocument : "+productTemplateDocument);
				if(productTemplateDocument != null) {
					String productName = productTemplateDocument.getString("productName");
					int fieldCount = 1;
					Map<String, String> fieldMap = new HashMap<String, String>();
					for(String key : productTemplateDocument.keySet()) {
						if(key.startsWith("field")) {
							fieldMap.put(key, productTemplateDocument.getString(key));
						}
					}
					String expirationDate = productTemplateDocument.getString("expirationDate");
					if(expirationDate != null) {
						fieldMap.put("expirationDate", expirationDate);
					}
					dynamicValues.put("productName", productName);
					dynamicValues.put("productType", productType);
					dynamicValues.put("productTemplateId", productTemplateId);
					dynamicValues.put("companyId", companyId);
					dynamicValues.put("fieldMap", fieldMap);	
	//				log.debug("Product Type : "+productType);
	//				if("FP".equals(productType)) {
	//					dynamicValues.put("foodProduct", "TRUE");
	//				}
					try {
						htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.PRODUCT_REGISTRATION_GET,
								dynamicValues, ProductRegistrationProcessor.class, locale);
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
			
		}
		else{
			htmlOutput="You are blocked by Admin.please contact with admin";
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
		log.debug("request.contextPath() : "+request.contextPath());
		log.debug("request.servletPath() : "+request.servletPath());
		String companyId = request.session().attribute("companyId");
		Long remainingRecordCount = ProductValidationSystemReadService.getRemainingRecordCount(companyId);
		String companyName = request.session().attribute("companyName");
		String category = request.session().attribute("category");
		String companyEmail=null;
		Document companyResult= ProductValidationSystemReadService.getCompanyEmail(companyName,companyId);
		if(companyResult != null) {
			companyEmail=companyResult.getString("companyEmail");
		}
		if(remainingRecordCount==null || (long)remainingRecordCount <= 0L ) {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				dynamicValues.put("companyName", companyName);
				dynamicValues.put("category", category );
				try {
					return ProcessorUtil.populateTemplate(TemplatePaths.RECORD_BALANCE_UNAVAILABLE, dynamicValues, ProductRegistrationProcessor.class, locale);
				} catch (TemplateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else {
			Set<String> queryParams = request.queryParams();
			Document productDocument = new Document();
			for(String param: queryParams) {
				if(param.startsWith("field")) {
					int indexFieldNameParam = param.indexOf("Name");
					if(indexFieldNameParam > 0) {
						String fieldName = request.queryParams(param);
						String fieldValue = request.queryParams(param.substring(0, indexFieldNameParam)+"Value");
						log.debug("FieldName : "+fieldName+" Field Value : "+fieldValue);
						productDocument.append(fieldName, fieldValue);
					}
				}
				else if(param.equals("productName")) {
					productDocument.append(param, request.queryParams(param));
				}
				else if(param.equals("manufacturedOn")) {
					productDocument.append(param, request.queryParams(param));
				}
				else if(param.equals("expirationDate")) {
					productDocument.append(param, request.queryParams(param));
				}
			}
			String productType = request.queryParams("productType");
			String productTemplateId = request.queryParams("productTemplateId");
			companyId = request.queryParams("companyId");
			productDocument.append("productTemplateId", productTemplateId);
			productDocument.append("companyId", companyId);
			productDocument.append("ResponseCode", ProductValidationSystemWebConstants.INITIAL_PRODUCT_RESPONSE_CODE);
			new CommonUtils().addHistoryFields(productDocument);
			String productId = ProductValidationSystemWriteService.registerProduct(productDocument, productType, companyId);
//			String hostName = request.host();
//			String contextRoot = request.contextPath();
			//String qrCodeImagefilePath = ProductRegistrationUtil.generateQRCode(hostName+contextRoot, productId, productType);
			try {		
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				//dynamicValues.put("companyName", companyName);
				//dynamicValues.put("qrCodeImagefilePath",qrCodeImagefilePath+".png");
				dynamicValues.put("companyName", companyName);
				dynamicValues.put("category", category);
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
				Document auditDocument = new Document();
				auditDocument.append("companyName", companyName);
				auditDocument.append("username", companyEmail);
				auditDocument.append("status", "one new product with productId"+productId +" has been successfully registered");
				auditDocument.append("time", timeStamp);
				ProductValidationSystemWriteService.updateCompanyAuditTable(auditDocument);
				htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.PRODUCT_REGISTRATION_POST,
						dynamicValues, ProductRegistrationProcessor.class, locale);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}		
		}
		return htmlOutput;
	}
}
