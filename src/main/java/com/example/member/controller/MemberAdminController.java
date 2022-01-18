package com.example.member.controller;

import com.example.member.model.MemberAndMemberStateVo;
import com.example.member.model.MemberJoinVo;
import com.example.member.model.MemberStateVo;
import com.example.member.model.MemberVo;
import com.example.member.service.MemberService;
import com.example.member.service.MemberStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberAdminController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberStateService memberStateService;

    // 회원 목록
    @GetMapping(value = {"/intranet", "/intranet/index", "/intranet/member/list"})
    public String memberList(@ModelAttribute("params") MemberVo params, Model model) {
        List<MemberVo> members = memberService.retrieveMemberList(params);
        model.addAttribute("members", members);

        return "page/intranet/member_list";
    }

    // 회원 상세보기
    @GetMapping("/intranet/member/{memNo}")
    public String memberDetail(@PathVariable("memNo") int memNo, Model model) {
        MemberVo member = memberService.retrieveMember(memNo);
        model.addAttribute("member", member);

        return "page/intranet/member_modify";
    }

    // 회원정보 수정하기
    @PostMapping("/intranet/member/modify")
    public String memberModify(@ModelAttribute("member") MemberJoinVo member) {
        memberService.reviseMember(member);

        return "redirect:/intranet/member/" + member.getMemNo();
    }

    // 회원정보 등급 수정하기
    @ResponseBody
    @PostMapping("/intranet/member/grade_modify")
    public List<MemberVo> memberGradeModify(@ModelAttribute("params") MemberVo params,
                                            @RequestParam("memNoList") List<Integer> ids, @RequestParam("grade") int grade) {

        for (int memNo : ids) {
            memberService.reviseMemberGrade(memNo, grade);
        }

        List<MemberVo> list = memberService.retrieveMemberList(params);
        return list;
    }

    // 회원 탈퇴(관리자)
//	@GetMapping("/intranet/member/withdarw")
//	public String memberWithdarw(HttpServletRequest req) {
//		HttpSession session = req.getSession();
//		MemberVo member = (MemberVo) session.getAttribute("member");
//		memberService.reviseMemberState(member.getMemNo(), "2");
//		return "redirect:/intranet";
//	}


    // ######## 회원 강퇴 ########
    // 강퇴 회원 목록
    @GetMapping("/intranet/member/block_list")
    public String memberBlockList(@ModelAttribute("params") MemberStateVo params, Model model) {
        List<MemberAndMemberStateVo> members = memberStateService.retrieveMemberBlockList(params);
        model.addAttribute("members", members);
        return "page/intranet/member_block_list";
    }

    // 회원 강퇴 사유 작성
    @RequestMapping(value = "/intranet/member/block_write", method = {RequestMethod.GET, RequestMethod.POST})
    public String memberBlockWrite() {

        return "page/intranet/member_block_write_ajax";
    }

    // 회원 강퇴
    @PostMapping("/intranet/member/block")
    public String memberBlock(@RequestParam("memNoList") List<Integer> ids, @RequestParam("reason") String reason,
                              Model model) {
        for (int memNo : ids) {
            // 회원 상태 "강제탈퇴" 변경
            memberService.reviseMemberState(memNo, "3");

            // 강퇴 정보 추가
            MemberStateVo memberState = new MemberStateVo(memNo, reason);
            memberStateService.registerMemberState(memberState);
        }

        return "redirect:/intranet/member/block_list";
    }

    // 회원 강퇴 취소
    @ResponseBody
    @PostMapping("/intranet/member/block_cancel")
    public List<MemberAndMemberStateVo> memberBlockCancel(@ModelAttribute("params") MemberStateVo params,
                                                          @RequestParam("stateNoList") List<Integer> stateNoList, Model model) {
        int memNo = 0;
        for (int stateNo : stateNoList) {

            // 강제 탈퇴 건 PK값으로 회원PK 조회
            memNo = memberStateService.retrieveMemberBlock(stateNo).getMemNo();

            // 회원 상태 "탈퇴" 변경
            memberService.reviseMemberState(memNo, "1");
        }

        List<MemberAndMemberStateVo> list = memberStateService.retrieveMemberBlockList(params);
        return list;
    }
}
