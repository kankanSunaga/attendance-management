package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.login.domain.model.WorkDetail;
import com.example.demo.login.domain.model.WorkDetailForm;
import com.example.demo.login.domain.service.WorkDetailService;

@Controller
public class WorkDetailController {
	
	@Autowired
	private WorkDetailService workDetailService;
	
    @GetMapping("/workDetail")
    public String getWorkDetail(@ModelAttribute WorkDetailForm form, Model model) {
        return "login/workDetail";
    }
    
    @PostMapping("/workDetail")
    public String postWorkDetail(@ModelAttribute @Validated WorkDetailForm form, BindingResult bindingResult, Model model) {
    	
    	if (bindingResult.hasErrors()) {
            return getWorkDetail(form, model);
        }
        
        System.out.println(form);
        
        //insert用変数
        WorkDetail workDetail = new WorkDetail();
        
        workDetail.setContractTime(form.getContractTime());
        workDetail.setStartTime(form.getStartTime());
        workDetail.setBreakTime(form.getBreakTime());
        workDetail.setEndTime(form.getEndTime());
        workDetail.setStartDate(form.getStartDate());
        workDetail.setOfficeName(form.getOfficeName());
        
        boolean result = workDetailService.insert(workDetail);
        
        if(result == true) {
        	System.out.println("insert成功");
        } else {
        	System.out.println("insert失敗");
        }
        
        return "login/startDateWaiting";
    }
}