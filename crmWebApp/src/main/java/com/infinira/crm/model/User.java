package com.infinira.crm.model;

public class User {
	private long userId;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private String address;
	private String phone;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User(String password) {
		if (password == null || password.trim().isEmpty()) {
			throw new RuntimeException("Invalid UserName is Given!");
		}
		this.password = password;
	}

	public User(long userId, String password) {
		this(password);
		if (userId  < 1) {
			throw new RuntimeException("Invalid UserName is Given!");
		}
		this.userId = userId;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		if (userId  < 1) {
			throw new RuntimeException("Invalid UserName is Given!");
		}
		this.userId = userId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		if (password == null || password.trim().isEmpty()) {
			throw new RuntimeException("Invalid Password is Given !");
		}
		this.password = password;
	}
}
