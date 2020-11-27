package com.example.demo.login.domain.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserIconForm {

	private MultipartFile file;
}
