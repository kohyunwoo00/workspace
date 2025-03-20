package com.kh.spring.board.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;

public interface BoardService {
	// 게시글 작성(파일첨부)
	void insertBoard(BoardDTO board, MultipartFile file); 
	/*
	 * insertBoard();
	 * save();
	 */
	// 게시글 목록조회
	List<BoardDTO> selectBoardList(int currentPage);
	/*
	 * selectBoardList();
	 * selectAll();
	 * findAll();
	 */
	
	// 게시글 상세보기(댓글도 같이 조회) --> 새로운 기능 사용
	BoardDTO selectBoard(int boardNo);
		
	// 게시글 수정
	BoardDTO updateBoard(BoardDTO board, MultipartFile file);
	
	// 게시글 삭제(딜리트인척하고 업데이트 할 것 STATUS컬럼값 N으로 바꿀것)
	void deleteBoard(int boardNo);
	
	// -------------- 1절
	
	// 게시글 검색 기능
}
