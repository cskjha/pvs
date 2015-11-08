package com.pvs.web.utilities;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.constants.RedirectPaths;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Response;
import spark.Session;

public class ProcessorUtil {
	public static String populateTemplate(String templatePath, Map<String, Object> dynamicValues, Class<?> className) throws TemplateException, IOException {		
		Configuration configuration = new Configuration(ProductValidationSystemWebConstants.FREEMARKER_VERSION);
		configuration.setClassForTemplateLoading(className, "/");
		String htmlOutput = null;
		Template template = configuration.getTemplate(templatePath);
		StringWriter stringWriter = new StringWriter();
		template.process(dynamicValues, stringWriter);
		htmlOutput = stringWriter.toString();
		return htmlOutput;
		
	}
	public static void performSecurityCheck(Session session, Response response) {
		if(session == null) {
			response.redirect(RedirectPaths.COMPANY_LOGIN);
		}
	}
}
