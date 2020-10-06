package com.example.demo.schedulingTasksSample;

//import java.sql.Date;
//import java.text.SimpleDateFormat;
import java.time.LocalTime;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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
	
	@Autowired
	CurrentTimeService currentTimeService;

//	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
//
//	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron = "0/5 * * * * *", zone = "Asia/Tokyo")
	public void reportCurrentTime() {
		
//		log.info("The time is now {}", dateFormat.format(new Date()));
		
		LocalTime localTime = LocalTime.now();
		
		CurrentTime currentTime = new CurrentTime();
		
		currentTime.setCurrentTime02(localTime);
		
		currentTimeService.insertByBatch(currentTime);
		
		System.out.println(localTime);
		
	}
}