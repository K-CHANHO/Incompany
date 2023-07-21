package com.study.personal.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.personal.DTO.boardDTO;
import com.study.personal.Service.boardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board/")
public class BoardController {
	
	@Autowired
	private boardService boardService;
	
	@GetMapping()
	public String boardList(@ModelAttribute("userid") String userid, Model model){
		
		List<boardDTO> boardList = boardService.boardList();
		model.addAttribute("boardList", boardList);
		
		return "/board/boardList";
	}
	
	@PostMapping
	public String boardList2(@ModelAttribute("userid") String userid, Model model){
		
		List<boardDTO> boardList = boardService.boardList();
		model.addAttribute("boardList", boardList);
		
		return "/board/boardList";
	}
	
	@PostMapping("/write")
	public String boardWrite(@ModelAttribute("userid") String userid) {
		
		return "/board/write";
	}
	
	@PostMapping("/save")
	public String boardSave(@ModelAttribute boardDTO boardDTO, RedirectAttributes redirectAttributes) {
		
		boardService.save(boardDTO);
		redirectAttributes.addAttribute("userid", boardDTO.getUserid());
		return "redirect:/board/";
	}
	
	@PostMapping("/toMain")
	public String backToMain(@ModelAttribute("userid") String userid) {
		
		return "main";
	}
}
