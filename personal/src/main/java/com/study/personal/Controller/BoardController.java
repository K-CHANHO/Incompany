package com.study.personal.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board/")
public class BoardController {
	
	@PostMapping
	public String boardList(@ModelAttribute("userid") String userid){
		return "boardList";
	}
	
	@GetMapping("/write")
	public String 
}
