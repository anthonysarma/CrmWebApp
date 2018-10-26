package com.infinira.crm.model;

public class Login {
	private long userId;
	private String password;

	public Login(long userId, String password) {
		if (userId < 1) {
			throw new RuntimeException("Invalid UserName is Given!");
		}
		this.userId = userId;
		if (password == null || password.trim().isEmpty()) {
			throw new RuntimeException("Invalid Password is Given!");
		}
		this.password = password;
	}

	public long getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}
}
