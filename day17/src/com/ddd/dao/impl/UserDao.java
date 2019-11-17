package com.ddd.dao.impl;

import com.ddd.entiy.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public List<User>  findall();
    public User findUserByUsernameAndPassword(String username,String password);

    void addUser(User user);

    void deleteUserByid(int i);

    User findUserById(int id);

    void updateUserById(User user);
//查询总记录数
    int findTotalCount(Map<String, String[]> condition);
//分页查询每页记录
    List<User> findByPage(int start, int rows,Map<String, String[]> condition);
}
