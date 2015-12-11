package com.pvs.web.freemarker.processors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
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

public class ProductTemplateRegistrationProcessor {
	
	final static Logger log = Logger.getLogger(ProductTemplateRegistrationProcessor.class);
	
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
	
	
	public static String postHTML(Request request, Response response) throws IOException {
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
		//Reading file from forms parameter
		Map<String, Map<String, Object>> fileMap = new FormUploadedFileReaderUtil().getMultipleFiles(request);
		Map<String, Object> allFileDataMap = null;
		Map<String, Object> allOtherParams = null;
		if(fileMap != null) {
			allFileDataMap = fileMap.get("files");
			allOtherParams = fileMap.get("params");
		}
		for(String param: allOtherParams.keySet()) {
			if(param.equals("productName") || param.equals("manufacturerName") ||  param.startsWith("field")) {
				productTemplateDocument.append(param, allOtherParams.get(param));
			}
		}
		for(String fileName : allFileDataMap.keySet()) {
			  InputStream inputStream = (InputStream)allFileDataMap.get(fileName);
			  String directoryPath = ProductValidationSystemWebConstants.PRODUCT_IMAGE_ROOT_PATH;
			  File directory = new File(directoryPath);
			  if(!directory.exists()) {
				  directory.mkdirs();
			  }
			  FileOutputStream fileOutputStream = new FileOutputStream(directoryPath+fileName);
			  IOUtils.copy(inputStream, fileOutputStream);
			  String imageURL = request.protocol().substring(0,  request.protocol().indexOf('/')).toLowerCase()+"://"+request.host()+request.contextPath()+"/"+fileName;
			  log.debug("imageURL"+imageURL);
			  productTemplateDocument.append("image", imageURL);	
		}	
		
		String productType = (String)allOtherParams.get("productType");
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
