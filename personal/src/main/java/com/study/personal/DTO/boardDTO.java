package com.study.personal.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class boardDTO {
	
	private Long board_no;
	private String board_id;
	private String userid;
	private String content;
	
}
