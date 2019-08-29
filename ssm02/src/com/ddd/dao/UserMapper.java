package com.ddd.dao;

import org.springframework.stereotype.Repository;

import com.ddd.pojo.User;

public interface UserMapper {
  public User findUserBynamepwd(User user) throws Exception;
}
