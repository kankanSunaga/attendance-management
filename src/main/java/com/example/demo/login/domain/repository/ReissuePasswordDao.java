package com.example.demo.login.domain.repository;

import java.util.Optional;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.ReissuePassword;


public interface ReissuePasswordDao {

	void insertOne(ReissuePassword reissuePassword) throws DataAccessException;

	Optional<ReissuePassword> findByToken(String token);
	
	void updateChanged(int userIdOpt);
}