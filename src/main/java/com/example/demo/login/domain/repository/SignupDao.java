package com.example.demo.login.domain.repository;

import com.example.demo.login.domain.model.User;


public interface SignupDao {

	boolean hasExist(User user);
}
