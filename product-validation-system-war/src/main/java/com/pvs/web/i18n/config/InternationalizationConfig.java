package com.pvs.web.i18n.config;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Provider;

import com.pvs.web.constants.ProductValidationSystemWebConstants;
import com.pvs.web.i18n.config.models.LocalizingWrapper;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class InternationalizationConfig {
	private static Configuration config;
	private Provider<Locale> localeProvider;

	public InternationalizationConfig(Provider<Locale> localeProvider) {
		config = new Configuration(ProductValidationSystemWebConstants.FREEMARKER_VERSION);
		config.setClassForTemplateLoading(getClass(), "/");
		config.setObjectWrapper(new LocalizingWrapper(localeProvider));
		this.localeProvider = localeProvider;
	}

	public Template getTemplate(String name) throws IOException {
		return config.getTemplate(name, localeProvider.get());
	}
}
