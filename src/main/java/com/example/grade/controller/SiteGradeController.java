package com.example.grade.controller;

import com.example.grade.model.SiteGradeVo;
import com.example.grade.service.SiteGradeService;
import com.example.member.model.MemberVo;
import com.example.member.model.UserAccount;
import com.example.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SiteGradeController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private SiteGradeService siteGradeService;

    // 사이트 등급조회(맴버등급관리 누르면 실행함)
    @GetMapping("/intranet/grade/list")
    public String list(Model model) {
        List<SiteGradeVo> siteGrades = this.siteGradeService.retriveSiteGradeList();

        model.addAttribute("SiteGrades", siteGrades);
        return "page/intranet/sitegrade_list";
    }

    //사용자 - 사이트 등급 안내
    @GetMapping("/member/site_grade")
    public String memberModity(@AuthenticationPrincipal UserAccount userAccount, HttpServletRequest req, Model model) {
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();

        //접근하려던 게시글의 접근 등급명 가져오기
        model.addAttribute("boardGradeName", req.getAttribute("boardGradeName"));

        // DB에서 회원정보 상세 가져오기
        MemberVo memberDB = memberService.retrieveMember(memNo);
        //회원의 등급명 가져오기
        String gradeName = siteGradeService.retriveSiteGrade(memberDB.getGrade()).getMemGradeName();
        memberDB.setGradeName(gradeName);
        model.addAttribute("member", memberDB);

        // DB에서 사이트 등급 설정 가져오기
        List<SiteGradeVo> siteGrades = this.siteGradeService.retriveSiteGradeToUser();
        model.addAttribute("siteGrades", siteGrades);

        return "page/sitegrade_list";
    }

//    // 사이트 등급수정 다른 방법
//    @PostMapping("/intranet/grade/update")
//    public String SiteGradeModify(HttpServletRequest request) {
//
//        String[] memGrades = request.getParameterValues("memGrade");
//        String[] memGradeNames = request.getParameterValues("memGradeName");
//        String[] memGradeUses = request.getParameterValues("memGradeUse");
//        String[] memGradeBoards = request.getParameterValues("memGradeBoard");
//        String[] memGradeComments = request.getParameterValues("memGradeComment");
//        String[] memGradeVisits = request.getParameterValues("memGradeVisit");
//        String[] memGradeTypes = request.getParameterValues("memGradeType");
//
//
//        List<SiteGradeVo> SiteGrades = new ArrayList<SiteGradeVo>();
//
//        for (int i = 0; i < memGradeNames.length ; i++) {
//            SiteGradeVo siteGrade = new SiteGradeVo();
//            siteGrade.setMemGrade(Integer.parseInt(memGrades[i]));
//            siteGrade.setMemGradeName((memGradeNames[i]);
//            if (memGradeTypes[i] == null) {
//                siteGrade.setMemGradeType(0);
//
//            } else {
//                siteGrade.setMemGradeType(Integer.parseInt(memGradeTypes[i]);
//            }
//
//            if (memGradeUses[i] == null) {
//                siteGrade.setMemGradeUse(0);
//                siteGrade.setMemGradeUse(Integer.parseInt(memGradeUses[i]);
//            }
//            if (memGradeBoards[i] == null) {
//                siteGrade.setMemGradeBoards(0);
//                siteGrade.setMemGradeBoards(Integer.parseInt(setMemGradeBoards[i]));
//            }
//            if (memGradeComments[i] == null) {
//                siteGrade.memGradeComments(Integer.parseInt(memGradeComments[i]));
//            }
//            if (memGradeVisits[i] == null) {
//                siteGrade.setMemGradeType(Integer.parseInt(memGradeVisits[i]));
//            }
//            siteGrade.setMemGradeUse(Integer.parseInt(memGradeUses[i]));
//            siteGrade.setMemGradeBoard(Integer.parseInt(memGradeBoards[i]));
//            siteGrade.setMemGradeComment(Integer.parseInt(memGradeComments[i]));
//            siteGrade.setMemGradeVisit(Integer.parseInt(memGradeVisits[i]));
//            this.siteGradeService.siteGradeModify(siteGrade);
//        }
//    }


    // 사이트 등급수정
    @PostMapping("/intranet/grade/update")
    public String SiteGradeModify(@RequestParam(value = "memGrade[]") int[] memGrades, @RequestParam(value = "memGradeName[]") String[] memGradeNames, @RequestParam(value = "memGradeType[]") int[] memGradeTypes,
                                  @RequestParam(value = "memGradeUse[]") int[] memGradeUses, @RequestParam(value = "memGradeBoard[]") int[] memGradeBoards,
                                  @RequestParam(value = "memGradeComment[]") int[] memGradeComments, @RequestParam(value = "memGradeVisit[]") int[] memGradeVisits,
                                  @RequestParam(value = "memGrade1") Integer memGrade1, @RequestParam(value = "memGrade5") Integer memGrade5,
                                  @RequestParam(value = "memGradeName1") String memGradeName1, @RequestParam(value = "memGradeName5") String memGradeName5) {

        List<SiteGradeVo> SiteGrades = new ArrayList<SiteGradeVo>();


        if (memGrade1 != null) {  //새싹 멤버
            SiteGradeVo siteGrade = new SiteGradeVo();
            siteGrade.setMemGrade(1);
            siteGrade.setMemGradeName(memGradeName1);
            siteGrade.setMemGradeType(0);
            siteGrade.setMemGradeUse(0);
            siteGrade.setMemGradeBoard(0);
            siteGrade.setMemGradeComment(0);
            siteGrade.setMemGradeVisit(0);
        }

        if (memGrade5 != null) { //최고 관리자
            SiteGradeVo siteGrade = new SiteGradeVo();
            siteGrade.setMemGrade(5);
            siteGrade.setMemGradeName(memGradeName5);
            siteGrade.setMemGradeType(0);
            siteGrade.setMemGradeUse(0);
            siteGrade.setMemGradeBoard(0);
            siteGrade.setMemGradeComment(0);
            siteGrade.setMemGradeVisit(0);
        }

        for (int i = 0; i < 3; i++) {
            SiteGradeVo siteGrade = new SiteGradeVo();
            siteGrade.setMemGrade(memGrades[i]);
            siteGrade.setMemGradeName(memGradeNames[i]);
            siteGrade.setMemGradeType(memGradeTypes[i]);
            siteGrade.setMemGradeUse(memGradeUses[i]);
            siteGrade.setMemGradeBoard(memGradeBoards[i]);
            siteGrade.setMemGradeComment(memGradeComments[i]);
            siteGrade.setMemGradeVisit(memGradeVisits[i]);

            this.siteGradeService.siteGradeModify(siteGrade);
        }
        return "redirect:/intranet/grade/list";
    }
}