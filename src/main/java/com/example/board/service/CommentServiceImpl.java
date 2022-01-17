package com.example.board.service;

import com.example.board.model.CommentVo;
import com.example.board.model.PostUpdateCommentVo;
import com.example.board.persistent.CommentDao;
import com.example.board.persistent.PostDao;
import com.example.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PostDao postDao;

    @Autowired
    private CommentDao commentDao;

    //	댓글 추가
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void registerComment(CommentVo comment) {
        //댓글 추가
        this.commentDao.insertComment(comment);

        //게시글 댓글 개수 추가
        PostUpdateCommentVo updateComment = new PostUpdateCommentVo(comment.getPostNo(), 1);
        this.postDao.upCommentcount(updateComment);
    }

    //	게시글 댓글 목록 조회
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<CommentVo> retrieveCommentList(int postNo) {
        List<CommentVo> comments = new ArrayList<CommentVo>();
        comments = this.commentDao.selectCommentList(postNo);
        for (CommentVo comment : comments) {
            //개행처리
            comment.setContent(comment.getContent().replaceAll("<br>", "\r\n"));

            // DB에서 대댓글의 댓글인 경우 대댓글 작성자의 닉네임 가져오기
            int parentMemNo = comment.getParentMemNo();
            if (parentMemNo > 0) {
                String parentMemNick = memberService.retrieveMember(parentMemNo).getNick();
                comment.setParentMemNick(parentMemNick);
            }
        }
        return comments;
    }

    //	게시글 댓글 조회
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public CommentVo retrieveComment(int comNo) {
        CommentVo comment = new CommentVo();
        comment = this.commentDao.selectComment(comNo);
        return comment;
    }

    // 마지막 댓글 pk조회
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public int retrieveCommentMax() {
        int max = this.commentDao.selectCommentMax();
        return max;
    }

    // 댓글 그룹내 순서 조회
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public int retrieveCommentOrder(int parents) {
        int order = this.commentDao.selectCommentOrder(parents);
        return order;
    }

    //	댓글 내용 수정
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void reviseComment(CommentVo comment) {
        this.commentDao.updateComment(comment);
    }

    //	댓글삭제
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void removeComment(CommentVo comment) {
        //댓글 삭제 처리
        this.commentDao.deleteComment(comment.getComNo());

        //게시글 댓글 개수 감소
        System.out.println("간다");
        PostUpdateCommentVo updateComment = new PostUpdateCommentVo(comment.getPostNo(), -1);
        this.postDao.upCommentcount(updateComment);
    }
}
