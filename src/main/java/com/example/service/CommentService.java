package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.board.CommentDao;
import com.example.vo.CommentVo;

@Service("commentService")
public class CommentService {
	@Autowired
	private CommentDao commentDao;

//	댓글삭제
	public void removeComment(int comNo) {
		this.commentDao.deleteComment(comNo);
	}

//	댓글 내용 수정
	public void reviseComment(CommentVo comment) {
		this.commentDao.updateComment(comment);
	}

//	게시글 댓글 목록 조회
	public List<CommentVo> retrieveCommentList(int postNo) {
		List<CommentVo> commentList = new ArrayList<CommentVo>();
		commentList = this.commentDao.selectCommentList(postNo);
		return commentList;
	}

//	게시글 댓글 조회
	public CommentVo retrieveComment(int comNo) {
		CommentVo comment = new CommentVo();
		comment = this.commentDao.selectComment(comNo);
		return comment;
	}

	// 마지막 댓글 pk조회
	public int retrieveCommentMax() {
		int max = this.commentDao.selectCommentMax();
		return max;
	}

	// 댓글 그룹내 순서 조회
	public int retrieveCommentOrder(int parents) {
		int order = this.commentDao.selectCommentOrder(parents);
		return order;
	}

//	댓글 추가
	public void registerComment(CommentVo comment) {
		this.commentDao.insertComment(comment);
	}
}
