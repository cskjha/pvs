package com.pvs.web.freemarker.processors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.update.ProductValidationSystemUpdateService;
import com.pvs.service.utils.CommonUtils;
import com.pvs.service.write.ProductValidationSystemWriteService;
import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.FormUploadedFileReaderUtil;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class EditProductTemplateProcessor {
	final static Logger log = Logger.getLogger(EditProductTemplateProcessor.class);
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String companyName = request.session().attribute("companyName");
		String companyId = request.session().attribute("companyId");
		String category = request.session().attribute("category");
		String companyEmail=null;
		Document companyResult= ProductValidationSystemReadService.getCompanyEmail(companyName,companyId);
		if(companyResult != null) {
			companyEmail=companyResult.getString("companyEmail");
		}
		String locale = ProcessorUtil.getLanguage(request);
		Map<String, Object> dynamicValues = new HashMap<String, Object>();
		ProcessorUtil.populateDynamicValues(dynamicValues);
		dynamicValues.put("companyName", companyName);
		dynamicValues.put("category", category);
		boolean userStatus= ProductValidationSystemReadService.userStatus(companyEmail);
		if(userStatus == true){
			String productTemplateId=request.queryParams("productTemplateId");
			String productType =ProductValidationSystemReadService.getProductType(productTemplateId);
			Document productTemplateDocument = ProductValidationSystemReadService.getProductTemplateRecord(productTemplateId, productType);
			log.debug("productTemplateDocument : "+productTemplateDocument);
			if(productTemplateDocument != null) {
				String productName = productTemplateDocument.getString("productName");
				String manufacturerName = productTemplateDocument.getString("manufacturerName");
				String imageURL = productTemplateDocument.getString("image").toString();
				int fieldCount = 1;
				Map<String, String> fieldMap = new HashMap<String, String>();
				for(String key : productTemplateDocument.keySet()) {
					if(key.startsWith("field")) {
						fieldMap.put(key, productTemplateDocument.getString(key));
					}
				}
				
				dynamicValues.put("productName", productName);
				dynamicValues.put("manufacturerName", manufacturerName);
				dynamicValues.put("imageURL", imageURL);
				dynamicValues.put("productType", productType);
				dynamicValues.put("productTemplateId", productTemplateId);
				dynamicValues.put("companyId", companyId);
				dynamicValues.put("fieldMap", fieldMap);	
//				log.debug("Product Type : "+productType);
//				if("FP".equals(productType)) {
//					dynamicValues.put("foodProduct", "TRUE");
//				}
				try {
					htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.PRODUCT_TEMPLATE_REGISTRATION_GET,dynamicValues, EditProductTemplateProcessor.class, locale);
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
		else{
			htmlOutput="You are blocked by Admin.please contact with admin";
		}
		return htmlOutput;
	}
	public static String postHTML(Request request, Response response) {
		String htmlOutput = null;
		String productname = null;
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		
		String locale = ProcessorUtil.getLanguage(request);
		String companyId = request.session().attribute("companyId");
		String companyName = request.session().attribute("companyName");
		String category = request.session().attribute("category");
		String companyEmail=null;
		Document companyResult= ProductValidationSystemReadService.getCompanyEmail(companyName,companyId);
		if(companyResult != null) {
			companyEmail=companyResult.getString("companyEmail");
		}
		
		Set<String> queryParams = request.queryParams();
		Document productTemplateDocument = new Document();
		//Reading file from forms parameter
		Map<String, Map<String, Object>> fileMap = new FormUploadedFileReaderUtil().getMultipleFiles(request);
		Map<String, Object> allFileDataMap = null;
		Map<String, Object> allOtherParams = null;
		if(fileMap != null) {
			allFileDataMap = fileMap.get("files");
			allOtherParams = fileMap.get("params");
		}
		String productType = (String)allOtherParams.get("productType");
		String productTemplateId = (String)allOtherParams.get("productTemplateId");
		log.debug("Product Type : "+productType);
		for(String param: allOtherParams.keySet()) {
			if(param.equals("productName") || param.equals("manufacturerName") || param.equals("manufacturedOn") ||  param.startsWith("field") 
					|| ("FP".equals(productType) && param.equals("expirationDate"))) {
				productTemplateDocument.append(param, allOtherParams.get(param));
				if(param.equals("productName")){
					productname=allOtherParams.get(param).toString();
				}
				
			}
			
		}
		
		for(String fileName : allFileDataMap.keySet()) {
			  InputStream inputStream = (InputStream)allFileDataMap.get(fileName);
			  String directoryPath = ProductValidationSystemWebConstants.PRODUCT_IMAGE_ROOT_PATH;
			  File directory = new File(directoryPath);
			  if(!directory.exists()) {
				  directory.mkdirs();
			  }
			  FileOutputStream fileOutputStream;
			try {
				fileOutputStream = new FileOutputStream(directoryPath+fileName);
				 IOUtils.copy(inputStream, fileOutputStream);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			  String imageURL = request.protocol().substring(0,  request.protocol().indexOf('/')).toLowerCase()+"://"+request.host()+request.contextPath()+"/"+fileName;
			  log.debug("imageURL"+imageURL);
			  productTemplateDocument.append("image", imageURL);	
		}	
		new CommonUtils().addHistoryFields(productTemplateDocument);
		ProductValidationSystemUpdateService.updateProductTemplate(productTemplateDocument ,productTemplateId, productType, companyId);	
		try {
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			ProcessorUtil.populateDynamicValues(dynamicValues);
			dynamicValues.put("companyName", companyName);
			dynamicValues.put("category", category);
			String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
			Document auditDocument = new Document();
			auditDocument.append("companyName", companyName);
			auditDocument.append("username", companyEmail);
			auditDocument.append("status", "one new product "+productname +" template has been successfully updated");
			auditDocument.append("time", timeStamp);
			ProductValidationSystemWriteService.updateCompanyAuditTable(auditDocument);
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
