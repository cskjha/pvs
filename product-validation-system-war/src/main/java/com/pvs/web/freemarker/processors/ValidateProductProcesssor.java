package com.pvs.web.freemarker.processors;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.update.ProductValidationSystemUpdateService;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.utilities.ProductRegistrationUtil;

import spark.Request;
import spark.Response;
import spark.Session;

public class ValidateProductProcesssor {
	
	static final Logger log = Logger.getLogger(ValidateProductProcesssor.class);
	
	public static String getJSON(Request request, Response response) {
//		Session session = request.session(false);
//		if(session == null) {
//			response.redirect(RedirectPaths.COMPANY_LOGIN);
//			return null;
//		}
		String JSONResponse = "error:product not found";
		String productScanCode = request.queryParams("productScanCode");
		if(productScanCode != null) {
			String productId = ProductRegistrationUtil.getProductIdFromProductScanCode(productScanCode);
			String productType = ProductRegistrationUtil.getProductTypeFromProductScanCode(productScanCode);
			Document result = ProductValidationSystemReadService.getProductDetails(productId, productType);
			if(result != null) {
//				String companyId = result.getString("companyId");
//				ProductValidationSystemUpdateService.updateRemainingScanCount(companyId);
//				Document productDetails = new Document();
//				productDetails.append("productName", result.getString("productName"));
//				for(String property : result.keySet()) {
//					if(property.startsWith("field")) {
//						int indexOfField = property.indexOf("Name");
//						if(indexOfField > 0) {
//							String prefix = property.substring(0, indexOfField);					
//							String fieldName = result.getString(prefix+"Name");
//							String fieldValue = result.getString(prefix+"Value");
//							productDetails.append(fieldName, fieldValue);
//							
//						}
//					}
//				}
				//Remove Id, company id and product template id from the response
				Document productDetails = new Document();
				productDetails.append("productRating", "4");
				for(String resultPropery : result.keySet()) {
					if(!("companyId".equals(resultPropery) || "productTemplateId".equals(resultPropery) || "_id".equals(resultPropery) || "creationDate".equals(resultPropery))) {
						productDetails.append(resultPropery, result.get(resultPropery));
					}
				}
				String productTemplateId = result.getString("productTemplateId");
				log.debug("productTemplateId :"+productTemplateId +" Product Type" +productType);
				Document productTemplateRecord = ProductValidationSystemReadService.getProductTemplateRecord(productTemplateId, productType);
				log.debug("productTemplateRecord"+productTemplateRecord);
				String manufacturerName = (String)productTemplateRecord.get("manufacturerName");
				String imageURL = (String)productTemplateRecord.get("image");
				productDetails.append("manufacturerName", manufacturerName);
				productDetails.append("image", imageURL);
				JSONResponse = productDetails.toJson();
			}
		}
		return JSONResponse;
	}
	public static String postJSON(Request request, Response response) {
		return postJSON(request, response);
	}
}
