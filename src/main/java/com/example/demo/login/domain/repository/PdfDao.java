package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.Pdf;


public interface PdfDao {
	
	public List<Pdf> selectMany() throws DataAccessException;
	
}