package com.infinira.crm.model;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
public class Customer {

	private int customerId;
	private String firstName;
	private String middleName;
	private String lastName;
	private Gender gender;
	private String emailId;

	private Map<String, String> contacts;
	private Map<String, String> identities;
	private List<String> comment;

	// Constructors
	public Customer() {}
	
	public Customer(String firstName, Gender gender) {
		if (isNullOrEmpty(firstName)) {
			throw new RuntimeException(CUSTOMER_0001);
		}

		if (firstName.length() > 25) {
			throw new RuntimeException(MessageFormat.format(CUSTOMER_0002, firstName.length()));
		}

		this.firstName = firstName.trim();
		this.gender = gender;
	}

	public Customer(int customerId, String firstName, Gender gender) {
		this(firstName, gender);
		validateCustomerId(customerId);
		this.customerId = customerId;
	}

	public Customer(int customerId) {
		validateCustomerId(customerId);
		this.customerId = customerId;
	}

	// setters
	public void setCustomerId(int customerId) {
		validateCustomerId(customerId);
		this.customerId = customerId;
	}

	public void setMiddleName(String middleName) {
		if ((middleName != null) && (middleName.trim().length() == 0)) {
			throw new RuntimeException(CUSTOMER_0003);
		}

		if ((middleName != null) && (middleName.length() > 40)) {
			throw new RuntimeException(MessageFormat.format(CUSTOMER_0004, middleName.length()));
		}

		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		if ((lastName != null) && (lastName.trim().length() == 0)) {
			throw new RuntimeException(CUSTOMER_0005);
		}

		if ((lastName != null) && (lastName.length() > 25)) {
			throw new RuntimeException(MessageFormat.format(CUSTOMER_0006, lastName.length()));
		}

		this.lastName = lastName;
	}

	public void setEmailId(String emailId) {
		if (isNullOrEmpty(emailId)) {
			throw new RuntimeException(CUSTOMER_0007);
		}

		if ((emailId != null) && (emailId.length() > 254)) {
			throw new RuntimeException(MessageFormat.format(CUSTOMER_0008, emailId.length()));
		}

		this.emailId = emailId;
	}

	public void setContacts(Map<String, String> contacts) {
		this.contacts = contacts;
	}

	public void setIdentities(Map<String, String> identities) {
		this.identities = identities;
	}

	public void setComment(List<String> comment) {
		this.comment = comment;
	}

	// Getters
	public int getCustomerId() {
		return customerId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public Gender getGender() {
		return this.gender;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public Map<String, String> getContacts() {
		return contacts;
	}

	public Map<String, String> getIdentities() {
		return identities;
	}

	public List<String> getComment() {
		return comment;
	}

	public enum Gender {
		M, F, O
	}

	// validation
	private void validateCustomerId(int customerId) {
		if (customerId < 1) {
			throw new RuntimeException(CUSTOMER_0009 + customerId);
		}

	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	// Constants
	private static final String CUSTOMER_0001 = "First name should not be null or Blank";
	private static final String CUSTOMER_0002 = "Length of the First Name {0} is  more than 25 characters";
	private static final String CUSTOMER_0003 = "Middle name should not contain wrong value";
	private static final String CUSTOMER_0004 = "Length of the Middle name {0} should not contain more than 40 characters";
	private static final String CUSTOMER_0005 = "Last Name should not contain wrong value";
	private static final String CUSTOMER_0006 = "Length of the Last name {0} should not contain more than 25 characters.";
	private static final String CUSTOMER_0007 = "EmailId should not be null or empty";
	private static final String CUSTOMER_0008 = "Length of the EmailId {0} should not contain more than 254 characters";
	private static final String CUSTOMER_0009 = "Invalid Customer Id is Given";
}
