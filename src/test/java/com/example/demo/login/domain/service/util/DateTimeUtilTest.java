package com.example.demo.login.domain.service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import com.example.demo.login.domain.service.util.DateTimeUtil;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DateTimeUtilTest {
	
	@Autowired
	DateTimeUtil dateTimeUtil;

	@Test
    @DisplayName("2021が返る")
    void toStringDate_1() {
        LocalDate localDate = LocalDate.of(2021, 01, 01);
        String actual = dateTimeUtil.toStringDate(localDate, "yyyy");
        String expect = "2021";
        assertEquals(actual, expect);
    }

	@Test
    @DisplayName("202101が返る")
    void toStringDate_2() {
        LocalDate localDate = LocalDate.of(2021, 01, 01);
        String actual = dateTimeUtil.toStringDate(localDate, "yyyyMM");
        String expect = "202101";
        assertEquals(actual, expect);
    }

	@Test
    @DisplayName("20210101が返る")
    void toStringDate_3() {
        LocalDate localDate = LocalDate.of(2021, 01, 01);
        String actual = dateTimeUtil.toStringDate(localDate, "yyyyMMdd");
        String expect = "20210101";
        assertEquals(actual, expect);
    }

	@Test
    @DisplayName("2021年01月01日が返る")
    void toStringDate_4() {
        LocalDate localDate = LocalDate.of(2021, 01, 01);
        String actual = dateTimeUtil.toStringDate(localDate, "yyyy年MM月dd日");
        String expect = "2021年01月01日";
        assertEquals(actual, expect);
    }

	@Test
    @DisplayName("2021年01月01日(金)が返る")
    void toStringDate_5() {
        LocalDate localDate = LocalDate.of(2021, 01, 01);
        String actual = dateTimeUtil.toStringDate(localDate, "yyyy年MM月dd日(E)");
        String expect = "2021年01月01日(金)";
        assertEquals(actual, expect);
    }

	@Test
	@DisplayName("202101が返る")
	void toStringYearMonth_1() {
		int year = 2021;
		int month = 1;
		String actual = dateTimeUtil.toStringYearMonth(year, month);
		String expect = "202101";
		assertEquals(actual, expect);
	}
	
	@Test
	@DisplayName("202110が返る")
	void toStringYearMonth_2() {
		int year = 2021;
		int month = 10;
		String actual = dateTimeUtil.toStringYearMonth(year, month);
		String expect = "202110";
		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("2021-01-01が返る")
	void BeginningOfMonth_1() {
		String yearMonth = "202101";
		LocalDate actual = dateTimeUtil.BeginningOfMonth(yearMonth);
		LocalDate expect = LocalDate.of(2021, 1, 1);
		assertEquals(actual, expect);
	}
	
	@Test
	@DisplayName("2021-10-10が返る")
	void BeginningOfMonth_2() {
		String yearMonth = "202110";
		LocalDate actual = dateTimeUtil.BeginningOfMonth(yearMonth);
		LocalDate expect = LocalDate.of(2021, 10, 1);
		assertEquals(actual, expect);
	}

	@Test
	@DisplayName("0時間10分が返る")
	void getTotalTime_1() {
		int totalTime = 10;
		String actual = dateTimeUtil.getTotalTime(totalTime);
		String expect = "0時間10分";
		assertEquals(actual, expect);
	}
	
	@Test
	@DisplayName("1時間0分が返る")
	void getTotalTime_2() {
		int totalTime = 60;
		String actual = dateTimeUtil.getTotalTime(totalTime);
		String expect = "1時間0分";
		assertEquals(actual, expect);
	}
	
	@Test
	@DisplayName("1時間40分が返る")
	void getTotalTime_3() {
		int totalTime = 100;
		String actual = dateTimeUtil.getTotalTime(totalTime);
		String expect = "1時間40分";
		assertEquals(actual, expect);
	}
	
	@Test
	@DisplayName("2021-01-01が返る")
	void createDate_1() {
		String yyyyMMdd = "20210101";
		LocalDate actual = dateTimeUtil.createDate(yyyyMMdd);
		LocalDate expect = LocalDate.of(2021, 1, 1);
		assertEquals(actual, expect);
	}
	
	@Test
	@DisplayName("2021-10-10が返る")
	void createDate_2() {
		String yyyyMMdd = "20211010";
		LocalDate actual = dateTimeUtil.createDate(yyyyMMdd);
		LocalDate expect = LocalDate.of(2021, 10, 10);
		assertEquals(actual, expect);
	}
}