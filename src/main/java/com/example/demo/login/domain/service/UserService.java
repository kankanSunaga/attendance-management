package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService {
	@Autowired
	UserDao dao;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	// insert用メソッド
	public boolean insert(User user) {

		int rowNumber = dao.insertOne(user);
		boolean result = false;
		if (rowNumber > 0) {
			result = true;
		}
		return result;
	}

	// カウント用メソッド
	public int countPermission() {
		return dao.countPermission();
	}

	// 未承認ユーザー取得メソッド
	public List<User> selectPermission() {
		return dao.selectPermission();
	}

	// １件取得用メッソド
	public User selectOne(int userId) {
		return dao.selectOne(userId);
	}

	// 承認ステータス更新用メソッド
	public boolean updatePermission(User user) {

		int rowNumber = dao.updatePermission(user);

		boolean result = false;
		if (rowNumber > 0) {
			result = true;
		}
		return result;
	}

	// 凍結ステータス更新用メソッド
	public boolean updateFrozen(User user) {

		int rowNumber = dao.updateFrozen(user);

		boolean result = false;
		if (rowNumber > 0) {
			result = true;
		}
		return result;
	}

	// emailで検索したユーザーのuserIdを返却
	public User selectByEmail(String email) {
		return dao.selectByEmail(email);
	}
	
	public int updateEmail(User user) {
		return dao.updateEmail(user);
     }
	
	
	public boolean updatePassword(int userId, String oldPassword, String newPassword) {
		
		String encodedPassword = dao.selectOne(userId).getPassword();
		
		boolean status = false;
		
		if(passwordEncoder.matches(oldPassword, encodedPassword)) {

			User user = selectOne(userId);
			dao.updatePassword(user, newPassword);
			
			status = true;
			
		} 
		
		return status;
    	
    }
	
}

