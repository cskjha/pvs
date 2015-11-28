package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.pvs.enums.PlanStates;
import com.pvs.service.write.ProductValidationSystemWriteService;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class PlanRegistrationProcessor {

	public static String getHTML(Request request, Response response) {
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		
		String htmlOutput = null;
		try {
			String companyName = session.attribute("companyName");
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			ProcessorUtil.populateDynamicValues(dynamicValues);
			dynamicValues.put("companyName", companyName);
			htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.PLAN_REGISTRATION_GET, dynamicValues,
					PlanRegistrationProcessor.class	);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
	
	
	public static String postHTML(Request request, Response response) {
		String htmlOutput = null;
		Session session = request.session(false);
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
			return null;
		}
		String planName = request.queryParams("planName");
		String allowedRecordCount = request.queryParams("allowedRecordCount");
		String allowedScanCount = request.queryParams("allowedScanCount");
		String planPrice = request.queryParams("planPrice");
		String planState = request.queryParams("planState");
		String currencyCode = request.queryParams("currencyCode");
		if("on".equals(planState)) {
			planState = PlanStates.ACTIVE.getPlanState();
		}
		else {
			planState = PlanStates.PASSIVE.getPlanState();
		}		
		Document planDocument = new Document();
		planDocument.append("planName", planName);
		planDocument.append("allowedRecordCount", allowedRecordCount);
		planDocument.append("allowedScanCount", allowedScanCount);
		planDocument.append("planPrice", planPrice);
		planDocument.append("planState", planState);
		planDocument.append("currencyCode", currencyCode);
		if(ProductValidationSystemWriteService.registerPlan(planDocument)) {
			try {
				String companyName = session.attribute("companyName");
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				dynamicValues.put("companyName", companyName);
				htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.PLAN_REGISTRATION_POST, dynamicValues, PlanRegistrationProcessor.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch(TemplateException e) {
				e.printStackTrace();
			}
		}		
		return htmlOutput;
	}



}
