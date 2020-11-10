package com.example.demo.login.controller;

import java.io.IOException;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.DateTimeUtilityService;
import com.example.demo.login.domain.service.DayOfWeekService;
import com.example.demo.login.domain.service.PdfService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.WorkTimeService;

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
	DateTimeUtilityService dateTimeUtilityService;

	@GetMapping("/contract/{contractId}/{yearMonth}/pdfDownload")
	public void getPdfDownload(@ModelAttribute WorkTime form, Model model, HttpServletRequest request,
			HttpServletResponse response, @PathVariable("contractId") int contractId,
			@PathVariable("yearMonth") String yearMonth) throws IOException {

		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");

		LocalDate minWorkDay = dateTimeUtilityService.BeginningOfMonth(yearMonth);

		LocalDate maxWorkDay = minWorkDay.with(TemporalAdjusters.lastDayOfMonth());

		List<WorkTime> contractDayList = workTimeService.rangedSelectMany(contractId, minWorkDay, maxWorkDay);

		String strYearMonth = dateTimeUtilityService.toStringDate(minWorkDay, "yyyy年MM月");

		pdfService.createPdf(userId, contractId, yearMonth, minWorkDay, maxWorkDay, strYearMonth, response);

		model.addAttribute("yearMonthUrl", yearMonth);
		model.addAttribute("contractDay", contractDayList);
	}
}