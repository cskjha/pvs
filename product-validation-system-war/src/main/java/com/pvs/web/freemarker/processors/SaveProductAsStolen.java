package com.pvs.web.freemarker.processors;

import com.pvs.service.update.ProductValidationSystemUpdateService;
import com.pvs.web.constants.RedirectPaths;

import spark.Request;
import spark.Response;
import spark.Session;

public class SaveProductAsStolen {
	public static String postHTML(Request request, Response response) {
		String htmlOutput = null;
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String productId = request.queryParams("productId");
		String responseCode= request.queryParams("product_response_code");
		String productType= request.queryParams("productType");
		String productTemplateId = request.queryParams("productTemplateId");
		
		System.out.println("productId : " + productId);
		System.out.println("responseCode : " + responseCode);
		System.out.println("productTye : " + productType);
		
		ProductValidationSystemUpdateService.updateProductResponseCode(productId,responseCode,productType);			
		response.redirect(RedirectPaths.LIST_PRODUCTS+"?productTemplateId="+productTemplateId+"&productType="+productType);
		return htmlOutput;
	}
}
