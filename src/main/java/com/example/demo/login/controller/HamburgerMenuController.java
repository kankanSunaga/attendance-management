package com.example.demo.login.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.ChangeContractTimeForm;
import com.example.demo.login.domain.model.ChangeEmailForm;
import com.example.demo.login.domain.model.ChangePasswordForm;
import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.model.ContractForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.UserIconForm;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.UserIconService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.util.PathUtil;
import com.example.demo.login.domain.service.util.SessionUtil;

@Controller
public class HamburgerMenuController {

	@Autowired
	UserService userService;

	@Autowired
	UserIconService userIconService;

	@Autowired
	SessionUtil sessionUtil;

	@Autowired
	ContractService contractService;

	@Autowired
	SessionUtil sessionutil;

	@Autowired
	PathUtil pathUtil;

	@Autowired
	HttpServletRequest request;

	@Autowired
	ResourceLoader resourceLoader;

	@GetMapping("/changeUserIcon")
	public String getChangeUserIcon(Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/changeUserIcon";
	}

	@PostMapping("/changeUserIcon")
	public String postChengeUserIcon(UserIconForm form, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());		

		return "redirect:/home";
	}

	@GetMapping("/changePassword")
	public String getChangePassword(@ModelAttribute ChangePasswordForm form, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		model.addAttribute("user", userService.selectOne(userId));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/changePassword";

	}

	@PostMapping("/changePassword")
	public String postChengePassword(@ModelAttribute @Validated ChangePasswordForm form, BindingResult bindingResult,
			Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		if (bindingResult.hasErrors()) {
			return getChangePassword(form, model);
		}

		model.addAttribute("status", userService.updatePassword(userId, form.getPassword(), form.getNewPassword()));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/changePassword";

	}

	@GetMapping("/changeEmail")
	public String getChangeEmail(@ModelAttribute ChangeEmailForm form, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		model.addAttribute("user", userService.selectOne(userId));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/changeEmail";

	}

	@PostMapping("/changeEmail")
	public String postChengeEmail(@ModelAttribute @Validated ChangeEmailForm form, BindingResult bindingResult,
			Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		if (bindingResult.hasErrors()) {
			return getChangeEmail(form, model);
		}

		User user = userService.selectOne(userId);
		userService.updateEmail(userService.setNewEmail(user, form));

		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "redirect:/changeEmail";

	}

	@GetMapping("/changeContractTime")
	public String getChangeContractTime(@ModelAttribute ChangeContractTimeForm form, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		Contract underContract = contractService.underContract(userId, LocalDate.now());
		contractService.setOldContractTime(userId, underContract, form);

		model.addAttribute("ChangeContractTimeForm", form);
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/changeContractTime";

	}

	@PostMapping("/changeContractTime")
	public String postChangeContractTime(@ModelAttribute @Validated ChangeContractTimeForm form,
			BindingResult bindingResult, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		if (bindingResult.hasErrors()) {
			return getChangeContractTime(form, model);
		}

		Contract updateContractTime = contractService.setUpdateContractTime(form, userId);
		model.addAttribute("status", contractService.updateContract(updateContractTime));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/changeContractTime";

	}

	@GetMapping("/changeContract")
	public String getChangeContract(Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		model.addAttribute("contract", contractService.underContract(userId, LocalDate.now()));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/changeContract";
	}

	@PostMapping("/changeContract")
	public String postChangeContract(ContractForm form, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		Contract underContract = contractService.underContract(userId, LocalDate.now());
		contractService.updateEndDate(contractService.setEndDate(underContract, form));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/contract";
	}
}