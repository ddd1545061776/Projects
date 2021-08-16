package com.ddd.service.impl;

import com.ddd.dao.UserDao;
import com.ddd.domain.User;
import com.ddd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, password);
        return user;
    }
}
