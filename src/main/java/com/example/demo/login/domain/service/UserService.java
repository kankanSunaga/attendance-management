package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;
@Service 
public class UserService { 
    @Autowired 
    UserDao dao;
    
    //insert用メソッド
    public boolean insert(User user) {
        
        int rowNumber = dao.insertOne(user);
        boolean result = false;
        if(rowNumber > 0) {
            result = true;
        }
        return result;
    }
    
    //カウント用メソッド
    public int countPermission() {
    	return dao.countPermission() ;
    }
    //未承認ユーザー取得メソッド
    public List<User> selectPermission(){
		return dao.selectPermission();    	
    }
    //１件取得用メッソド
    public User selectOne(int userId) {
    	return dao.selectOne(userId);
    }
    
}

