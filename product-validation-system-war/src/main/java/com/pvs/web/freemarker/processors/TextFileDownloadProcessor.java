package com.pvs.web.freemarker.processors;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class TextFileDownloadProcessor {
	final static Logger log = Logger.getLogger(ProductListViewProcessor.class);

	private static final int BYTES_DOWNLOAD = 1024;
	
	public static String getHTML(Request request, Response response) {

		
		String htmlOutput = null;
		try {

			Session session = request.session(false);
			if (session == null) {
				response.redirect(RedirectPaths.COMPANY_LOGIN);
				return null;
			}
			String productTemplateId = request.queryParams("productTemplateId");
			String productType = request.queryParams("productType");
			
			List<Document> productListViewDocument = ProductValidationSystemReadService
					.getProductListViewRecord(productTemplateId, productType);
			Iterator<Document> productListIterator = productListViewDocument.iterator();
			
			String allproductCode="";
			while (productListIterator.hasNext()) {
				Document product = productListIterator.next();
				String productId = product.getObjectId("_id").toHexString();
				allproductCode+= productType+productId+"\r\n";
				
				
			}

			response.raw().setContentType("text/plain");
	        response.raw().setHeader("Content-Disposition", "attachment;filename=" + productTemplateId + ".txt");

	        InputStream input = new ByteArrayInputStream(allproductCode.getBytes("UTF8"));

	        int read = 0;
	        byte[] bytes = new byte[BYTES_DOWNLOAD];
	        OutputStream os = response.raw().getOutputStream();

	        while ((read = input.read(bytes)) != -1) {
	            os.write(bytes, 0, read);
	        }
	        os.flush();
	        os.close();

			
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		return htmlOutput;
	}
	
		
}
