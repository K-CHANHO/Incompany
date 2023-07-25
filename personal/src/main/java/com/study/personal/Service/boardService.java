package com.study.personal.Service;

import java.util.List;
import java.util.UUID;

import com.study.personal.DTO.boardDTO;
import com.study.personal.Entity.Board;

public interface boardService {
	
	void save(boardDTO boardDTO);
	
	List<boardDTO> boardList();

	boardDTO boardView(String board_id);

	boardDTO boardEdit(boardDTO boardDTO);

	void boardDelete(boardDTO boardDTO);
	
	default Board boardDTOToBoard(boardDTO boardDTO) {
			
		Board board = Board.builder()
				.board_no(boardDTO.getBoard_no())
				.board_id(UUID.randomUUID().toString())
				.userid(boardDTO.getUserid())
				.content(boardDTO.getContent())
				.build();
			
			return board;
	}
	
	default boardDTO boardToBoardDTO(Board board) {
		
		boardDTO DTO = boardDTO.builder()
				.board_no(board.getBoard_no())
				.board_id(board.getBoard_id())
				.userid(board.getUserid())
				.content(board.getContent())
				.build();
		
		return DTO;
	}
	
}
