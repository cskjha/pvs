package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.valueobjects.PlanVO;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class DisplayPlanProcessor {
	
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		try {			
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				Session session = request.session(false);
				if(session != null) {
					String companyName = session.attribute("companyName");
					String companyId = session.attribute("companyId");
					Long remainingRecordCount = null;
					Document companyPlanRecord = ProductValidationSystemReadService.getCompanyPlanRecord(companyId);
					String companyPlanName = null;
					if(companyPlanRecord != null) {
						String companyPlanId = companyPlanRecord.getString("companyPlanId");
						companyPlanName = ProductValidationSystemReadService.getCompanyPlanName(companyPlanId);
						remainingRecordCount = Long.parseLong(companyPlanRecord.getString("remainingRecordCount"));						
					}
					dynamicValues.put("companyName", companyName);
					request.session().attribute("companyPlanName",companyPlanName);
					if(companyPlanName != null && remainingRecordCount > 0) {
						response.redirect(RedirectPaths.MY_PLAN);
						return null;
					}
					else {
						String locale = ProcessorUtil.getLanguage(request);
						List<PlanVO> planList = ProductValidationSystemReadService.getAllPlans();
						if(planList != null && planList.size() != 0) {
							dynamicValues.put("planList", planList);
							htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.DISPLAY_PLAN, dynamicValues, DisplayPlanProcessor.class, locale);
						}
						else {
							htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.DISPLAY_PLAN, dynamicValues, DisplayPlanProcessor.class, locale);
						}
					}
				}
				else {
					response.redirect("companylogin");
					return null;
				}		
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
	
	public static String postHTML(Request request, Response response) {
		String htmlOutput = null;
		try {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				Session session = request.session(false);
				if(session != null) {
					String companyName = session.attribute("companyName");
					String companyPlanName = session.attribute("companyPlanName");
					dynamicValues.put("companyName", companyName);
					if(companyPlanName != null) {
						response.redirect(RedirectPaths.MY_PLAN);
						return null;
					}
				}
				else {
					response.redirect(RedirectPaths.COMPANY_LOGIN);
					return null;
				}
				String locale = ProcessorUtil.getLanguage(request);
				htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.DISPLAY_PLAN, dynamicValues, DisplayPlanProcessor.class, locale);		
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
		
		return htmlOutput;
	}
}
