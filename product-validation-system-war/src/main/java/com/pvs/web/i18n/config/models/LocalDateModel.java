package com.pvs.web.i18n.config.models;

import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

public class LocalDateModel extends BeanModel implements TemplateScalarModel {

	private final LocalDate object;
	private final Locale locale;

	public LocalDateModel(LocalDate object, Locale locale, BeansWrapper wrapper) {
		super(object, wrapper);
		this.object = object;
		this.locale = locale;
	}

	public String getAsString() throws TemplateModelException {
		DateTimeFormatter formatter = DateTimeFormat.forStyle("M-").withLocale(
				locale);
		return formatter.print(object);
	}

}
