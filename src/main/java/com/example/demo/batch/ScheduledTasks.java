package com.example.demo.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.login.domain.service.MonthService;

@Component
public class ScheduledTasks {

	@Autowired
	MonthService monthService;

	@Scheduled(cron = " * * * 1 * * ", zone = "Asia/Tokyo")
	public void updateDeadlineStatus() {

		monthService.updateToDeadline();
	}
}