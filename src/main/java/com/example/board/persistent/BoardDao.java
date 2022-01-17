package com.example.board.persistent;

import com.example.board.model.BoardVo;

import java.util.List;

public interface BoardDao {
    // 게시판 등록
    void insertBoard(BoardVo Board);

    // 게시판 상세조회
    BoardVo selectBoard(int boardNo);

    // 유형별 게시판 목록 조회
    List<BoardVo> selectBoardListByType(String type);

    //게시판 전체 목록 조회
    List<BoardVo> selectBoardList();

    // 게시판 총 개수
    int selectBoardTotalCount(int params);

    //게시판 목록 조회 - page
    List<BoardVo> selectPageBoardList(BoardVo params);

    //게시판 총 개수-page
    int selectPageBoardTotalCount(BoardVo params);

    //게시판 타입 조회
    BoardVo selectBoardType(int boardNo);

    // 게시판 수정
    void updateBoard(BoardVo Board);

    //게시판 게시글 수 수정(증가 감소)
    int updateBoardPost(int boardNo, int updatepostCount);

    //게시판 삭제
    void removeBoard(int boardNo);

}
