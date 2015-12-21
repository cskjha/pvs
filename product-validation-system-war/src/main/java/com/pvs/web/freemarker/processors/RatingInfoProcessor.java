package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.pvs.db.connection.utils.DatabaseConstants;
import com.pvs.service.utils.CommonUtils;
import com.pvs.service.write.ProductValidationSystemWriteService;
import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;

public class RatingInfoProcessor {
	public static String postHTML(Request request, Response response) {
		String htmloutput=null;
		String ratingId = request.queryParams("ratingId");
		String productId = request.queryParams("productId");
		String ip = request.queryParams("ip");
		String username = request.queryParams("username");
		String dateNtime = request.queryParams("dateNtime");
		
		Document ratingDocument = new Document();
		ratingDocument.append("ratingId", ratingId);
		ratingDocument.append("productId", productId);
		ratingDocument.append("ip", ip);
		ratingDocument.append("username", username);
		ratingDocument.append("dateNtime", dateNtime);
		new CommonUtils().addHistoryFields(ratingDocument);
		
		if(ProductValidationSystemWriteService.ratingInfo(ratingDocument)) {
			try {
				Map<String, Object> dynamicValues = new HashMap<String, Object>();
				ProcessorUtil.populateDynamicValues(dynamicValues);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return htmloutput;
	}
}
