package com.example.board.persistent;

import com.example.board.model.BoardGroupVo;
import com.example.board.model.BoardVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("boardGroupDao")
public class BoardGroupDaoImpl implements BoardGroupDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    //게시판 그룹 등록
    @Override
    public void insertBoardGroup(BoardGroupVo boardGroup){
        this.sqlSessionTemplate.insert("BoardGroupDao.insertBoardGroup", boardGroup);
    }

    //게시판 그룹 전체 목록 조회
    @Override
    public List<BoardGroupVo> selectBoardGroupList(){
        return this.sqlSessionTemplate.selectList("BoardGroupDao.selectBoardGroupList");
    }

    // 게시판 그룹 상세조회
    @Override
    public BoardGroupVo selectBoardGroup(int groupNo){
        System.out.println("selectBoardGroup 실행");
        return this.sqlSessionTemplate.selectOne("BoardGroupDao.selectBoardGroup", groupNo);
    }

    //게시판 그룹별 게시판 개수 조회
    @Override
    public int selectBoardGroupCount(int groupNo){
        return this.sqlSessionTemplate.selectOne("BoardGroupDao.selectBoardGroupCount", groupNo);
    }

    //게시판 그룹 총 개수-page
    @Override
    public int selectPageBoardGroupTotalCount(BoardGroupVo params){
        return this.sqlSessionTemplate.selectOne("BoardGroupDao.selectPageBoardGroupCount", params);
    }

    //게시판 그룹 목록 조회 - page
    @Override
    public List<BoardGroupVo> selectPageBoardGroupList(BoardGroupVo params){
        return this.sqlSessionTemplate.selectList("BoardGroupDao.selectPageBoardGroupList", params);
    }

    //게시판 그룹 수정
    @Override
    public void updateBoardGroup(BoardGroupVo boardGroup){
        this.sqlSessionTemplate.update("BoardGroupDao.updateBoardGroup", boardGroup);
    }

    //게시판 삭제
    @Override
    public BoardGroupVo removeBoardGroup(int groupNo){
        this.sqlSessionTemplate.delete("BoardGroupDao.deleteBoardGroup", groupNo);
        return null;
    }

}
