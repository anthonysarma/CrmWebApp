package com.infinira.crm.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infinira.crm.service.I18n;

public class LoggerUtil {
	private static final Logger LOGGER = LogManager.getLogger();

	public static void loggAndThrowException(String key, Throwable th, Object... parameters) {
		error(key, th, parameters);
		throw new CustomerException(I18n.getInstance().getMessage(key, parameters), th);
	}

	public static void error(String key, Throwable th, Object... parameters) {
		LOGGER.error(I18n.getInstance().getMessage(key, parameters), th);
	}
	
	public static Logger getLogger() {
		return LOGGER;
	}
}
