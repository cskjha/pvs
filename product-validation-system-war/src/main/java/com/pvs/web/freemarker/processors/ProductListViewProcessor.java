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
import com.pvs.service.valueobjects.ProductVO;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class ProductListViewProcessor {
	final static Logger log = Logger.getLogger(ProductListViewProcessor.class);
	public static String getHTML(Request request, Response response) {
		
		String htmlOutput = null;
		try {	
			
			Session session = request.session(false);
			if(session == null) {
				response.redirect(RedirectPaths.COMPANY_LOGIN);
				return null;
			}
			String productTemplateId = request.queryParams("productTemplateId");
			String productType= request.queryParams("productType");
			String userName = request.session().attribute("companyName");
			String companyId = request.session().attribute("companyId"); 
			String locale = ProcessorUtil.getLanguage(request);
			
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			ProcessorUtil.populateDynamicValues(dynamicValues);
			dynamicValues.put("companyName", userName);
			
			List<ProductVO> productVOList = new ArrayList<ProductVO>();
			List<Document> productListViewDocument = ProductValidationSystemReadService.getProductListViewRecord(productTemplateId,productType);
			Iterator<Document> productListIterator = productListViewDocument.iterator();
			while(productListIterator.hasNext()) {
				Document product = productListIterator.next();
				String productId = product.getObjectId("_id").toHexString();
				ProductVO productVO = new ProductVO();
				productVO.setProductId(productType + productId);
				
				//TODO: we need to do a for loop
				/*String productFieldName1 = product.getString("field1Name");
				String productFieldValue1 = product.getString("field1Value");
				productVO.getFieldName().add(productFieldName1);
				productVO.getFieldValue().add(productFieldValue1);*/
				
				productVOList.add(productVO);
			}					
	
			dynamicValues.put("productList", productVOList);
			htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.LIST_PRODUCTS, dynamicValues, ProductListViewProcessor.class, locale);
			return htmlOutput;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}		
		return htmlOutput;
	}
}
