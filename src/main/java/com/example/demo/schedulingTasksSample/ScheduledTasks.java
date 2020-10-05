package com.example.demo.schedulingTasksSample;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.login.domain.model.CurrentTime;
import com.example.demo.login.domain.repository.CurrentTimeDao;
import com.example.demo.login.domain.service.CurrentTimeService;

@Component
public class ScheduledTasks {
	
	@Autowired
	CurrentTimeDao dao;

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
//		log.info("The time is now {}", dateFormat.format(new Date()));
		int currentTime = CurrentTimeService.insertByBatch(currentTime);
	}
}