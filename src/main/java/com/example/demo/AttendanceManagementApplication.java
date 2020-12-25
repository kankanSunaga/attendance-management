package com.example.demo;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class AttendanceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendanceManagementApplication.class, args);
	}

	@Bean("ForgotPasswordService#sendMail")
	public Executor taskExecutor1() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setThreadNamePrefix("Thread1--");
		executor.initialize();

		return executor;
	}
}