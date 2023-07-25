package com.study.personal.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.personal.DTO.boardDTO;
import com.study.personal.Repository.boardRepository;
import com.study.personal.Service.boardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board/")
public class BoardController {
	
	@Autowired
	private boardService boardService;
	
	@RequestMapping()
	public String boardList(@ModelAttribute("userid") String userid, Model model){
		
		List<boardDTO> boardList = boardService.boardList();
		model.addAttribute("boardList", boardList);
		
		return "/board/boardList";
	}
	
	@PostMapping("/write")
	public String boardWrite(@ModelAttribute("userid") String userid) {
		
		return "/board/boardWrite";
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
	
	@GetMapping("/view")
	public String viewBoard(@RequestParam("id") String board_id, Model model) {
		
		boardDTO boardDTO = boardService.boardView(board_id);
		
		model.addAttribute("board", boardDTO);
		
		return "/board/boardView";
	}
	
	@PostMapping("/viewTOedit")
	public String editBoard(boardDTO boardDTO, Model model) {
		
		model.addAttribute("board", boardDTO);
		
		return "/board/boardEdit";
	}
	
	@PostMapping("/editTOview")
	public String Boardview(boardDTO boardDTO, Model model) {
		
		boardDTO = boardService.boardEdit(boardDTO);
		model.addAttribute("board", boardDTO);
		
		return "/board/boardView";
	}
	
	@PostMapping("/delete")
	public String BoardDelete(boardDTO boardDTO, RedirectAttributes redirectAttributes) {

		boardService.boardDelete(boardDTO);
		redirectAttributes.addAttribute("userid", boardDTO.getUserid());
		
		return "redirect:/board/";
	}
	
	
}
