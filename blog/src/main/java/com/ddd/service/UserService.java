package com.ddd.service;

import com.ddd.domain.User;

public interface UserService {
    User checkUser(String username,String password);
}
