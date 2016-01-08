package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.bson.Document;

import com.pvs.db.connection.utils.DatabaseConstants;
import com.pvs.service.utils.CommonUtils;
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
		Session session = request.session(false);
		if(session != null) {
			response.redirect(RedirectPaths.DISPLAY_PLAN);
			return null;
		}
		try {
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			ProcessorUtil.populateDynamicValues(dynamicValues);
			String locale = ProcessorUtil.getLanguage(request);
			htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.COMPANY_REGISTRATION_GET,
					dynamicValues, CompanyRegistrationProcessor.class, locale);	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
	
	
	public static String postHTML(Request request, Response response) {
		String htmlOutput = null;
		String companyPassword = null;
		String companyName = request.queryParams("companyName");
		String companyEmail = request.queryParams("email");
		String originalcompanyPassword = request.queryParams("password");
		
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(originalcompanyPassword.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			companyPassword = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(companyPassword.length() < 32 ){
				companyPassword = "0"+companyPassword;
			}
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		String category = "company";
		String status="enabled";
		Document companyDocument = new Document();
		companyDocument.append("companyName", companyName);
		companyDocument.append(DatabaseConstants.PRIMARY_KEY_COMPANY_COLLECTION, companyEmail);
		companyDocument.append("companyPassword", companyPassword);
		companyDocument.append("category", category);
		companyDocument.append("status", status);
		new CommonUtils().addHistoryFields(companyDocument);
		
		
		if(ProductValidationSystemWriteService.registerCompany(companyDocument)) {
			try {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				String locale = ProcessorUtil.getLanguage(request);
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
				Document auditDocument = new Document();
				auditDocument.append("companyName", companyName);
				auditDocument.append("username", companyEmail);
				auditDocument.append("status", "one new user has been successfully registered");
				auditDocument.append("time", timeStamp);
				ProductValidationSystemWriteService.updateCompanyAuditTable(auditDocument);
				htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.COMPANY_REGISTRATION_POST, dynamicValues, CompanyRegistrationProcessor.class, locale);
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
