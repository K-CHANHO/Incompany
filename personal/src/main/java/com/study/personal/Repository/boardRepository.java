package com.study.personal.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

	@Transactional
	@Modifying(flushAutomatically = true)
	@Query(value = "UPDATE Board b " +
					"SET content = :content " +
					"WHERE board_id = :board_id")
	void boardEdit(@Param("board_id") String board_id, @Param("content") String content);


	
	
}
