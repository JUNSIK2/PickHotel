package com.example.board.controller;


import com.example.alarm.AlarmMsg;
import com.example.alarm.model.AlarmVo;
import com.example.alarm.service.AlarmService;
import com.example.board.model.CommentVo;
import com.example.board.model.PostVo;
import com.example.board.service.CommentService;
import com.example.board.service.PostService;
import com.example.member.model.MemberVo;
import com.example.member.model.UserAccount;
import com.example.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j

public class CommentController {

    @Autowired
    public MemberService memberService;
    @Autowired

    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AlarmService alarmService;

    // ########## 댓글 ########## //
    // 댓글 등록 ajax (회원만 가능함)
    @ResponseBody
    @PostMapping("/comment/create")
    public AlarmVo create(CommentVo comment, @AuthenticationPrincipal UserAccount userAccount) {
        AlarmVo alarm = new AlarmVo();
        CommentVo comment1 = new CommentVo();

        // 작성자 본인이거나 관리자 인지 권한 확인
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();

        comment1.setMemNo(memNo);
        comment1.setPostNo(comment.getPostNo());
        comment1.setContent(comment.getContent().replaceAll("\r\n", "<br>"));
        comment1.setComClass(comment.getComClass());
        comment1.setParentMemNo(comment.getParentMemNo());

        //인덱스 번호 셋팅
        int max = this.commentService.retrieveCommentMax();
        comment1.setComNo(max + 1);

        //알람 기본 값 설정
        String alarmSend = "false";
        String alarmType = "";
        String alarmMsg = "";
        int alarmGetMemNo = 0;

        PostVo post = this.postService.retrieveDetailBoard(comment.getPostNo());

        //댓글인 경우
        if (comment.getComClass() == 0) {
            //댓글 그룹내 0순위를 가진다. 프론트에서 0을 보내옴
            comment1.setOrder(comment.getOrder());
            //댓글 그룹는 인덱스번호 와 동일하다
            comment1.setParents(max + 1);

            //본인 글에 댓글 단 경우 알림을 발송하지 않는다.
            if (memNo != post.getWriterNo()) {
                alarmSend = "true";
                alarmType = "1";
                alarmMsg = AlarmMsg.COMMENT_DEP1.getValue();
                alarmGetMemNo = this.postService.retrieveDetailBoard(comment.getPostNo()).getWriterNo();
            }
        } else { //댓글에 댓글을 달 경우

            //댓글 그룹내 마지막 순서를 가져온다
            int order = this.commentService.retrieveCommentOrder(comment.getParents());
            comment1.setOrder(order + 1);

            //댓글 그룹은 댓글의 pk값을 받아온다.
            comment1.setParents(comment.getParents());

            //답글 타입 공통
            alarmType = "2";
            if (comment.getParentMemNo() != 0) { //재댓글에 멘션한 경우
                if (memNo != comment.getParentMemNo()) { //멘션한 게시글 작성자가 본인인 경우 알림 발송x
                    alarmSend = "true";
                    alarmMsg = AlarmMsg.COMMENT_DEP3.getValue();
                    alarmGetMemNo = comment.getParentMemNo();
                }
            } else { //재댓글을 단 경우
                CommentVo parentsComment = this.commentService.retrieveComment(comment.getParents());
                if (memNo != parentsComment.getMemNo()) { //본인 댓글에 글을 남기는 게 아닌 경우
                    alarmSend = "true";
                    alarmMsg = AlarmMsg.COMMENT_DEP2.getValue();
                    alarmGetMemNo = parentsComment.getMemNo();
                }
            }
        }

        //댓글 등록 실행
        this.commentService.registerComment(comment1);

        //회원 댓글 갯수 추가
        this.memberService.reviseCommentCount(memNo, 1);

        // 알림 설정
        if (alarmSend == "true") {
            String alarmUrl = "/board/" + post.getBoardNo() + "/post/" + comment.getPostNo() + "#comment_" + comment1.getComNo();
            alarm.setType(alarmType);
            alarm.setContent(alarmMsg);
            alarm.setUrl(alarmUrl);
            alarm.setMemNo(alarmGetMemNo);
            alarm.setIsSend(alarmSend);
            this.alarmService.registerAlarm(alarm);
        }
        alarm.setResult("OK");

        return alarm;
    }

    // 댓글 목록 ajax
    @ResponseBody
    @PostMapping("/comment/list")
    public List<CommentVo> list(int postNo, @AuthenticationPrincipal UserAccount userAccount) {
        List<CommentVo> comments = this.commentService.retrieveCommentList(postNo);
        return comments;
    }

    // 댓글 수정 ajax (작성자만 가능함)
    @ResponseBody
    @PostMapping("/comment/update")
    public String update(CommentVo comment, @AuthenticationPrincipal UserAccount userAccount) {
        // 리턴값 셋팅
        String result;

        // 세션가져올 준비
        MemberVo member = userAccount.getMember();

        // 회원 id
        int memNo = member.getMemNo();
        int memberGrade = member.getGrade();
        // 작성된 댓글 작성자 id
        int writerNo = this.commentService.retrieveComment(comment.getComNo()).getMemNo();
        // 작성자 본인이나 관리자가 아닌 경우
        if (memNo != writerNo && memberGrade != 5) {
            result = "denine";
        } else { // 작성자 본인이거나 관리자인 경우
            this.commentService.reviseComment(comment);
            result = "OK";
        }
        return result;
    }

    // 댓글 삭제 ajax (작성자만 가능함)
    @ResponseBody
    @PostMapping("/comment/remove")
    public String remove(CommentVo comment, @AuthenticationPrincipal UserAccount userAccount) {
        // 리턴값 셋팅
        String result;
        // 세션가져올 준비
        MemberVo member = userAccount.getMember();

        // 회원 id
        int memNo = member.getMemNo();
        int memberGrade = member.getGrade();
        // 작성된 댓글 작성자 id
        int writerNo = this.commentService.retrieveComment(comment.getComNo()).getMemNo();
        // 작성자 본인이나 관리자가 아닌 경우
        if (memNo != writerNo && memberGrade != 5) {
            result = "denine";
        } else { // 작성자 본인이거나 관리자인 경우
            this.commentService.removeComment(comment);

            //회원 댓글 갯수 감소
            this.memberService.reviseCommentCount(memNo, -1);
            result = "OK";
        }
        return result;
    }
}
