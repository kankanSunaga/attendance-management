package com.example.demo.login.domain.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService {
	
	@Autowired 
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;
	
	//insert用メソッド
	public boolean insert (User user) {
		
		int rowNumber = dao.insertOne(user);
		boolean result = false;
		if(rowNumber > 0) {
			result = true;
		}
	}
	
	//count用メソッド
	public int count() {
		return dao.count();
	}
	
	//全件取得用メソッド
	public List<User> selectMany() {
		return dao.selecMany();
	}
	
	//1件取得用メソッド
	public User selectOne(int userId) {
		return dao.selectOne(userId);
	}
	
	//1件更新用メソッド
	public boolean updateOne(User user) {
		int rowNumber = dao.uodateOne(user);
		boolean result = false;
		if(rowNumber > 0) {
			result = true;
		}
		return result;
	}
	
	//1件削除用メソッド
	public boolean deleteOne(int userId) {
		int rowNumber = dao.deleteOne(userId);
		boolean result = false;
		if(rowNumber > 0) {
			result = true;
		}
		return result;
	}
}