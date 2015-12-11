package com.pvs.web.utilities;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import spark.Request;

public class FormUploadedFileReaderUtil {
	
	static final Logger log = Logger.getLogger(FormUploadedFileReaderUtil.class);
	
//	public FileItem getSingleFile(Request request) {
//		FileItem fileItem = null;		
//		return fileItem;
//	}
	public Map<String, Map<String, Object>> getMultipleFiles(Request request) {
		Map<String, Map<String, Object>> files = new HashMap<String, Map<String, Object>>();
		 boolean isMultipart;
		 int maxFileSize = 50000 * 1024;
		 //int maxMemSize = 4 * 1024;
		// Check that we have a file upload request
	      isMultipart = ServletFileUpload.isMultipartContent(request.raw());
	      if( !isMultipart ){
	         log.debug("No File Uploaded");
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
	      Map<String , Object> filesMap = new HashMap<String, Object>();
	      Map<String , Object> paramsMap = new HashMap<String, Object>();
	      while ( fileNameIterator.hasNext () ) 
	      {
	         FileItem fileItem = (FileItem)fileNameIterator.next();
	         if ( !fileItem.isFormField () )	
	         {
	            // Get the uploaded file parameters
	            String fileName = fileItem.getName();
	            log.debug("fileName : "+fileName);
	            InputStream inputStream = fileItem.getInputStream();
	            filesMap.put(fileName, inputStream);	   
	         }
	         else {
	        	 String paramName = fileItem.getFieldName();
	        	 String paramValue = fileItem.getString();
	        	 paramsMap.put(paramName, paramValue);
	        	 log.debug("paramName :"+paramName+" paramValue:"+paramValue);
	         }
	      }
	      files.put("files", filesMap);
	      files.put("params", paramsMap);
	   }catch(Exception ex) {
	       System.out.println(ex);
	   }			
		return files;
	}
}
