package com.example.board.service;

import com.example.board.model.BoardVo;

import java.util.List;

public interface BoardService {

    //게시판 등록
    void registerBoard(BoardVo board);

    //게시판 상세 조회
    BoardVo retrieveBoard(int boardNo);

    // 게시판 전체조회
    List<BoardVo> retrieveBoardList();

    // 유형별 게시판 목록 조회
    List<BoardVo> retrieveBoardListByType(String type);

    //페이징을 위한 게시판 전체조회
    List<BoardVo> retrievePageBoardList(BoardVo params);

    // 게시판 그룹별 게시판 개수 조회
    int retrieveBoardGroupCount(int groupNo);

    //게시판 게시글 갯수 수정
    int reviseBoardPost(int boardNo, int postCount);

    //게시판 타입 조회
    BoardVo retrieveBoardType(int boardNo);

    //게시판 수정
    void reviseBoard(BoardVo board);

    //게시판 삭제
    void removeBoard(int boardNo);


}
