package com.pvs.web.i18n.config.models;

import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

public class LocalizedBooleanModel implements TemplateScalarModel,
		TemplateBooleanModel {

	private final boolean value;
	private final String yes;
	private final String no;

	public LocalizedBooleanModel(boolean value, String yes, String no) {
		this.value = value;
		this.yes = yes;
		this.no = no;

	}

	public boolean getAsBoolean() throws TemplateModelException {
		return value;
	}

	public String getAsString() throws TemplateModelException {
		return value ? yes : no;
	}

}
