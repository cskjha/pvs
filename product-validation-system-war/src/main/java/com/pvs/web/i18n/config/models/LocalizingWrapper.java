package com.pvs.web.i18n.config.models;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Provider;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.ReadableInstant;
import org.joda.time.YearMonth;

import com.pvs.web.i18n.localization.Messages;

import ch.qos.cal10n.BaseName;
import ch.qos.cal10n.MessageConveyor;
import ch.qos.cal10n.MessageParameterObj;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class LocalizingWrapper extends DefaultObjectWrapper {

	private Provider<Locale> localeProvider;
	
	static final Logger log = Logger.getLogger(LocalizingWrapper.class);

	public LocalizingWrapper( Provider<Locale> localeProvider) {
		this.localeProvider = localeProvider;
	}

	@Override
	public TemplateModel wrap(Object object) throws TemplateModelException {
		if (object instanceof ReadableInstant) {
			return new PvsInstantModel((ReadableInstant) object,
					localeProvider.get(), this);
		}
		if (object instanceof LocalDateTime) {
			return new LocalDateTimeModel((LocalDateTime) object,
					localeProvider.get(), this);
		}
		if (object instanceof LocalDate) {
			return new LocalDateModel((LocalDate) object, localeProvider.get(),
					this);
		}
		if (object instanceof LocalTime) {
			return new LocalTimeModel((LocalTime) object, localeProvider.get(),
					this);
		}
		if (object instanceof YearMonth) {
			return new YearMonthModel((YearMonth) object, this);
		}
		if (object instanceof Enum
				&& object.getClass().isAnnotationPresent(BaseName.class)) {
			return super.wrap(lookUp((Enum<?>) object));
		}
		if (object instanceof MessageParameterObj) {
			return super.wrap(lookUp((MessageParameterObj) object));
		}
		if (object instanceof Boolean) {
			return new LocalizedBooleanModel((Boolean) object,
					lookUp(Messages.YES), lookUp(Messages.NO));
		}
		return super.wrap(object);
	}

	private Object lookUp(MessageParameterObj object) {
		return getDictionary().getMessage(object);
	}

	public String lookUp(Enum<?> object) {
		return getDictionary().getMessage(object);
	}

	// in a realistic setting we would cache the message conveyors per locale
	public MessageConveyor getDictionary() {
		return new MessageConveyor(localeProvider.get());
	}
}
