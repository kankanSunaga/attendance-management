package com.example.demo.login.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.batch.item.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.ChangeEmailForm;
import com.example.demo.login.domain.model.ChangePasswordForm;
import com.example.demo.login.domain.model.ContractForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.UserIconForm;
import com.example.demo.login.domain.service.UserIconService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.util.PathUtil;
import com.example.demo.login.domain.service.util.SessionUtil;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Controller
public class HamburgerMenuController {

	@Autowired
	UserService userService;

	@Autowired
	UserIconService userIconService;

	@Autowired
	SessionUtil sessionUtil;

	@Autowired
	HttpServletRequest request;

	@Autowired
	protected ResourceLoader resourceLoader;

	@Autowired
	PathUtil pathUtil;

	@GetMapping("/changeUserIcon")
	public String getChangeUserIcon(Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "login/changeUserIcon";
	}

	@PostMapping("/changeUserIcon")
	public String postChengeUserIcon(UserIconForm form, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		if (form.getFile().isEmpty()) {
			return "login/changeUserIcon";
		}

		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("updateStatus", userIconService.setImage(form.getFile(), userId));

		return "login/changeUserIcon";
	}

	@GetMapping("/changePassword")
	public String getChangePassword(@ModelAttribute ChangePasswordForm form, HttpServletRequest request, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		User user = userService.selectOne(userId);
		model.addAttribute("user", user);
		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "login/changePassword";

	}

	@PostMapping("/changePassword")
	public String postChengePassword(@ModelAttribute @Validated ChangePasswordForm form, BindingResult bindingResult,
			Model model, HttpServletRequest request) throws IOException {
		
		int userId = sessionUtil.getUserId(request);

		if (bindingResult.hasErrors()) {
			return getChangePassword(form, request, model);
		}

		model.addAttribute("status", userService.updatePassword(userId, form.getPassword(), form.getNewPassword()));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		
		return "login/changePassword";

	}

	@GetMapping("/changeEmail")
	public String getChangeEmail(@ModelAttribute ChangeEmailForm form, HttpServletRequest request, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		User user = userService.selectOne(userId);
		model.addAttribute("user", user);
		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "login/changeEmail";

	}

	@PostMapping("/changeEmail")
	public String postChengeEmail(@ModelAttribute @Validated ChangeEmailForm form, BindingResult bindingResult,
			Model model, HttpServletRequest request) throws IOException {

		if (bindingResult.hasErrors()) {
			return getChangeEmail(form, request, model);
		}

		int userId = sessionUtil.getUserId(request);

		User user = userService.selectOne(userId);
		user.setEmail(form.getNewEmail());
		userService.updateEmail(user);
		
		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "redirect:/changeEmail";

	}

	@GetMapping("/changeContractTime")
	public String getChangeContractTime(@ModelAttribute ContractForm form, Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		int userId = sessionUtil.getUserId(request);
		
		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "login/changeContractTime";
	}

	@GetMapping("/changeContract")
	public String getChangeContract(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int userId = sessionUtil.getUserId(request);
		
		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "login/changeContract";
	}
}