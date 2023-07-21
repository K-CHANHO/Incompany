package com.study.personal.Service;

import java.util.List;
import java.util.UUID;

import com.study.personal.DTO.boardDTO;
import com.study.personal.Entity.Board;

public interface boardService {
	
	void save(boardDTO boardDTO);
	
	List<boardDTO> boardList();

	default Board boardDTOToBoard(boardDTO boardDTO) {
			
		Board board = Board.builder()
				.board_id(UUID.randomUUID().toString())
				.userid(boardDTO.getUserid())
				.content(boardDTO.getContent())
				.build();
			
			return board;
	}
	
	default boardDTO boardToBoardDTO(Board board) {
		
		boardDTO DTO = boardDTO.builder()
				.board_no(board.getBoard_no())
				.userid(board.getUserid())
				.content(board.getContent())
				.build();
		
		return DTO;
	}
	
	
}
