package com.example.board.controller;

import com.example.board.model.CommentVo;
import com.example.board.service.CommentServiceImpl;
import com.example.member.model.MemberVo;
import com.example.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    public MemberService memberService;
    @Autowired
    private CommentServiceImpl commentService;

    // ########## 댓글 ########## //
    // 댓글 등록 ajax (회원만 가능함)
    @ResponseBody
    @PostMapping("/comment/create")
    public String create(CommentVo comment, HttpServletRequest request) {
        String result = "false";
        CommentVo comment1 = new CommentVo();
        HttpSession session = request.getSession();

        MemberVo member = (MemberVo) session.getAttribute("member");

        // 회원이 아닌 경우 작성이 제한됨
        if (member == null) {
            result = "isNotMember";
        } else {
            int memNo = member.getMemNo();
            comment1.setMemNo(memNo);
            comment1.setPostNo(comment.getPostNo());
            comment1.setContent(comment.getContent().replaceAll("\r\n", "<br>"));
            comment1.setComClass(comment.getComClass());
            comment1.setParentMemNo(comment.getParentMemNo());
            //인덱스 번호 셋팅
            int max = this.commentService.retrieveCommentMax();
            comment1.setComNo(max + 1);

            //댓글인 경우
            if (comment.getComClass() == 0) {
                //댓글 그룹내 0순위를 가진다. 프론트에서 0을 보내옴
                comment1.setOrder(comment.getOrder());
                //댓글 그룹는 인덱스번호 와 동일하다
                comment1.setParents(max + 1);
            } else { //답글인 경우
                //댓글 그룹내 마지막 순서를 가져온다
                int order = this.commentService.retrieveCommentOrder(comment.getParents());
                comment1.setOrder(order + 1);

                //댓글 그룹은 댓글의 pk값을 받아온다.
                comment1.setParents(comment.getParents());
            }
            //실행
            this.commentService.registerComment(comment1);
            //성공했을 경우 ok 리턴
            result = "OK";
        }
        return result;
    }

    // 댓글 목록 ajax
    @ResponseBody
    @PostMapping("/comment/list")
    public List<CommentVo> list(int postNo) {
        List<CommentVo> comments = this.commentService.retrieveCommentList(postNo);

        return comments;
    }

    // 댓글 수정 ajax (작성자만 가능함)
    @ResponseBody
    @PostMapping("/comment/update")
    public String update(CommentVo comment, HttpServletRequest request) {
        // 리턴값 셋팅
        String result = "false";
        // 세션가져올 준비
        HttpSession session = request.getSession();

        MemberVo member = (MemberVo) session.getAttribute("member");
        // 회원이 아닌 경우 작성이 제한됨
        if (member == null) {
            result = "isNotMember";
        } else {
            // 회원 id
            int memNo = member.getMemNo();
            // 작성된 댓글 작성자 id
            int orComNothis = this.commentService.retrieveComment(comment.getComNo()).getMemNo();
            // 작성자 본인이 아닌 경우
            if (memNo != orComNothis) {
                result = "denine";
            } else { // 작성자 본인인 경우

                this.commentService.reviseComment(comment);
                result = "OK";
            }
        }
        return result;
    }

    // 댓글 삭제 ajax (작성자만 가능함)
    @ResponseBody
    @PostMapping("/comment/remove")
    public String remove(CommentVo comment, HttpServletRequest request) {
        // 리턴값 셋팅
        String result = "false";
        // 세션가져올 준비
        HttpSession session = request.getSession();

        MemberVo member = (MemberVo) session.getAttribute("member");
        // 회원이 아닌 경우 작성이 제한됨
        if (member == null) {
            result = "isNotMember";
        } else {
            // 회원 id
            int memNo = member.getMemNo();

            // 작성된 댓글 작성자 id
            int writemMemNo = this.commentService.retrieveComment(comment.getComNo()).getMemNo();

            // 작성자 본인이 아닌 경우
            if (memNo != writemMemNo) {
                result = "denine";
            } else {
                // 작성자 본인인 경우
                this.commentService.removeComment(comment);
                result = "OK";
            }
        }
        return result;
    }

}
