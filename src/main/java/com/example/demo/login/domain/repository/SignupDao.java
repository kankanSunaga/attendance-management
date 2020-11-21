package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

public interface SignupDao {

	public boolean hasExist(User user) throws DataAccessException;
}
