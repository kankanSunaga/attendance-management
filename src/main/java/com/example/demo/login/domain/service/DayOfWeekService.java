package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

@Service
public class DayOfWeekService {

	@Autowired
	DayOfWeekService dayOfWeekService;

	public boolean hasHoliday(LocalDate termination) throws IOException {

		String holiday = termination.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

		if (holiday.equals("Sunday") || holiday.equals("Saturday")) {
			return true;
		}

		final String API_URL = "http://s-proj.com/utils/checkHoliday.php?kind=h&date=" + termination;

		URL url = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		String readLine = "";
		String writeContent = "";

		try {
			url = new URL(API_URL);
			isr = new InputStreamReader(url.openStream());
			br = new BufferedReader(isr);
			while ((readLine = br.readLine()) != null)
				writeContent = writeContent + readLine;

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			isr.close();
			br.close();
		}

		if (writeContent.equals("holiday")) {
			return true;
		} else {
			return false;
		}
	}

	public LocalDate getLastWeekDay(LocalDate termination) throws IOException {

		LocalDate lastDate = termination.with(TemporalAdjusters.lastDayOfMonth());

		while (true) {
			if (dayOfWeekService.hasHoliday(lastDate)) {
				lastDate = lastDate.minusDays(1);
			} else {
				break;
			}
		}
		return lastDate;
	}
}