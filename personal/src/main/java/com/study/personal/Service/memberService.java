package com.study.personal.Service;

import java.util.UUID;

import com.study.personal.DTO.memberDTO;
import com.study.personal.Entity.Member;

public interface memberService {
	
	void join(memberDTO memberDTO);
	
	Member login(memberDTO memberDTO);
	
	default Member memberDTOToEntity(memberDTO memberDTO) {
		
		Member member = Member.builder()
				.id(UUID.randomUUID().toString())
				.userid(memberDTO.getUserid())
				.password(memberDTO.getPassword())
				.build();
		
		return member;
	}

}
