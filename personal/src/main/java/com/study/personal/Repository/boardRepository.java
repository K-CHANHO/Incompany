package com.study.personal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.study.personal.Entity.Board;

@Repository
public interface boardRepository extends JpaRepository<Board, String>{
	
	@Query(value = "SELECT b " +
					"FROM Board b " +
					"WHERE b.board_id LIKE :board_id")
	Board findByUUID(@Param("board_id") String board_id);
	
	
}
