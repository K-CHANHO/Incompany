package com.study.personal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.personal.Entity.Board;

@Repository
public interface boardRepository extends JpaRepository<Board, String>{
	
	
}
