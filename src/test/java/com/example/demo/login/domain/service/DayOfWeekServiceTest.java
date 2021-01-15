package com.example.demo.login.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DayOfWeekServiceTest {
	
	@Autowired
	DayOfWeekService dayOfWeekService;

	@Test
	@DisplayName("引数のLocalDateが、休日か判定")
	void hasHoliday_1() throws IOException {
		LocalDate termination = LocalDate.of(2021, 1, 2);
		boolean actual = dayOfWeekService.hasHoliday(termination);
		boolean expect = true;
		assertEquals(actual, expect);
	}
	
	@Test
	@DisplayName("引数のLocalDateが、休日か判定")
	void hasHoliday_2() throws IOException {
		LocalDate termination = LocalDate.of(2021, 1, 4);
		boolean actual = dayOfWeekService.hasHoliday(termination);
		boolean expect = false;
		assertEquals(actual, expect);
	}
	
	@Test
	@DisplayName("引数のLocalDateから、最終平日を判定")
	void getLastWeekDay_1() throws IOException {
		LocalDate termination = LocalDate.of(2021, 1, 1);
		LocalDate actual = dayOfWeekService.getLastWeekDay(termination);
		LocalDate expect = LocalDate.of(2021, 1, 29);
		assertEquals(actual, expect);
	}
	
	@Test
	@DisplayName("引数のLocalDateから、最終平日を判定")
	void getLastWeekDay_2() throws IOException {
		LocalDate termination = LocalDate.of(2021, 2, 1);
		LocalDate actual = dayOfWeekService.getLastWeekDay(termination);
		LocalDate expect = LocalDate.of(2021, 2, 26);
		assertEquals(actual, expect);
	}
}