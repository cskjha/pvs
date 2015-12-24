package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.valueobjects.CompanyVO;
import com.pvs.service.valueobjects.ProductVO;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class DisplayUserListProcessor {

	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		try {
				Session session = request.session(false);
				if(session != null) {
			
				String locale = ProcessorUtil.getLanguage(request);
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				List<CompanyVO> userVOList = new ArrayList<CompanyVO>();
				List<Document> userListViewDocument = ProductValidationSystemReadService.getAllCompanyUserStatus();
				Iterator<Document> productListIterator = userListViewDocument.iterator();
				while(productListIterator.hasNext()) {
					Document user = productListIterator.next();
					CompanyVO companyVO = new CompanyVO();
					companyVO.setCompanyEmail(user.getString("companyEmail"));
					companyVO.setStatus(user.getString("status"));
					
					userVOList.add(companyVO);
				}					
		
				dynamicValues.put("userList", userVOList);
				htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.DISPLAY_USERLIST, dynamicValues, DisplayUserListProcessor.class, locale);
				return htmlOutput;
				}
				else {
					response.redirect("companylogin");
					return null;
				}
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
}
