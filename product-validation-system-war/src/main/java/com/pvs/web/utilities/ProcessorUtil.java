package com.pvs.web.utilities;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Provider;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.YearMonth;

import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.i18n.config.InternationalizationConfig;
import com.pvs.web.i18n.localization.Messages;

import ch.qos.cal10n.MessageParameterObj;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Response;
import spark.Session;

public class ProcessorUtil {
	
	final static Logger log = Logger.getLogger(ProcessorUtil.class);
	
	private static InternationalizationConfig config;
	
	public static String populateTemplate(String templatePath, Map<String, Object> dynamicValues, Class<?> className) throws TemplateException, IOException {		
		String htmlOutput = null;
		initConfig();
		log.debug("Config Test : "+config);
		log.debug("templatePath"+templatePath);
		Template template = config.getTemplate(templatePath);
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
	
	public static void initConfig() {
		config = new InternationalizationConfig(getLocaleProvider());
	}

	private static Provider<Locale> getLocaleProvider() {
		return new Provider<Locale>() {
			public Locale get() {
				return Locale.GERMAN;
			}
		};
	}

	public static Map<String, Object> getDataModel() {
		HashMap<String, Object> datamodel = new HashMap<String, Object>();
		datamodel.put("date", new LocalDate());
		datamodel.put("time", new LocalTime());
		datamodel.put("month", new YearMonth());
		datamodel.put("boolean", true);
		datamodel.put("greeting", new MessageParameterObj(Messages.HELLO,
				"Freemarker"));
		return datamodel;
	}
	
	public static void populateDynamicValues(Map<String, Object> dynamicValues) {		
		for(Messages message : Messages.values()) {
			log.debug("Message String "+message);
			dynamicValues.put(message.toString(), new MessageParameterObj(message));
		}
	}	
}
