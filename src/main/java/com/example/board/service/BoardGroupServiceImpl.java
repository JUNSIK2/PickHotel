package com.example.board.service;

import com.example.board.model.BoardGroupVo;
import com.example.board.persistent.BoardDao;
import com.example.board.persistent.BoardGroupDao;
import com.example.common.paging.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("boardGroupService")
public class BoardGroupServiceImpl implements BoardGroupService {

    @Autowired
    private BoardGroupDao boardGroupDao;

    @Autowired
    private BoardDao boardDao;

    //게시판 그룹 등록
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void registerBoardGroup(BoardGroupVo boardGroup) {
        this.boardGroupDao.insertBoardGroup(boardGroup);
    }

    //게시판 그룹 전체 목록조회
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<BoardGroupVo> retrieveBoardGroupList() {
        return this.boardGroupDao.selectBoardGroupList();
    }

    //게시판 그룹 상세조회
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public BoardGroupVo retrieveBoardGroup(int groupNo) {
        System.out.println("retrieveBoard 실행");
        return this.boardGroupDao.selectBoardGroup(groupNo);
    }

    //게시판 그룹별 게시판 개수 조회
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public int retrieveBoardGroupCount(int groupNo) {
        return this.boardGroupDao.selectBoardGroupCount(groupNo);
    }


    //페이징을 위한 게시판 그룹 전체조회
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<BoardGroupVo> retrievePageBoardGroupList(BoardGroupVo params) {
        List<BoardGroupVo> boardGroupList = Collections.emptyList();

        int boardGroupTotalCount = this.boardGroupDao.selectPageBoardGroupTotalCount(params);

        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(boardGroupTotalCount);
        params.setPaginationInfo(paginationInfo);

        if (boardGroupTotalCount > 0) {
            boardGroupList = this.boardGroupDao.selectPageBoardGroupList(params);
        }

        return boardGroupList;
    }


    //게시판 그룹 수정
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void reviseBoardGroup(BoardGroupVo boardGroup) {
        this.boardGroupDao.updateBoardGroup(boardGroup);
    }

    //게시판 그룹 삭제
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public BoardGroupVo removeBoardGroup(int groupNo) {
        return this.boardGroupDao.removeBoardGroup(groupNo);
    }
}
