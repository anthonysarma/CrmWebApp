package com.infinira.crm.service;

import org.springframework.stereotype.Service;

import com.infinira.crm.dao.UserDao;
import com.infinira.crm.model.User;

@Service
public class UserService implements IUserService {

	private static volatile UserService userService;

	// Singleton Pattern
	private UserService() {
	}

	public static UserService getInstance() {
		if (userService == null) {
			synchronized (UserService.class) {
				if (userService == null) {
					userService = new UserService();
				}
			}
		}
		return userService;
	}
	@Override
	public void createUser(User user) {
		UserDao.createUser(user);
	}
	
	@Override
	public boolean validateUser(User user) {
		return UserDao.authenticateUser(user);
	}

}
