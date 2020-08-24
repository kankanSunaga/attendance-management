package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class helloBootstrapController {
	//エビデンス用のHTML画面のGETコントローラー
	@GetMapping("/hellobootstrap")
	public String getHelloBootstrap(Model model) {
		//hellobootstrapに画面遷移
		return "/helloBootstrap";
	}

}
