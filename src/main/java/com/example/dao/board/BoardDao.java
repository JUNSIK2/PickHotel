package com.example.dao.board;

import java.util.List;

import com.example.vo.BoardVo;

public interface BoardDao {
	// 게시판 등록
	void insertBoard(BoardVo Board);

	// 게시판 수정
	public void updateBoard(BoardVo Board);
	
	//게시판 삭제
	 void removeBoard(int boardno);

	// 게시판 전체조회
	public List<BoardVo> selectBoardList();

	// 게시판 수를 조회한다
	public int selectBoardTotalCount();

	// 게시판 상세조회
	public BoardVo selectBoard(int boardNo);
	
	
	
}
