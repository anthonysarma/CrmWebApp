package com.infinira.crm.util;

@SuppressWarnings("serial")
public class CustomerException extends RuntimeException {
	public CustomerException(String message, Throwable th) {
		super(message, th);
	}
}
