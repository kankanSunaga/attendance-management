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
}