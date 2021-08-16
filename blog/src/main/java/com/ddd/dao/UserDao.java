package com.ddd.dao;

import com.ddd.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String  password);
}
