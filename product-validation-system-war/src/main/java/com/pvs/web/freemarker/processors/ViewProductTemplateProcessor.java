package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.bson.Document;

import com.pvs.service.read.ProductValidationSystemReadService;
import com.pvs.service.valueobjects.ProductTemplateVO;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class ViewProductTemplateProcessor {
	
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
		try {			
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				Session session = request.session(false);
				String locale = ProcessorUtil.getLanguage(request);
				if(session == null) {
					response.redirect(RedirectPaths.COMPANY_LOGIN);
					return null;
				}
				else {
					String companyId = session.attribute("companyId");
					String companyName = session.attribute("companyName");
					List<ProductTemplateVO> productTemplateVOList = new ArrayList<ProductTemplateVO>();
					List<Document> productTemplates = ProductValidationSystemReadService.getCompanyTemplateRecords(companyId);
					Iterator<Document> productTemplateIterator = productTemplates.iterator();
					while(productTemplateIterator.hasNext()) {
						Document productTemplate = productTemplateIterator.next();
						String productTemplateId = productTemplate.getObjectId("_id").toHexString();
						String productTemplateName = productTemplate.getString("productName");
						String productType = productTemplate.getString("productType");
						ProductTemplateVO productTemplateVO = new ProductTemplateVO();
						productTemplateVO.setProductTemplateId(productTemplateId);
						productTemplateVO.setProductTemplateName(productTemplateName);
						productTemplateVO.setProductType(productType);
						productTemplateVOList.add(productTemplateVO);
					}					
					dynamicValues.put("companyName", companyName);
					dynamicValues.put("productTemplateList", productTemplateVOList);
					htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.VIEW_PRODUCT_TEMPLATE, dynamicValues, ViewProductTemplateProcessor.class, locale);
					return htmlOutput;
				}		
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
}
