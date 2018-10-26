package com.infinira.crm.controller;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.infinira.crm.enums.Contacts;
import com.infinira.crm.model.Customer;
import com.infinira.crm.service.CustomerService;
import com.infinira.crm.util.CustomerUtil;
import com.infinira.crm.util.LoggerUtil;

import static com.infinira.crm.util.CrmConstant.*;

@RestController
@RequestMapping("/register")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	@ResponseBody
	public void createStudent(@RequestParam(value = "firstName", required = true) String firstName,
			@RequestParam(value = "middleName", required = false) String middleName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "gender", required = true) String gender,
			@RequestParam(value = "emailId", required = false) String emailId,
			@RequestParam(value = "comments", required = false) String comments,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "mobile", required = false) String mobile, Model s) {

		String currentMethod = new Object() {}.getClass().getEnclosingMethod().getName();
		LoggerUtil.getLogger().info("The Current Method executing" + currentMethod);
		
		HashMap<String, String> contacts = new HashMap<String, String>();
		contacts.put(Contacts.MOBILE.toString(), mobile);
		contacts.put(Contacts.PHONE.toString(), phone);

		Customer customer = new Customer(firstName, CustomerUtil.getGender(gender));
		customer.setMiddleName(middleName);
		customer.setLastName(lastName);
		customer.setEmailId(emailId);
		customer.setComment(Arrays.asList(comments.split(NEW_LINE)));
		customer.setContacts(contacts);
		
		customerService.createCustomer(customer);
	}

}
