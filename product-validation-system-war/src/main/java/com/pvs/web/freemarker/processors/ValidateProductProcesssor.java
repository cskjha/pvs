package com.pvs.web.freemarker.processors;

import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;

import spark.Request;

public class ValidateProductProcesssor {
	public static String getJSON(Request request) {
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
