package com.study.personal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.study.personal.DTO.memberDTO;
import com.study.personal.Entity.Member;
import com.study.personal.Service.memberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@Autowired
	private memberService memberService;
	
	@GetMapping("/member_join")
	public void goToLoginJoin() {}
	
	@PostMapping("/member_join")
	public String member_join(memberDTO memberDTO) {
		
		System.out.println(memberDTO);
		memberService.join(memberDTO);
		
		return "index";
	}
	
	@PostMapping("/login")
	public String login(memberDTO memberDTO) {
		
		Member member = memberService.login(memberDTO);
		
		return "main";
	}

}
