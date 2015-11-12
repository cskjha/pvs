package com.pvs.web.freemarker.processors;

import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.update.ProductValidationSystemUpdateService;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.utilities.ProductRegistrationUtil;

import spark.Request;
import spark.Response;
import spark.Session;

public class ValidateProductProcesssor {
	public static String getJSON(Request request, Response response) {
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String JSONResponse = "error:product not found";
		String productScanCode = request.queryParams("productScanCode");
		if(productScanCode != null) {
			String productId = ProductRegistrationUtil.getProductIdFromProductScanCode(productScanCode);
			String productType = ProductRegistrationUtil.getProductTypeFromProductScanCode(productScanCode);
			Document result = ProductValidationSystemReadService.getProductDetails(productId, productType);
			if(result != null) {
				String companyId = result.getString("companyId");
				ProductValidationSystemUpdateService.updateRemainingScanCount(companyId);
				Document productDetails = new Document();
				for(String property : result.keySet()) {
					if("productName".equals(property) || property.startsWith("field")) {
						productDetails.append(property, result.getString(property));
					}
				}
				JSONResponse = productDetails.toJson();
			}
		}
		return JSONResponse;
	}
	public static String postJSON(Request request, Response response) {
		return postJSON(request, response);
	}
}
