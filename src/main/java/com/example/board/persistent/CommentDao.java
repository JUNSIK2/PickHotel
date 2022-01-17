package com.example.board.persistent;

import com.example.board.model.CommentVo;

import java.util.List;

public interface CommentDao {

    // 댓글 추가
    void insertComment(CommentVo comment);

    // 게시글 댓글 목록 조회
    List<CommentVo> selectCommentList(int postNo);

    // 댓글 조회
    CommentVo selectComment(int comNo);

    // 마지막 댓글 pk조회
    int selectCommentMax();

    // 댓글 그룹내 순서 조회
    int selectCommentOrder(int parents);

    // 댓글 내용 수정
    void updateComment(CommentVo comment);

    // 댓글 블라인드 처리
    void blindComment(int comNo, int isblind);

    // 댓글삭제
    void deleteComment(int comNo);
}
