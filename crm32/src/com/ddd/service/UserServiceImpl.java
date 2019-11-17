package com.ddd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ddd.entity.User;

import com.ddd.mapper.UserDao;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userdao;
	public User login(User user) {
		
		return userdao.login(user);
	}

}
