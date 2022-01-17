package com.example.member.controller;

import com.example.member.CustomMailSender;
import com.example.member.model.*;
import com.example.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    public CustomMailSender customMailSender;

    @Autowired
    public MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 아이디 찾기 폼
    @GetMapping("/findIdForm")
    public String memberFindIdForm() {
        return "page/member_findidform";
    }

    // 아이디 찾기 결과
    @PostMapping("/findId")
    public String memberFindId(@Valid MemberFindIDVo member, Model model) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", member.getName());
        map.put("birth", member.getBirth());
        System.out.println("ddrr" + member.getBirth());
        List<MemberVo> findIdResultList = memberService.retrieveMemberId(map);

        model.addAttribute("findIdResultList", findIdResultList);

        return "page/member_findid";
    }


    // 비밀번호 찾기 폼
    @GetMapping("/findPwdForm")
    public String memberFindPwForm() {
        return "page/member_findpwdform";
    }

    // 비밀번호 변경 폼
    @PostMapping("/alterPwdForm")
    public String memberAlterPwForm(@Valid MemberIdVo member, Model model) {
        model.addAttribute("id", member.getId());
        return "page/member_alterpwdform";
    }

    // 비밀번호 변경
    @PostMapping("/alterPwd")
    public String memberAlterPw(@Valid MemberIdPwVo member) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", member.getId());
        map.put("pwd", passwordEncoder.encode(member.getPwd()));
        this.memberService.modifyPw(map);

        return "page/index";
    }


    // 회원가입 폼
    @GetMapping("/join")
    public String memberJoinForm() {
        return "page/member_join";
    }

    //회원가입
    @PostMapping("/join")
    public String memberJoin(@Valid MemberJoinVo member) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", member.getId());
        map.put("nick", member.getNick());
        map.put("name", member.getName());
        map.put("pwd", passwordEncoder.encode(member.getPwd()));
        map.put("gender", member.getGender());
        map.put("hp", member.getHp());
        map.put("birth", member.getBirth());

        this.memberService.registerMember(map);
        this.memberService.registerRole(member.getId());

        return "redirect:/welcome";
    }

    // 회원 가입 환영 페이지
    @GetMapping("/welcome")
    public String memberWelcome() {
        return "page/member_welcome";
    }

    // 탈퇴회원 조회
    @GetMapping("/withdarwCheck")
    @ResponseBody
    public Map memberWithDarwCheck(@Valid MemberIdVo member, HttpServletResponse response) throws Exception {

        String state = this.memberService.checkWithDraw(member.getId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", state);

        return map;
    }

    // 인증메일 발송
    @GetMapping("/sendMail")
    @ResponseBody
    public Map memberMailSender(@Valid MemberIdVo member, HttpServletRequest req)
            throws MessagingException, IOException {

        int random = (int) (Math.random() * 9000) + 1000;
        String message = "인증번호를 입력해주세요. : " + random;

        customMailSender.sendMail(member.getId(), message);

        int count = 1;
        if (member.getId() != null) {
            count = 0;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sendmail", count);
        System.out.println("random :::::" + random);

        HttpSession session = req.getSession();
        session.setAttribute("random", random);
        return map;
    }

    // 인증번호 체크
    @GetMapping("/mailCheck")
    @ResponseBody
    public Map memberMailSender(HttpServletRequest req) throws MessagingException, IOException {

        HttpSession session = req.getSession();
        int random = (int) session.getAttribute("random");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("random", random);

        return map;
    }

    // 아이디 중복체크
    @GetMapping("/idCheck")
    @ResponseBody
    public Map memberIdCheck(@Valid MemberIdVo member, Model model) {

        String idCheck = this.memberService.retrieveIdCheck(member.getId());

        int count = 1;
        if (idCheck == null) {
            count = 0;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("overLapId", count);

        return map;

    }

    // 닉네임 중복체크
    @GetMapping("/nickCheck")
    @ResponseBody
    public Map memberNickCheck(@Valid MemberNickVo member, Model model) {

        String nickCheck = this.memberService.retrieveNickCheck(member.getNick());

        int count = 1;
        if (nickCheck == null) {
            count = 0;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("overLapNick", count);

        return map;

    }

    // 회원 탈퇴(사용자)
    @GetMapping("/member/withdarw")
    public String memberWithdarw() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();

        memberService.reviseMemberState(user.getMember().getMemNo(), "2");

        return "redirect:/logout";
    }

    // 회원 정보 상세조회 + 수정 폼
    @GetMapping("/member")
    public String memberDetail(@AuthenticationPrincipal UserAccount userAccount, Model model) {
        MemberVo member = userAccount.getMember();
        int memNo = member.getMemNo();

        // DB에서 회원정보 상세 가져오기
        MemberVo memberDB = memberService.retrieveMember(memNo);

        model.addAttribute("memberDetail", memberDB);

        return "page/member_modify";
    }

    // 회원 정보 수정 작업
    @PostMapping("/member/modify")
    public String memberModity(@ModelAttribute("member") MemberJoinVo member) {

        // 회원 정보 수정
        memberService.reviseMember(member);

        return "redirect:/member";
    }

    //회원등급에 회원 존재 여부 확인
    @ResponseBody
    @PostMapping("/intranet/grade/list/memgrade_check")
    public String memberGradeCheck(int memGrade) {
        int checkgrade = memberService.checkMemberGrade(memGrade);

        if (checkgrade != 0) {
            return "false";
        } else {
            return "success";
        }
    }
}
