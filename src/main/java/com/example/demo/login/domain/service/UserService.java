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
    
    public boolean insert(User user) {
        
        int rowNumber = dao.insertOne(user);
        boolean result = false;
        if(rowNumber > 0) {
            result = true;
        }
        return result;
    }
    
    
    public int countPermission() {
    	return dao.countPermission() ;
    }
    
    
    public List<User> selectPermission(){
    	return dao.selectPermission();    	
    }
    
    
    public User selectOne(int userId) {
    	return dao.selectOne(userId);
    }
    
    
    public boolean updatePermission(User user) {
    	
    	int rowNumber = dao.updatePermission(user);
    	
    	boolean result = false;
    	if(rowNumber > 0) {
    		result = true;
    	}
    	return result;
    }
    
    
    public boolean updateFrozen(User user) {
    	
    	int rowNumber = dao.updateFrozen(user);
    	
    	boolean result = false;
    	if(rowNumber > 0) {
    		result = true;
    	}
    	return result;
    }
    

    public User selectByEmail(String email) {
    	User userId = dao.selectByEmail(email);
    	
    	return userId;
    }
    

    public boolean updateEmail(User user) {
    	int status = dao.updateEmail(user);
    	boolean updateEmailResult = false;
    	if(status > 0) {
    		updateEmailResult = true;
    	}
    	
    	return updateEmailResult;
    }
    
}