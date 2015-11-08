package com.pvs.web.freemarker.processors;

import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.web.constants.RedirectPaths;

import spark.Request;
import spark.Response;
import spark.Session;

public class ValidateProductProcesssor {
	public static String getJSON(Request request, Response response) {
		Session session = request.session();
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String JSONResponse = "error:product not found";
		String QRCode = request.queryParams("QRCode");
		if(QRCode != null) {
			Document result = ProductValidationSystemReadService.getProductDetails(QRCode,"instanceId","ProductType");
			if(result != null) {
				JSONResponse = result.toJson();
			}
		}
		return JSONResponse;
	}
	public static String postJSON(Request request, Response response) {
		Session session = request.session();
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String JSONResponse = "error:product not found";
		String QRCode = request.queryParams("QRCode");
		if(QRCode != null) {
			Document result = ProductValidationSystemReadService.getProductDetails(QRCode,"instanceId","ProductType");
			if(result != null) {
				JSONResponse = result.toJson();
			}
		}
		return JSONResponse;
	}
}
