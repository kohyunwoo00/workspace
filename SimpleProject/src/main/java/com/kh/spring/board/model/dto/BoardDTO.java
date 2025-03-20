package com.kh.spring.board.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// ValueObject == 불변성
// DTO는 setter 때문에 불변성일 수가 없다
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class BoardDTO { // 데이터 전송 목적 페이지
	private int boardNo;
	private String boardTitle;
	private String boardCountent;
	private String boardWriter;
	private int count;
	private String createDate;
	private String changeName;
	private String status;
}
