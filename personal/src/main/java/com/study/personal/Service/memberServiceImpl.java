package com.study.personal.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.personal.DTO.memberDTO;
import com.study.personal.Entity.Member;
import com.study.personal.Repository.memberRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class memberServiceImpl implements memberService{
	
	@Autowired
	private memberRepository memberRepository;

	@Override
	public void join(memberDTO memberDTO) {
		
		Member member = memberDTOToEntity(memberDTO);
		memberRepository.save(member);
		
	}

	@Override
	public Member login(memberDTO memberDTO) {
		
		Member member = memberRepository.isCorrect(memberDTO.getUserid(), memberDTO.getPassword());
		
		return member;
	}
	
	

}
