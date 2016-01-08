package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.pvs.db.connection.utils.DatabaseConstants;
import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.update.ProductValidationSystemUpdateService;
import com.pvs.service.valueobjects.ProductVO;
import com.pvs.service.valueobjects.ProductValueVO;
import com.pvs.service.valueobjects.ProductWebViewVO;
import com.pvs.service.write.ProductValidationSystemWriteService;
import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;
import com.pvs.web.utilities.ProductRegistrationUtil;

import freemarker.template.TemplateException;
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
		String JSONResponse = "error json:product not found";
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
				
				Document fullproductDetails = new Document();
				Document productDetails = new Document();
				
				
				for(String resultPropery : result.keySet()) {
					if(!("companyId".equals(resultPropery) || "productTemplateId".equals(resultPropery) || "_id".equals(resultPropery) || "ResponseCode".equals(resultPropery) || "creationDate".equals(resultPropery))) {
						productDetails.append(resultPropery, result.get(resultPropery));
					}
					if("ResponseCode".equals(resultPropery)){
						fullproductDetails.append("ResponseCode", result.get(resultPropery));
						if(result.get(resultPropery).equals(ProductValidationSystemWebConstants.INITIAL_PRODUCT_RESPONSE_CODE)){
							fullproductDetails.append("ResponseDetails", "success");
						}
						else if(result.get(resultPropery).equals(ProductValidationSystemWebConstants.STOLEN_PRODUCT_RESPONSE_CODE)){
							fullproductDetails.append("ResponseDetails", "product is stolen");
						}
					}
				}
				productDetails.append("productRating", "4");
				
				String productTemplateId = result.getString("productTemplateId");
				log.debug("productTemplateId :"+productTemplateId +" Product Type" +productType);
				Document productTemplateRecord = ProductValidationSystemReadService.getProductTemplateRecord(productTemplateId, productType);
				log.debug("productTemplateRecord"+productTemplateRecord);
				String manufacturerName = (String)productTemplateRecord.get("manufacturerName");
				String imageURL = (String)productTemplateRecord.get("image");
				productDetails.append("manufacturerName", manufacturerName);
				productDetails.append("image", imageURL);
				fullproductDetails.append("ProductDetails",productDetails);
				JSONResponse = fullproductDetails.toJson();
			}
		}
		return JSONResponse;
	}
	
	public static String getHTML(Request request, Response response) {
		String HTMLResponse = "error html:product not found";
		String productScanCode = request.queryParams("productScanCode");
		if(productScanCode != null) {
			String productId = ProductRegistrationUtil.getProductIdFromProductScanCode(productScanCode);
			String productType = ProductRegistrationUtil.getProductTypeFromProductScanCode(productScanCode);
			Document result = ProductValidationSystemReadService.getProductDetails(productId, productType);
			if(result != null) {
			
				
				String productTemplateId = result.getString("productTemplateId");
				log.debug("productTemplateId :"+productTemplateId +" Product Type" +productType);
				Document productTemplateRecord = ProductValidationSystemReadService.getProductTemplateRecord(productTemplateId, productType);
				log.debug("productTemplateRecord"+productTemplateRecord);
				String manufacturerName = (String)productTemplateRecord.get("manufacturerName");
				String imageURL = (String)productTemplateRecord.get("image");
				
				
				String locale = ProcessorUtil.getLanguage(request);
				//List<ProductWebViewVO> productwebviewVOList = new ArrayList<ProductWebViewVO>();
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				
				ProductWebViewVO productWebViewVO = new ProductWebViewVO();
				productWebViewVO.setManufacturerName(manufacturerName);
				productWebViewVO.setProductRating("4");
				productWebViewVO.setImage(imageURL);
				//productwebviewVOList.add(productwebviewVO);
				ArrayList<ProductValueVO> productValueArrayList = new ArrayList<ProductValueVO>();
				
				
				
				for(String resultPropery : result.keySet()) {
					if(!("companyId".equals(resultPropery) || "productTemplateId".equals(resultPropery) || "_id".equals(resultPropery) || "creationDate".equals(resultPropery))) {
						
						if (resultPropery.equals("manufacturedOn")) {
							productWebViewVO.setManufacturedOn(result.get(resultPropery).toString());
						}
						else if(resultPropery.equals("productName")){
							productWebViewVO.setProductName(result.get(resultPropery).toString());
						}
						else{
							ProductValueVO productValueVO = new ProductValueVO();
						
							productValueVO.setFieldName(resultPropery);
							productValueVO.setFieldValue(result.get(resultPropery).toString());
							productValueArrayList.add(productValueVO);
						}
					}
				}
				
				productWebViewVO.setDynamicValues(productValueArrayList);
				
				dynamicValues.put("productwebview", productWebViewVO);
				
				try {
					HTMLResponse = ProcessorUtil.populateTemplate(TemplatePaths.PRODUCT_WEBVIEW, dynamicValues, ValidateProductProcesssor.class, locale);
					return HTMLResponse;
				} catch (TemplateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return HTMLResponse;
	}
	
	public static String postJSON(Request request, Response response) {
		
		String physical_address = request.queryParams("mac");
		String longitude = request.queryParams("longitude");
		String latitude = request.queryParams("latitude");
		
		Document userDetails = new Document();
		userDetails.append("physical_address", physical_address);
		userDetails.append("longitude", longitude);
		userDetails.append("latitude", latitude);
		
		ProductValidationSystemWriteService.saveUserDetails(userDetails);
		
		return getJSONORHTML(request,response);
	}
	public static String getJSONORHTML(Request request, Response response) {

		String type = (null!=request.queryParams("type")?request.queryParams("type"):"") ;
		
		if (type.equalsIgnoreCase(ProductValidationSystemWebConstants.JSON))
			return getJSON(request,response);
		else
			return getHTML(request, response);
		
	}
	
	
}
