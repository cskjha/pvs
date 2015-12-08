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

import com.pvs.web.constants.RedirectPaths;
import com.pvs.web.i18n.config.InternationalizationConfig;
import com.pvs.web.i18n.localization.Messages;

import ch.qos.cal10n.MessageParameterObj;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Session;

public class ProcessorUtil {
	
	final static Logger log = Logger.getLogger(ProcessorUtil.class);
	
	private static InternationalizationConfig config;
	
	public static String populateTemplate(String templatePath, Map<String, Object> dynamicValues, Class<?> className, String language) throws TemplateException, IOException {		
		String htmlOutput = null;
		initConfig(language);
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
	
	public static void initConfig(String language) {
		config = new InternationalizationConfig(getLocaleProvider(language));
	}

	private static Provider<Locale> getLocaleProvider(final String language) {		
		return new Provider<Locale>() {
			public Locale get() {
				return new Locale(language);
			}
		};
	}
	public static void populateDynamicValues(Map<String, Object> dynamicValues) {		
		for(Messages message : Messages.values()) {
			log.debug("Message String "+message);
			dynamicValues.put(message.toString(), new MessageParameterObj(message));
		}
	}
	public static String getLanguage(Request request) {
		String language = "en";
//		Session session = request.session(false);
//		if(session != null) {
//			language = session.attribute("language");
//			if(language == null || language.length() != 2) {
//				Locale locale = request.raw().getLocale();
//				language = locale.getLanguage();
//			}
//		}
//		else {
//			language = request.queryParams("language");
//			if(language == null || language.length() != 2) {
//				Locale locale = request.raw().getLocale();
//				language = locale.getLanguage();
//			}
//		}
				
		return language;
	}
}
