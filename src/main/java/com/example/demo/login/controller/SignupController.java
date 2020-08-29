package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;
@Controller
public class SignupController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/signup")
    public String getSignup(@ModelAttribute SignupForm form, Model model) {
        
        return "login/signup";
    }
    
    @PostMapping("/signup")
    public String postSignup(@ModelAttribute @Validated SignupForm form, BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            
            return getSignup(form, model);
        }
        
        System.out.println(form);
        
        User user = new User();
        
        user.setUserName(form.getUserName());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());
        user.setRole("ROLE_GENERAL");
        
        boolean result = userService.insert(user);
        
        if(result == true) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }
        
        return "login/unapproved";
    }
}