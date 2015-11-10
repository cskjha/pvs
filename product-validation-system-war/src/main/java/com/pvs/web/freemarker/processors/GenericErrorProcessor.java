package com.pvs.web.freemarker.processors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.pvs.web.constants.TemplatePaths;
import com.pvs.web.utilities.ProcessorUtil;

import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;

public class GenericErrorProcessor {
	public static String getHTML(Request request, Response response) {
		String htmlOutput = null;
			Map<String, Object> dynamicValues = new HashMap<String, Object>();
			try {
				htmlOutput = ProcessorUtil.populateTemplate(TemplatePaths.GENERIC_ERROR, dynamicValues, GenericErrorProcessor.class);
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		return htmlOutput;
	}
}