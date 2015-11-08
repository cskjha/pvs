package com.pvs.web.utilities;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.pvs.web.constants.ProductValidationSystemWebConstants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

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

}
