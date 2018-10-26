package com.infinira.crm.util;

import java.util.List;

public class StringUtils {
	private static final String SPACE = "\\S";
	private static final String EMPTY = "";

	public static boolean isNullOrEmpty(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isBlank(String value) {
		if (value == null || value.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static String removeAllSpaces(String value) {
		if (value != null) {
			return value.replaceAll(SPACE, EMPTY);
		}
		return value;
	}

	public static boolean isBlank(List<String> listOfValues) {
		if(listOfValues == null || listOfValues.isEmpty()) return true;
		for(String val : listOfValues ) {
			return isBlank(val);
		}
		return false;
	}
}
