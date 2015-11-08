package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.pvs.db.connection.utils.DatabaseConstants;
import com.pvs.service.write.ProductValidationSystemWriteService;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class CompanyRegistrationProcessor {
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		Session session = request.session();
		if(session != null) {
			response.redirect(RedirectPaths.DISPLAY_PLAN);
			return null;
		}
		try {
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.COMPANY_REGISTRATION_GET,
					dynamicValues, CompanyRegistrationProcessor.class);	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
	
	
	public static String postHTML(Request request, Response response) {
		String htmlOutput = null;
		String companyName = request.queryParams("companyName");
		String companyEmail = request.queryParams("email");
		String companyPassword = request.queryParams("password");
		Document companyDocument = new Document();
		companyDocument.append("companyName", companyName);
		companyDocument.append(DatabaseConstants.PRIMARY_KEY_COMPANY_COLLECTION, companyEmail);
		companyDocument.append("companyPassword", companyPassword);
		if(ProductValidationSystemWriteService.registerCompany(companyDocument)) {
			try {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.COMPANY_REGISTRATION_POST, dynamicValues, CompanyRegistrationProcessor.class);
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
