package com.infinira.crm.service;

import java.util.List;

import com.infinira.crm.model.Customer;

public interface ICustomerService {
	void createCustomer(Customer customer);

	void updateCustomer(Customer customer);

	void deleteCustomer(int customerId);

	Customer getCustomer(int customerId);

	List<Customer> getCustomers();
}
