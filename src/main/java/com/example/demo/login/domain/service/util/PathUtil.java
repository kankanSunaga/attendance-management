package com.example.demo.login.domain.service.util;

import org.springframework.stereotype.Service;

@Service
public class PathUtil {

	public String createPath(String path, int userId, String format) {
		
		String createPath = path + "/" + userId + "." + format;
		
		return createPath;
	}
}