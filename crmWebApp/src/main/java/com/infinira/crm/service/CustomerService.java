package com.infinira.crm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infinira.crm.dao.CustomerDao;
import com.infinira.crm.model.Customer;

@Service
public class CustomerService implements ICustomerService {
	private static volatile CustomerService customerService;

	// Singleton Pattern
	private CustomerService() {
	}

	public static CustomerService getInstance() {
		if (customerService == null) {
			synchronized (CustomerService.class) {
				if (customerService == null) {
					customerService = new CustomerService();
				}
			}
		}
		return customerService;
	}

	@Override
	public void createCustomer(Customer customer) {

		CustomerDao.createCustomer(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		CustomerDao.updateCustomer(customer);
	}

	@Override
	public void deleteCustomer(int customerId) {
		CustomerDao.deleteCustomer(customerId);
	}

	@Override
	public Customer getCustomer(int customerId) {
		return CustomerDao.getCustomer(customerId);
	}

	@Override
	public List<Customer> getCustomers() {
		return CustomerDao.getAllCustomers();
	}
}
