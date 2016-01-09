package com.pvs.web.freemarker.processors;

import org.apache.log4j.Logger;

import com.pvs.service.delete.ProductValidationSystemDeleteService;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.utilities.ProcessorUtil;

import spark.Request;
import spark.Response;
import spark.Session;

public class RemoveProductTemplateProcessor {

	final static Logger log = Logger.getLogger(RemoveProductTemplateProcessor.class);
	public static String getHTML(Request request, Response response) {
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
		}
		String productTemplateId = request.queryParams("productTemplateId");
		String productType = request.queryParams("productType");
		log.debug("productTemplateId : "+productTemplateId);
		ProductValidationSystemDeleteService.removeProductTemplate(productTemplateId,productType);
		response.redirect(RedirectPaths.VIEW_PRODUCT_TEMPLATES);
		
		return null;
	}
	
}
