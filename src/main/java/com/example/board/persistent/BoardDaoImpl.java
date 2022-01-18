package com.example.board.persistent;

import com.example.board.model.BoardVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("boardDao")
public class BoardDaoImpl implements BoardDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    //게시판 등록
    @Override
    public void insertBoard(BoardVo board) {
        this.sqlSessionTemplate.insert("BoardDao.insertBoard", board);
    }


    // 게시판 상세조회
    @Override
    public BoardVo selectBoard(int boardNo) {
        return this.sqlSessionTemplate.selectOne("BoardDao.selectBoard", boardNo);
    }

    //게시판 목록 조회
    @Override
    public List<BoardVo> selectBoardList() {
        return this.sqlSessionTemplate.selectList("BoardDao.selectBoardList");
    }

    // 유형별 게시판 목록 조회
    @Override
    public List<BoardVo> selectBoardListByType(String type) {
        return this.sqlSessionTemplate.selectList("BoardDao.selectBoardListByType", type);
    }

    //게시판 총 개수
    @Override
    public int selectBoardTotalCount(int params) {
        return this.sqlSessionTemplate.selectOne("BoardDao.selectBoardTotalCount", params);
    }

    //게시판 목록 조회 - page
    @Override
    public List<BoardVo> selectPageBoardList(BoardVo params) {
        return this.sqlSessionTemplate.selectList("BoardDao.selectPageBoardList", params);
    }

    //게시판 총 개수-page
    @Override
    public int selectPageBoardTotalCount(BoardVo params) {
        return this.sqlSessionTemplate.selectOne("BoardDao.selectPageBoardCount", params);
    }

    //게시판 타입 조회
    public BoardVo selectBoardType(int boardNo) {
        return this.sqlSessionTemplate.selectOne("BoardDao.selectBoardType", boardNo);
    }

    //게시판 수정
    @Override
    public void updateBoard(BoardVo board) {
        this.sqlSessionTemplate.update("BoardDao.updateBoard", board);

    }

    //게시판 게시글 수 수정(증가 감소)
    @Override
    public int updateBoardPost(int boardNo, int updatepostCount) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("boardNo", boardNo);
        paramMap.put("updatepostCount", updatepostCount);

        sqlSessionTemplate.update("BoardDao.updateBoardPost", paramMap);

        return boardNo;
    }

    //게시판 삭제
    @Override
    public void removeBoard(int boardNo) {
        this.sqlSessionTemplate.delete("BoardDao.deleteBoard", boardNo);
    }

}
