package com.study.personal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.study.personal.Entity.Member;

@Repository
public interface memberRepository extends JpaRepository<Member, String>{
	
	@Query("SELECT m " +
			"FROM Member m " +
			"WHERE 1=1 " +
			"AND m.userid = :userid " +
			"AND m.password = :password "
			)
	Member isCorrect(@Param("userid") String userid, @Param("password") String password);
	
}
