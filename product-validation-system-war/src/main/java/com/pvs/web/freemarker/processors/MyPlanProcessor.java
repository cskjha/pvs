package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.write.ProductValidationSystemWriteService;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class MyPlanProcessor {
	public static String getHTML(Request request, Response response) {
		return postHTML(request, response);
	}
	
	public static String postHTML(Request request, Response response) {
		String htmlOutput = null;
		try {
			Session session = request.session(false);
			if(session == null) {
				response.redirect(RedirectPaths.COMPANY_LOGIN);
				return null;
			}
			else {
				String companyEmail = request.session().attribute("companyEmail");
				String companyName = request.session().attribute("companyName");
				Document companyPlanRecord = ProductValidationSystemReadService.getCompanyPlanRecord(companyEmail);
				String companyPlanId = null;
				String companyPlanName = null;
				if (companyPlanRecord != null) {
					companyPlanId = companyPlanRecord.getString("companyPlanId");
					companyPlanName = ProductValidationSystemReadService.getCompanyPlanName(companyPlanId);
				}
				if(companyPlanName != null) {
					Map<String, Object> dynamicValues = new HashMap<String, Object>();
					dynamicValues.put("companyPlanName", companyPlanName);
					dynamicValues.put("companyName", companyName);
					htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.MY_PLAN, dynamicValues, MyPlanProcessor.class);
				}
				else {
					companyPlanId = request.queryParams("companyPlanId");
					if(companyPlanId != null) {
						boolean updateStatus = ProductValidationSystemWriteService.registerCompanyPlan(companyEmail, companyPlanId );
						if(updateStatus == true) {
							companyPlanName = ProductValidationSystemReadService.getCompanyPlanName(companyPlanId);
							Map<String, Object> dynamicValues = new HashMap<String, Object>();
							dynamicValues.put("companyPlanName", companyPlanName);
							dynamicValues.put("companyName", companyName);
							htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.MY_PLAN, dynamicValues, MyPlanProcessor.class);
						}
						else {
							Map<String, Object> dynamicValues = new HashMap<String, Object>();
							htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.GENERIC_ERROR, dynamicValues, MyPlanProcessor.class);
						}
					}
				}
			}			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
}