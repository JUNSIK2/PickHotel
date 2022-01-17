package com.example.board.persistent;

import com.example.board.model.AttachVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("attachDao")
public class AttachDao {

    @Autowired
    private SqlSession sqlSession;

    //파일을 등록하다.
    public void insertPostAttach(AttachVo attach) {
        this.sqlSession.insert("AttachDao.insertPostAttach", attach);
    }

    //파일을 삭제한다.
    public void deletePostAttach(int attaNo) {
        this.sqlSession.delete("AttachDao.deletePostAttach", attaNo);
    }

    //게시글 삭제에 의해 파일을 삭제한다.
    public void deleteAttachbyPost(int poNo) {
        this.sqlSession.delete("AttachDao.deleteAttachbyPost", poNo);
    }

    //게시글 번호로 파일목록을 조회한다.
    public List<AttachVo> selectPostAttach(int poNo) {
        return this.sqlSession.selectList("AttachDao.selectPostAttach", poNo);
    }

    //파일을 조회한다.
    public AttachVo selectPostAttachByAtNo(int attaNo) {
        return this.sqlSession.selectOne("AttachDao.selectPostAttachByAtNo", attaNo);
    }
}














