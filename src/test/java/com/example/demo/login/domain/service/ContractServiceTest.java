package com.example.demo.login.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContractServiceTest {
	
	@Autowired
	ContractService contractService;

	@Test
    @DisplayName("notCloseが返る")
    void selectDisplay_1() {
		String yearMonth = "202101";
		boolean deadlineStatus = false;
		boolean requestStatus = true;
        LocalDate nowDate = LocalDate.now();
        String actual = contractService.selectDisplay(yearMonth, deadlineStatus, requestStatus, nowDate);
        String expect = "notClose";
        assertEquals(actual, expect);
    }
	
	@Test
    @DisplayName("nowRequestが返る")
    void selectDisplay_2() {
		String yearMonth = "202101";
		boolean deadlineStatus = true;
		boolean requestStatus = true;
        LocalDate nowDate = LocalDate.now();
        String actual = contractService.selectDisplay(yearMonth, deadlineStatus, requestStatus, nowDate);
        String expect = "nowRequest";
        assertEquals(actual, expect);
    }
	
	@Test
    @DisplayName("lastMonthが返る")
    void selectDisplay_3() {
		String yearMonth = "202101";
		boolean deadlineStatus = true;
		boolean requestStatus = false;
        LocalDate nowDate = LocalDate.of(2021, 2, 1);
        String actual = contractService.selectDisplay(yearMonth, deadlineStatus, requestStatus, nowDate);
        String expect = "lastMonth";
        assertEquals(actual, expect);
    }
	
	@Test
    @DisplayName("hideが返る")
    void selectDisplay_4() {
		String yearMonth = "202101";
		boolean deadlineStatus = true;
		boolean requestStatus = false;
        LocalDate nowDate = LocalDate.of(2021, 3, 1);
        String actual = contractService.selectDisplay(yearMonth, deadlineStatus, requestStatus, nowDate);
        String expect = "hide";
        assertEquals(actual, expect);
    }
}