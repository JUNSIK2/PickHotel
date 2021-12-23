package com.example.dao.board;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.vo.CommentVo;

@Repository("commentDao")
public class CommentDao {
	@Autowired
	private SqlSession sqlSession;

	// 댓글삭제
	public void deleteComment(int comNo) {
		this.sqlSession.delete("CommentDao.deleteComment", comNo);
	}

	// 댓글 내용 수정
	public void updateComment(CommentVo comment) {
		this.sqlSession.update("CommentDao.updateComment", comment);
	}

	// 게시글 댓글 목록 조회
	public List<CommentVo> selectCommentList(int postNo) {
		List<CommentVo> commentList = new ArrayList<CommentVo>();
		commentList = this.sqlSession.selectList("CommentDao.selectCommentList", postNo);
		return commentList;
	}

	// 댓글 목록 조회
	public CommentVo selectComment(int comNo) {
		CommentVo comment = new CommentVo();
		comment = this.sqlSession.selectOne("CommentDao.selectComment", comNo);
		return comment;
	}

	// 마지막 댓글 pk조회
	public int selectCommentMax() {
		int max = this.sqlSession.selectOne("CommentDao.selectCommentMax");
		return max;
	}

	// 댓글 그룹내 순서 조회
	public int selectCommentOrder(int parents) {
		int order = this.sqlSession.selectOne("CommentDao.selectCommentOrder", parents);
		return order;
	}

	// 댓글 추가
	public void insertComment(CommentVo comment) {
		this.sqlSession.insert("CommentDao.insertComment", comment);

	}

}
