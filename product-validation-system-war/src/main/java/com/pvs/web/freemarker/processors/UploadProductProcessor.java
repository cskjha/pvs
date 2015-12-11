package com.pvs.web.freemarker.processors;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.pvs.service.excel.ExcelFileHandler;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.FormUploadedFileReaderUtil;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class UploadProductProcessor {
	static final Logger log = Logger.getLogger(UploadProductProcessor.class);
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		try {			
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				Session session = request.session(false);
				String locale = ProcessorUtil.getLanguage(request);
				if(session == null) {
					response.redirect(RedirectPaths.COMPANY_LOGIN);
					return null;
				}
				else {
					String productTemplateId = request.queryParams("productTemplateId");
					String productType = request.queryParams("productType");
					String companyName = session.attribute("companyName");					
					dynamicValues.put("companyName", companyName);
					dynamicValues.put("productTemplateId", productTemplateId);
					dynamicValues.put("productType", productType);
					
					htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.UPLOAD_PRDUCT_FROM_EXCEL_GET, dynamicValues, UploadProductProcessor.class, locale);
					return htmlOutput;
				}		
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
	
	public static String postHTML(Request request, Response response) {
		String htmlOutput = null;
		Map<String, Object> dynamicValues = new HashMap<String, Object>();
		try {	
				ProcessorUtil.populateDynamicValues(dynamicValues);
				Session session = request.session(false);
				String locale = ProcessorUtil.getLanguage(request);
				if(session == null) {
					response.redirect(RedirectPaths.COMPANY_LOGIN);
					return null;
				}
				else {
					Map<String, Map<String, Object>> fileMap = new FormUploadedFileReaderUtil().getMultipleFiles(request);
					Map<String, Object> allFileDataMap = null;
					Map<String, Object> allOtherParams = null;
					if(fileMap != null) {
						allFileDataMap = fileMap.get("files");
						allOtherParams = fileMap.get("params");
					}
					String productTemplateId = request.queryParams("productTemplateId");
					String productType = request.queryParams("productType");
					String companyName = request.session().attribute("companyName");	
					String companyId = request.session().attribute("companyId");
					log.debug("productTemplateId :"+productTemplateId+ " productType :"+productType+ " companyName:"+companyName+" companyId:"+companyId);
					for(String fileName : allFileDataMap.keySet()) {
						  if(fileName != null && fileName.indexOf("xlsx") > 0) {
							  int recordInserted = new ExcelFileHandler().readExcelAndInsertRecord((InputStream)allFileDataMap.get(fileName), productTemplateId, productType, companyId);
							  log.debug("Number of Record Inserted : "+ recordInserted);
						  }
					}
				    dynamicValues.put("companyName", companyName);
					htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.UPLOAD_PRDUCT_FROM_EXCEL_POST, dynamicValues, ViewProductTemplateProcessor.class, locale);
				}
		}catch(Exception ex) {
			log.debug(ex);
		}					
		return htmlOutput;
	} 
}
