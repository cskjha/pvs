package com.pvs.web.freemarker.processors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.utils.CommonUtils;
import com.pvs.service.valueobjects.ProductTemplateVO;
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
		boolean userStatus= ProductValidationSystemReadService.userStatus(companyEmail);
		if(userStatus == true){
			
			Long remainingRecordCount = ProductValidationSystemReadService.getRemainingRecordCount(companyId);
			if(remainingRecordCount!=null && (long)remainingRecordCount <= 0L ) {
					
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
			List<ProductTemplateVO> productTemplateVOList = new ArrayList<ProductTemplateVO>();
			List<Document> productTemplates = ProductValidationSystemReadService.getCompanyTemplateRecords(companyId);
			
			Iterator<Document> productTemplateIterator = productTemplates.iterator();
			while(productTemplateIterator.hasNext()) {
				Document productTemplate = productTemplateIterator.next();
				String productTemplateId = productTemplate.getObjectId("_id").toHexString();
				String productTemplateName = productTemplate.getString("productName");
				String productType = productTemplate.getString("productType");
				ProductTemplateVO productTemplateVO = new ProductTemplateVO();
				productTemplateVO.setProductTemplateId(productTemplateId);
				productTemplateVO.setProductTemplateName(productTemplateName);
				productTemplateVO.setProductType(productType);
				productTemplateVOList.add(productTemplateVO);
			}
					
			dynamicValues.put("productTemplateList", productTemplateVOList);
				
				ProcessorUtil.populateDynamicValues(dynamicValues);
				dynamicValues.put("companyName", companyName);
				dynamicValues.put("category", category);
			try {
				htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.PRODUCT_TEMPLATE_REGISTRATION_GET,
						dynamicValues, ProductRegistrationProcessor.class, locale);
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		//if user_status is disable i.e. blocked by admin
		else{
			/*
			dynamicValues.put("user_status", "disable");
			try {
				htmlOutput=ProcessorUtil.populateTemplate(TemplatePaths.PRODUCT_TEMPLATE_REGISTRATION_POST,dynamicValues, ProductRegistrationProcessor.class, locale);
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			htmlOutput="You are blocked by Admin.please contact with admin";
		}
		return htmlOutput;
	}
	
	
	public static String postHTML(Request request, Response response) throws IOException {
		String htmlOutput = null;
		String productname = null;
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		
		String locale = ProcessorUtil.getLanguage(request);
		String companyId = request.session().attribute("companyId");
		Long remainingRecordCount = ProductValidationSystemReadService.getRemainingRecordCount(companyId);
		String companyName = request.session().attribute("companyName");
		String category = request.session().attribute("category");
		String companyEmail=null;
		Document companyResult= ProductValidationSystemReadService.getCompanyEmail(companyName,companyId);
		if(companyResult != null) {
			companyEmail=companyResult.getString("companyEmail");
		}
		if(remainingRecordCount!=null && (long)remainingRecordCount <= 0L ) {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				dynamicValues.put("companyName", userName);
				dynamicValues.put("category", category);
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
		String productType = (String)allOtherParams.get("productType");
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
			  FileOutputStream fileOutputStream = new FileOutputStream(directoryPath+fileName);
			  IOUtils.copy(inputStream, fileOutputStream);
			  String imageURL = request.protocol().substring(0,  request.protocol().indexOf('/')).toLowerCase()+"://"+request.host()+request.contextPath()+"/"+fileName;
			  log.debug("imageURL"+imageURL);
			  productTemplateDocument.append("image", imageURL);	
		}	
		new CommonUtils().addHistoryFields(productTemplateDocument);
		ProductValidationSystemWriteService.registerProductTemplate(productTemplateDocument, productType, companyId);	
		try {
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			ProcessorUtil.populateDynamicValues(dynamicValues);
			dynamicValues.put("companyName", companyName);
			dynamicValues.put("category", category);
			String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
			Document auditDocument = new Document();
			auditDocument.append("companyName", companyName);
			auditDocument.append("username", companyEmail);
			auditDocument.append("status", "one new product "+productname +" template has been successfully registered");
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
