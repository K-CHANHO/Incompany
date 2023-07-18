package com.study.personal.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {
	
	@Id
	private String id;
	
	@Column(nullable = false)
	private String userid;
	
	@Column(nullable = false)
	private String password;
	
}
