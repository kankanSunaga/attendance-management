package com.example.demo.login.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.model.ContractForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.UserService;

@Controller
public class ContractController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private ContractService contractService;
	
	@GetMapping("/contract")
	public String getContract(@ModelAttribute ContractForm form, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		// SpringSecurityのセッションの呼出(emailの呼出)
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// emailで検索したユーザーのuserIdの取得
		User user = userService.selectByEmail(auth.getName());
				
		// セッションの保持(userId)
		HttpSession session = request.getSession();
		session.setAttribute("userId", user.getUserId());
		
		
		return "login/contract";
	}
    
	@PostMapping("/contract")
	public String postContract(@ModelAttribute @Validated ContractForm form, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return getContract(form, model, null, null);
		}
		
		System.out.println(form);
		
		//insert用変数
		Contract contract = new Contract();
		
		contract.setContractTime(form.getContractTime());
		contract.setStartTime(form.getStartTime());
		contract.setBreakTime(form.getBreakTime());
		contract.setEndTime(form.getEndTime());
		contract.setStartDate(form.getStartDate());
		contract.setOfficeName(form.getOfficeName());

		boolean result = contractService.insert(contract);

		if(result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}
		
		//勤務開始日に以前か以降かのチェック
		LocalDate formDate = form.getStartDate();
		LocalDate currentDate = LocalDate.now();
		
		if(formDate.compareTo(currentDate) > 0) {
			return "login/startDateWaiting";
		} else {
			return "login/login";
		}
	}
}