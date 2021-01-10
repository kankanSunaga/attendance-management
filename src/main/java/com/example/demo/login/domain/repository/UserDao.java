package com.example.demo.login.domain.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.login.domain.model.User;


public interface UserDao {

	int insertOne(User user);

	int countPermission();

	List<User> selectPermission();

	User selectOne(int userId);

	void updatePermission(int userId);

	void updateFrozen(int userId);

	User selectByEmail(String email);

	int updateEmail(User user);

	int updatePassword(User user, String newPassword);

	Optional<User> findByEmail(String email);
	
	void updateReissuePassword(int userId, String reissuePassword);	
}