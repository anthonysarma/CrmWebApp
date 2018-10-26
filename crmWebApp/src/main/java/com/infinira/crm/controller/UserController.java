package com.infinira.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.infinira.crm.model.User;
import com.infinira.crm.service.UserService;

@RestController
@RequestMapping("/user")

public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	@ResponseBody
	public String authenticateUser(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password, ModelMap model) {
		User user = new User(Long.parseLong(userName), password);
		if (!userService.validateUser(user)) {
			model.put("errorMessage", "Invalid Credentials");
			return "403";
		}
		model.put("name", userName);
		model.put("password", password);
		return "welcome";
	}
}
