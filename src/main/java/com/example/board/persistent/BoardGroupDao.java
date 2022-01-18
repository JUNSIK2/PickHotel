package com.example.board.persistent;

import com.example.board.model.BoardGroupVo;
import com.example.board.model.BoardVo;

import java.util.List;

public interface BoardGroupDao {

    //게시판 그룹 등록
    void insertBoardGroup(BoardGroupVo boardGroup);

    //게시판 그룹 전체 목록 조회
    List<BoardGroupVo> selectBoardGroupList();

    //게시판 그룹 상세조회
    BoardGroupVo selectBoardGroup(int groupNo);

    //게시판 그룹별 게시판 개수 조회
    int selectBoardGroupCount(int groupNo);

    //게시판 그룹 총 개수-page
    int selectPageBoardGroupTotalCount(BoardGroupVo params);

    //게시판 그룹 목록 조회 - page
    List<BoardGroupVo> selectPageBoardGroupList(BoardGroupVo params);

    //게시판 그룹 수정
    void updateBoardGroup(BoardGroupVo boardGroup);

    //게시판 삭제
    BoardGroupVo removeBoardGroup(int groupNo);

}
