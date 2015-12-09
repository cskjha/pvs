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
		 boolean isMultipart;
		 int maxFileSize = 50 * 1024;
		 //int maxMemSize = 4 * 1024;
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
					String companyId = session.attribute("companyId");
					
					
					// Check that we have a file upload request
				      isMultipart = ServletFileUpload.isMultipartContent(request.raw());
				      if( !isMultipart ){
				         System.out.println("Please upload a file");
				      }
				      DiskFileItemFactory factory = new DiskFileItemFactory();
				      // maximum size that will be stored in memory
				     // factory.setSizeThreshold(maxMemSize);
				      // Location to save data that is larger than maxMemSize.
				     // factory.setRepository(new File("c:\\temp"));

				      // Create a new file upload handler
				      ServletFileUpload upload = new ServletFileUpload(factory);
				      // maximum file size to be uploaded.
				      upload.setSizeMax( maxFileSize );

				      try{ 
				      // Parse the request to get file items.
				      List<FileItem> fileItems = upload.parseRequest(request.raw());
					  log.debug("Parse File Workd : File List : "+fileItems);
				      // Process the uploaded file items
				      Iterator<FileItem> fileNameIterator = fileItems.iterator();
				      while ( fileNameIterator.hasNext () ) 
				      {
				         FileItem fileItem = (FileItem)fileNameIterator.next();
				         if ( !fileItem.isFormField () )	
				         {
				            // Get the uploaded file parameters
				            String fileName = fileItem.getName();
				            log.debug("fileName : "+fileName);
				            InputStream inputStream = fileItem.getInputStream();
				            int recordInserted = new ExcelFileHandler().readExcelAndInsertRecord(inputStream, productTemplateId, productType, companyId);
				            log.debug("Number of Record Inserted : "+ recordInserted);
				         }
				      }
				   }catch(Exception ex) {
				       System.out.println(ex);
				   }					
				    dynamicValues.put("companyName", companyName);
					htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.UPLOAD_PRDUCT_FROM_EXCEL_POST, dynamicValues, ViewProductTemplateProcessor.class, locale);
					return htmlOutput;
				}		
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
}
