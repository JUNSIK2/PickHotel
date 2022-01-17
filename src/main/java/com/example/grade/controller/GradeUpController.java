package com.example.grade.controller;

import com.example.grade.model.GradeUpVo;
import com.example.grade.model.SiteGradeVo;
import com.example.grade.persistent.SiteGradeDao;
import com.example.grade.service.GradeUpService;
import com.example.member.model.MemberVo;
import com.example.member.model.UserAccount;
import com.example.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GradeUpController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private GradeUpService gradeUpService;

    @Autowired
    private SiteGradeDao siteGradeDao;

    // 등업신청목록조회(사용자)
    @GetMapping("/member/gradeup_list")
    public String list(@AuthenticationPrincipal UserAccount userAccount, Model model) {

        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();

        List<GradeUpVo> gradeUps = this.gradeUpService.selectGradeUp(memNo);

        if (gradeUps.isEmpty()) {
            model.addAttribute("GradeUps", "NoData");
        } else {
            model.addAttribute("GradeUps", gradeUps);
        }

        return "page/member_gradeup_list";
    }

    // 등업신청 가능여부 체크
    @ResponseBody
    @PostMapping("/member/gradeup_check")
    public int gradeup_checked(@AuthenticationPrincipal UserAccount userAccount) {

        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();

        int yetOk = gradeUpService.checkedGradeUp(memNo);
        // 1. 신청자가 대기중인 등업신청건이 있는지 확인
        if (yetOk == 0)
            return 0;
        else
            return 1;
    }

    // 등업신청 폼 창
    @GetMapping("/member/gradeup_write")
    public String gradeup_write(@AuthenticationPrincipal UserAccount userAccount, Model model) {
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();

        // 2.가능한 등급 목록 정보 받아올것
        List<SiteGradeVo> possibleGrades = this.siteGradeDao.selectPossibleGrade(memNo);
        if (possibleGrades.isEmpty()) {
            model.addAttribute("possibleGrades", "NoData");
        } else {
            model.addAttribute("possibleGrades", possibleGrades);
        }

        // 3. 현재 회원의 정보를 받아와야함
        MemberVo memberDB = memberService.retrieveMember(memNo);
        int boardCount = memberDB.getBoardCount();
        int commentCount = memberDB.getCommentCount();
        int visitCount = memberDB.getVisitCount();
        String nick = memberDB.getNick();
        int grade = memberDB.getGrade();
        String joinDate = memberDB.getJoinDate();

        GradeUpVo gradeUp = new GradeUpVo();
        gradeUp.setNick(nick);
        gradeUp.setMemNo(memNo);
        gradeUp.setBeforegrade(grade);
        gradeUp.setBoardCount(boardCount);
        gradeUp.setCommentCount(commentCount);
        gradeUp.setVisitCount(visitCount);
        gradeUp.setJoinDate(joinDate);

        model.addAttribute("gradeUp", gradeUp);

        if (gradeUpService.checkedGradeUp(memNo) != 0) {
            return "redirect:/";
        } else {
            return "page/member_gradeup_write";
        }
    }

    // 등업신청 작동 (등업 신청 버튼 누르면 실행함)
    @ResponseBody
    @PostMapping("/member/gradeup")
    public String gradeup(@ModelAttribute("GradeUpVo") GradeUpVo gradeUp, @AuthenticationPrincipal UserAccount userAccount) {

        // 세션 가져오기
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();
        int beforegrade = member.getGrade();

        //회원이 신청한 등급
        int aftergrade = gradeUp.getAftergrade();

        // 신청 가능한 등급인지 확인 할 것
        int result = 0;
        List<SiteGradeVo> possibleGrades = this.siteGradeDao.selectPossibleGrade(memNo);
        for (SiteGradeVo possibleGrade : possibleGrades) {
            int grade = possibleGrade.getMemGrade();
            if (grade == aftergrade) {
                result++;
            }
        }
        //신청한 내용과 db 일치여부
        if (result > 0) {
            gradeUp.setMemNo(memNo);
            gradeUp.setBeforegrade(beforegrade);
            gradeUpService.registerGradeUp(gradeUp);
            return "success";
        } else {
            return "false";
        }
    }

    // 등업신청 삭제
    @ResponseBody
    @GetMapping("/member/gradeup_delete/{gradeno}")
    public String gradeupdelete(@PathVariable("gradeno") int gradeno, @AuthenticationPrincipal UserAccount userAccount) {
        // 세션 가져오기
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();

        int gradeupstate = gradeUpService.checkedGradeUp(memNo);
        // 1. 신청자가 대기중인 등업신청건이 있는지 확인
        if (gradeupstate == 1) {
            gradeUpService.deleteGradeUp(gradeno);
            return "success";
        } else {
            return "false";
        }

    }

}