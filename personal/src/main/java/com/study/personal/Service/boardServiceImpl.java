package com.study.personal.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.personal.DTO.boardDTO;
import com.study.personal.Entity.Board;
import com.study.personal.Repository.boardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class boardServiceImpl implements boardService{

	@Autowired
	private boardRepository boardRepository;
	
	@Override
	public void save(boardDTO boardDTO) {
		
		Board board = boardDTOToBoard(boardDTO);
		
		boardRepository.save(board);
		
	}

	@Override
	public List<boardDTO> boardList() {
		
		List<boardDTO> boardList = new ArrayList<>();
		
		List<Board> entityBoardList = boardRepository.findAll();
		for (Board board : entityBoardList) {
			boardDTO dtoBoardList = boardToBoardDTO(board);
			boardList.add(dtoBoardList);
		}
		
		return boardList;
	}

	@Override
	public boardDTO boardView(String board_id) {
		log.error(board_id);
		Board board = boardRepository.findByUUID(board_id);
		log.error(board.toString());
		boardDTO boardDTO = boardToBoardDTO(board);
		
		return boardDTO;
	}
	
	
	

}
