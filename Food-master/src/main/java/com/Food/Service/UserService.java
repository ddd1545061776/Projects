package com.Food.Service;

import java.util.List;

import com.Food.Entity.User;

public interface UserService {
	public User UserLogin(String email,String password);
	public void UserRegister(User model);
	public User GetUserById(String userId);
	public void UserEdit(User model);
	public List<User> GetAllUser();
	public void DeleteUser(String userId);
	public void activeUser(String code);
}
