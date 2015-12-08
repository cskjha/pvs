package com.pvs.web.freemarker.processors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;

import com.pvs.service.excel.read.ExcelFileWriteService;
import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.utilities.ProcessorUtil;

import spark.Request;
import spark.Response;
import spark.Session;

public class DownloadProductFormatProcessor {
	
	public static String getHTML(Request request, Response response) {
		final Logger log = Logger.getLogger(DownloadProductFormatProcessor.class);
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
					String templateName = request.queryParams("templateName");
					//String companyName = session.attribute("companyName");
					
					Document productTemplateDocument = ProductValidationSystemReadService.getProductTemplateRecord(productTemplateId, productType);
					log.debug("productTemplateDocument : "+productTemplateDocument);
					if(productTemplateDocument != null) {
						Workbook workbook = new ExcelFileWriteService().writeSingleDocument(productTemplateDocument, productType+productTemplateId+".xlsx", 0);
						if(workbook != null) {
							response.header("Content-Disposition", ("attachment;filename="+templateName+"_"+productType+productTemplateId+"."+ProductValidationSystemWebConstants.EXCEL_EXTENSION));
							workbook.write(response.raw().getOutputStream());
						}
						return null;
					}
					else {
						response.redirect(RedirectPaths.GENERIC_ERROR_PAGE);
						return null;
					}
				}		
			
		} catch (IOException e) {
			e.printStackTrace();
		} 		
		return htmlOutput;
	}
}
