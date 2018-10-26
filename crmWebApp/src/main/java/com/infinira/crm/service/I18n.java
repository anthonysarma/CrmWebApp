package com.infinira.crm.service;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import com.infinira.crm.util.StringUtils;

import static com.infinira.crm.util.CrmConstant.*;

public class I18n {
	private static volatile I18n i18n;

	private ResourceBundle resourceBundle;

	// Singleton Pattern
	private I18n() {
		init();
	}

	public static I18n getInstance() {
		if (i18n == null) {
			synchronized (I18n.class) {
				if (i18n == null) {
					i18n = new I18n();
				}
			}
		}
		return i18n;
	}

	private void init() {
		try {
			resourceBundle = ResourceBundle.getBundle(RESOURCE_PROPERTY_FILE);
		} catch (Throwable th) {
		}
	}

	public String getMessage(String code, Object... parameters) {
		String message = null;

		if (StringUtils.isNullOrEmpty(code)) {
			return "Invalid message format specified";
		}
		try {
			message = MessageFormat.format(resourceBundle.getString(code), parameters);
		} catch (Throwable th) {
		}
		return message;
	}
}
