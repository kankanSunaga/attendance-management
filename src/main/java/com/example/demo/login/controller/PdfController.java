package com.example.demo.login.controller;

import java.io.IOException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.DayOfWeekService;
import com.example.demo.login.domain.service.MonthService;
import com.example.demo.login.domain.service.PdfService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.WorkTimeService;
import com.example.demo.login.domain.service.util.DateTimeUtil;
import com.example.demo.login.domain.service.util.SessionUtil;

@Controller
public class PdfController {

	@Autowired
	PdfService pdfService;

	@Autowired
	UserService userService;

	@Autowired
	ContractService contractService;

	@Autowired
	WorkTimeService workTimeService;

	@Autowired
	DayOfWeekService dayOfWeekService;
	
	@Autowired
	MonthService monthService;

	@Autowired
	DateTimeUtil dateTimeUtil;

	@Autowired
	SessionUtil sessionUtil;
	
	@GetMapping("/contract/{contractId}/{yearMonth}/pdfDownload")
	public void getPdfDownload(Model model, HttpServletRequest request, HttpServletResponse response,
			@PathVariable("contractId") int contractId, @PathVariable("yearMonth") String yearMonth)
			throws IOException {

		int userId = sessionUtil.getUserId(request);
		int monthId = monthService.selectMonthTable(contractId, yearMonth).getMonthId();

		LocalDate minWorkDay = dateTimeUtil.BeginningOfMonth(yearMonth);
		String strYearMonth = dateTimeUtil.toStringDate(minWorkDay, "yyyy年MM月");

		List<WorkTime> workTimeMonth = monthService.getMonth(monthId);
		List<Map<String, Object>> workTimeList = workTimeService.changeTimeFormat(workTimeMonth);
		LinkedHashMap<String, Object> calender = workTimeService.calender(yearMonth);
		LinkedHashMap<String, Object> calenderObject = workTimeService.setCalenderObject(calender, workTimeList);

		TemplateEngine engine = pdfService.initializeTemplateEngine();
		IContext makeContext = pdfService.makeContext(userId, strYearMonth, workTimeMonth, calender, calenderObject);
		pdfService.createPdf(userId, workTimeMonth, engine, makeContext);

		pdfService.pdfDownload(userId, response);
	}
}