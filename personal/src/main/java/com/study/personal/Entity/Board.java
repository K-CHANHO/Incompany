package com.study.personal.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long board_no;
	
	@Column(unique = true)
	private String board_id;
	
	@Column(nullable = false)
	private String userid;
	
	@Column(nullable = true)
	private String content;


}
