package com.example.demo.login.domain.service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PathUtilTest {

	@Autowired
	PathUtil pathUtil;

	@Test
	@DisplayName("user/1.jpgが返る")
	void createPath_1() {
		String path = "user";
		int userId = 1;
		String format = "jpg";
		String actual = pathUtil.createPath(path, userId, format);
		String expect = "user/1.jpg";
		assertEquals(actual, expect);
	}
	
	@Test
	@DisplayName("user/10.pngが返る")
	void createPath_2() {
		String path = "user";
		int userId = 10;
		String format = "png";
		String actual = pathUtil.createPath(path, userId, format);
		String expect = "user/10.png";
		assertEquals(actual, expect);
	}
}
