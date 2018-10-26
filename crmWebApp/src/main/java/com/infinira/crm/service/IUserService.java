package com.infinira.crm.service;

import com.infinira.crm.model.User;

public interface IUserService {
	public void createUser(User user);
	public boolean validateUser(User user);
}
