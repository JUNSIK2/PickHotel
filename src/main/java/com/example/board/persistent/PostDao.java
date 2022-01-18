package com.example.board.persistent;

import com.example.board.model.BoardVo;
import com.example.board.model.PostUpdateCommentVo;
import com.example.board.model.PostVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("postDao")
public class PostDao {
    @Autowired
    private SqlSession sqlSession;

    //게시글 정보를 등록하다.
    public int insertPost(PostVo post) {
        this.sqlSession.insert("PostDao.insertPost", post);
        return this.sqlSession.selectOne("PostDao.selectLastInsertID"); //SELECT LAST_INSERT_ID() 처리 필요
    }

    // 전체 게시글 목록 조회
    public List<PostVo> selectPostByTag(String tag) {
        List<PostVo> lists = this.sqlSession.selectList("PostDao.selectPostByTag", tag);
        return lists;
    }

    public List<PostVo> selectPostList(PostVo params) {
        return this.sqlSession.selectList("PostDao.selectPostList", params);
    }

    public int selectPostCount(PostVo params) {
        return this.sqlSession.selectOne("PostDao.selectPostCount", params);
    }

    //총 게시글 수를 구한다.
    public int selectTotalPostCount(HashMap<String, String> map) {
        int count = 0;
        count = this.sqlSession.selectOne("PostDao.selectTotalPostCount", map);
        return count;
    }

    public List<BoardVo> selectAllBoards() {
        return this.sqlSession.selectList("PostDao.selectAllBoards");
    }

    // 게시글 존재 여부 조회
    public Integer selectPostSearch(PostVo post) {
        return this.sqlSession.selectOne("PostDao.selectPostSearch", post);
    }

    //게시글 번호에 해당하는 게시글 상세정보를 조회
    public PostVo selectDetailPost(int postNo) {
        return this.sqlSession.selectOne("PostDao.selectDetailPost", postNo);
    }

    //내가 작성한 게시글 조회
    public List<PostVo> selectMyPosts(int MemNo) {
        return this.sqlSession.selectList("PostDao.selectMyPosts", MemNo);
    }

    //조회수 증가
    public void upHitcount(int no) {
        this.sqlSession.update("PostDao.upHitcount", no);
    }

    //댓글수 증가/감소 -- 수정해야함
    public void upCommentcount(PostUpdateCommentVo updateComment) {
        this.sqlSession.update("PostDao.upCommentcount", updateComment);
    }

    //게시글 정보 변경
    public void updatePost(PostVo post) {
        this.sqlSession.update("PostDao.updatePost", post);
    }


    //게시글을 블라인드 처리한다.
    public void blindPost(int postNo, int isblind) {
        this.sqlSession.delete("PostDao.deletePost", postNo);
    }

    //게시글 정보를 삭제하다.
    public void deletePost(int postNo) {
        this.sqlSession.delete("PostDao.deletePost", postNo);
    }

}














