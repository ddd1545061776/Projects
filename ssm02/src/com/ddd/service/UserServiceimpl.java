package com.ddd.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ddd.dao.UserMapper;
import com.ddd.pojo.User;
@Service("userService")
public class UserServiceimpl implements UserService {
	  @Autowired
      private UserMapper userMapper;
	public User findUserBynamepwd(User user)throws Exception {
		
		return userMapper.findUserBynamepwd(user);
	}

}
