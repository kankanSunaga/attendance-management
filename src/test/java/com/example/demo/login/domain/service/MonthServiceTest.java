package com.example.demo.login.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MonthServiceTest {

	@Autowired
	MonthService monthService;

	@Test
	@DisplayName("trueが返る")
	void deadlineCheck_1() {
		boolean deadlineStatus = true;
		LocalDate lastWeekDay = LocalDate.of(2021, 1, 29);
		LocalDate nowDate = LocalDate.now();
		boolean actual = monthService.deadlineCheck(deadlineStatus, lastWeekDay, nowDate);
		boolean expect = true;
		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("falseが返る")
	void deadlineCheck_2() {
		boolean deadlineStatus = false;
		LocalDate lastWeekDay = LocalDate.of(2021, 1, 29);
		LocalDate nowDate = LocalDate.of(2021, 1, 28);
		boolean actual = monthService.deadlineCheck(deadlineStatus, lastWeekDay, nowDate);
		boolean expect = true;
		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("falseが返る")
	void deadlineCheck_3() {
		boolean deadlineStatus = false;
		LocalDate lastWeekDay = LocalDate.of(2021, 1, 29);
		LocalDate nowDate = LocalDate.of(2021, 1, 29);
		boolean actual = monthService.deadlineCheck(deadlineStatus, lastWeekDay, nowDate);
		boolean expect = false;
		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("falseが返る")
	void deadlineCheck_4() {
		boolean deadlineStatus = false;
		LocalDate lastWeekDay = LocalDate.of(2021, 1, 29);
		LocalDate nowDate = LocalDate.of(2021, 1, 30);
		boolean actual = monthService.deadlineCheck(deadlineStatus, lastWeekDay, nowDate);
		boolean expect = false;
		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("1時間30分が返る")
	void checkQuota_1() {
		int contractTime = 100;
		int totalTime = 10;
		String actual = monthService.checkQuota(contractTime, totalTime);
		String expect = "1時間30分";
		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("達成しましたが返る")
	void checkQuota_2() {
		int contractTime = 100;
		int totalTime = 100;
		String actual = monthService.checkQuota(contractTime, totalTime);
		String expect = "達成しました";
		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("達成しましたが返る")
	void checkQuota_3() {
		int contractTime = 100;
		int totalTime = 110;
		String actual = monthService.checkQuota(contractTime, totalTime);
		String expect = "達成しました";
		assertEquals(actual, expect);
	}
}