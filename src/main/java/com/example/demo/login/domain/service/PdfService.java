package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.Pdf;
import com.example.demo.login.domain.repository.PdfDao;



@Service
public class PdfService {
	
	@Autowired
	PdfDao dao;
	
	public List<Pdf> selectMany() {
		
		return dao.selectMany();
	}
}