package com.example.board.service;

import com.example.board.model.BoardGroupVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardGroupService {


    //게시판 그룹 등록
    void registerBoardGroup(BoardGroupVo boardGroup);

    //게시판 그룹 전체 목록조회
    List<BoardGroupVo> retrieveBoardGroupList();

    //게시판 그룹 상세조회
    BoardGroupVo retrieveBoardGroup(int groupNo);

    //게시판 그룹별 게시판 개수 조회
    int retrieveBoardGroupCount(int groupNo);

    //페이징을 위한 게시판 그룹 전체조회
    List<BoardGroupVo> retrievePageBoardGroupList(BoardGroupVo params);

    //게시판 그룹 수정
    void reviseBoardGroup(BoardGroupVo boardGroup);

    //게시판 그룹 삭제
    BoardGroupVo removeBoardGroup(int groupNo);
}
