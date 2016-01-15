package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.valueobjects.AppUserDetailsVO;
import com.pvs.service.valueobjects.ProductTemplateVO;
import com.pvs.service.valueobjects.RegisteredProductVO;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class DashboardProcessor {
	final static Logger log = Logger.getLogger(EditProductTemplateProcessor.class);
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		Map<String, Object> dynamicValues = new HashMap<String, Object>();
		ProcessorUtil.populateDynamicValues(dynamicValues);
		String locale = ProcessorUtil.getLanguage(request);
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String companyName = request.session().attribute("companyName");
		String companyId = request.session().attribute("companyId");
		String category = request.session().attribute("category");
		Document planRecord = ProductValidationSystemReadService.getCompanyPlanRecord(companyId);
		String remainingRecordCount = planRecord.getString("remainingRecordCount");
		String remainingScanCount = planRecord.getString("remainingScanCount");
		
		List<RegisteredProductVO> registeredProductVOList = new ArrayList<RegisteredProductVO>();
		List<Document> productTemplates = ProductValidationSystemReadService.getCompanyTemplateRecords(companyId);
		Iterator<Document> productTemplateIterator = productTemplates.iterator();
		while(productTemplateIterator.hasNext()) {
			List<AppUserDetailsVO> appUserDetailsVOList = new ArrayList<AppUserDetailsVO>();
			Document productTemplate = productTemplateIterator.next();
			String productTemplateId = productTemplate.getObjectId("_id").toHexString();
			String productTemplateName = productTemplate.getString("productName");
			String productImage = productTemplate.getString("image");
			String productType = productTemplate.getString("productType");
			System.out.println("productname:"+productTemplateName);
			System.out.println("templateid:"+productTemplateId);
			System.out.println("producttype:"+productType);
			List<Document> appUserDetails = ProductValidationSystemReadService.getAppUserDetails(productTemplateId);
			Iterator<Document> userDetailsIterator = appUserDetails.iterator();
			while(userDetailsIterator.hasNext()) {
				Document userdetails = userDetailsIterator.next();
				String longitude = userdetails.getString("longitude");
				String latitude = userdetails.getString("latitude");
				System.out.println("longitude:"+longitude+"   latitude:"+latitude);
				AppUserDetailsVO appuserdetailsVO = new AppUserDetailsVO();
				appuserdetailsVO.setLongitude(longitude);
				appuserdetailsVO.setLatitude(latitude);
				appUserDetailsVOList.add(appuserdetailsVO);
			}
			
			
				int numberofRegisteredProduct = ProductValidationSystemReadService.getNumberofRegisteredProduct(productTemplateId,productType);
				System.out.println("number of registered product:"+numberofRegisteredProduct);
				RegisteredProductVO registeredProductVO = new RegisteredProductVO();
				registeredProductVO.setNumberofRegisteredProduct(numberofRegisteredProduct);
				registeredProductVO.setProductTemplateId(productTemplateId);
				registeredProductVO.setIconImg(productImage);
				registeredProductVO.setProductTemplateName(productTemplateName);
				registeredProductVO.setAppUserDetailsList(appUserDetailsVOList);
				registeredProductVOList.add(registeredProductVO);
			
		}
		dynamicValues.put("companyName", companyName);
		dynamicValues.put("category", category);
		//dynamicValues.put("appUserDetailsVOList", appUserDetailsVOList);
		dynamicValues.put("registeredProductList", registeredProductVOList);
		dynamicValues.put("remainingRecordCount", remainingRecordCount);
		dynamicValues.put("remainingScanCount", remainingScanCount);
		
		
		try {
			htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.DASHBOARD, dynamicValues, DashboardProcessor.class, locale);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return htmlOutput;
	}

}
