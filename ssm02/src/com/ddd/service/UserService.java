package com.ddd.service;

import org.springframework.stereotype.Service;

import com.ddd.pojo.User;
@Service
public interface UserService {
	public User findUserBynamepwd(User user) throws Exception;
}
