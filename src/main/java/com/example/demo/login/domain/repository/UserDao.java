package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;


public interface UserDao {
	
	public int insertOne(User user) throws DataAccessException;
	
	public int countPermission() throws DataAccessException;
	
	public List<User> selectPermission() throws DataAccessException;
	
	public User selectOne(int userId) throws DataAccessException;
	
	public int updatePermission(User user) throws DataAccessException;
	
	public int updateFrozen(User user) throws DataAccessException;
	
	public User selectByEmail(String email) throws DataAccessException;
	
	public int updateEmail(User user) throws DataAccessException;
	
}

