package com.example.demo.login.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

public interface UserDao {

	public int insertOne(User user) throws DataAccessException;

	public int countPermission() throws DataAccessException;

	public List<User> selectPermission() throws DataAccessException;

	public User selectOne(int userId) throws DataAccessException;

	public void updatePermission(int userId) throws DataAccessException;

	public void updateFrozen(int userId) throws DataAccessException;

	public User selectByEmail(String email) throws DataAccessException;

	public int updateEmail(User user) throws DataAccessException;

	public int updatePassword(User user, String newPassword) throws DataAccessException;

	public Optional<User> findByEmail(String email) throws DataAccessException;
	
	public void updateReissuePassword(int userId, String reissuePassword);
}