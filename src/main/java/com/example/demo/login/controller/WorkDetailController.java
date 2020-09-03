package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WorkDetailController {
    
    @GetMapping("/workDetail")
    public String getWorkDetail(Model model) {
        
        return "login/workDetail";
    }
    
    @PostMapping("/workDetail")
    public String postWorkDetail(Model model) {
        
    	return "login/workDetail";
    }
}