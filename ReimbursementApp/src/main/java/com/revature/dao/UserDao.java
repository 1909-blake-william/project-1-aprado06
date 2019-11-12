package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface UserDao {
	
	UserDao currentImplementation = new UserDaoSQL();
	
	List<User> findAll();
	
	User findByUsernameAndPassword(String username, String password);
	
	User findByUsername(String username);
}
