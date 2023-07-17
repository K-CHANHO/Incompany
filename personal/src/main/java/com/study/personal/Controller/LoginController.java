package com.study.personal.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Dto.memberDTO;

@Controller
public class LoginController {
	
	@GetMapping("/member_join")
	public void goToLoginJoin() {}
	
	@PostMapping("/member_join")
	public String member_join(@RequestParam("userid") String userid, @RequestParam("passwd") String passwd) {
		
		System.out.println(userid);
		System.out.println(passwd);
		
		return "index";
	}

}
