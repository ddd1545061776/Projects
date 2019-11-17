package com.ddd.service;

import com.ddd.entiy.PageBean;
import com.ddd.entiy.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> findall();
    public  User login(User user);

   public  void addUser(User user);

    void deleteUserByid(int i);

    User findByIdUser(int i);

    void updateUserById(User user);

    void deleteSelectedUser(String[] uids);

    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
