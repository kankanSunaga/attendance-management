package com.example.demo.login.domain.repository;

import java.util.Optional;

import com.example.demo.login.domain.model.ReissuePassword;


public interface ReissuePasswordDao {

	void insertOne(ReissuePassword reissuePassword);

	Optional<ReissuePassword> findByToken(String token);
	
	void updateChanged(int userIdOpt);
}