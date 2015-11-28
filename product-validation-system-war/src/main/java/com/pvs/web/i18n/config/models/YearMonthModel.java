package com.pvs.web.i18n.config.models;

import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

public class YearMonthModel extends BeanModel implements TemplateScalarModel {

	private final YearMonth object;

	public YearMonthModel(YearMonth object, BeansWrapper wrapper) {
		super(object, wrapper);
		this.object = object;
	}

	public String getAsString() throws TemplateModelException {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-yyyy");
		return formatter.print(object);
	}

}
