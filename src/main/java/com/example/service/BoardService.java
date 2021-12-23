package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.vo.BoardVo;


@Service
public interface BoardService {
	
	//게시판 등록
	void insertBoard(BoardVo board);
	
	//게시판 수정
	public void updateBoard(BoardVo board);
	
	//게시판 삭제
	void deleteBoard(int boardNo);
	
	//게시판 상세 조회
	public BoardVo selectBoard(int boardNo);
	
	//게시판 수 조회
	public int selectBoardTotalCount();
	
	//게시판 전체조회
	public List<BoardVo> retrieveBoardList();
	
	

}
