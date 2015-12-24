package com.pvs.web.freemarker.processors;

import org.apache.log4j.Logger;

import com.pvs.service.delete.ProductValidationSystemDeleteService;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.utilities.ProcessorUtil;

import spark.Request;
import spark.Response;
import spark.Session;

public class RemoveProductTemplateProcessor {

	final static Logger log = Logger.getLogger(ProductRegistrationProcessor.class);
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String productName = request.queryParams("productTemplateName");
		String productType = request.queryParams("productType");
		String companyName = request.queryParams("companyName");
		log.debug("productTemplateName : "+productName);
		//String companyId = request.session().attribute("companyId");
		//String locale = ProcessorUtil.getLanguage(request);
		boolean flag=ProductValidationSystemDeleteService.removeProductTemplate(productType,productName,companyName);
		if(flag)
			response.redirect(RedirectPaths.VIEW_PRODUCT_TEMPLATES);
		else
			response.redirect(RedirectPaths.REGISTER_PRODUCT_TEMPLATE);
		return htmlOutput;
	}
	
}