package com.example.member.controller;

import com.example.member.CustomMailSender;
import com.example.member.model.*;
import com.example.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberController {

    @Autowired
    public MemberService memberService;

    @Autowired
    public CustomMailSender customMailSender;

    // 아이디 찾기 폼
    @GetMapping("/findidform")
    public String memberFindIdForm() {
        return "page/member_findidform";
    }

    // 아이디 찾기 결과
    @PostMapping("/member_findid")
    public String memberFindId(@Valid MemberFindIDVo member, Model model) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", member.getName());
        map.put("birth", member.getName());

        List<MemberVo> findIdResultList = memberService.retrieveMemberId(map);

        model.addAttribute("findIdResultList", findIdResultList);

        return "page/member_findid";
    }

    // 비밀번호 찾기 폼
    @GetMapping("/member_findpwform")
    public String memberFindPwForm() {
        return "page/member_findpwform";
    }

    // 비밀번호 변경 폼
    @PostMapping("/member_alterpwform")
    public String memberAlterPwForm(@Valid MemberIdVo member, Model model) {
        model.addAttribute("id", member.getId());
        return "page/member_alterpwform";
    }

    // 비밀번호 변경
    @PostMapping("/member_alterpw")
    public String memberAlterPw(@Valid MemberIdPwVo member) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", member.getId());
        map.put("pwd", member.getPwd());
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
        map.put("pwd", member.getPwd());
        map.put("gender", member.getGender());
        map.put("hp", member.getHp());
        map.put("birth", member.getBirth());

        this.memberService.registerMember(map);

        return "redirect:/member/welcome";
    }

    // 회원 가입 환영 페이지
    @GetMapping("/member/welcome")
    public String memberWelcome() {
        return "page/member_welcome";
    }

    // 탈퇴회원 조회
    @GetMapping("/member_withdarwcheck")
    @ResponseBody
    public Map memberWithDarwCheck(@Valid MemberIdVo member, HttpServletResponse response) throws Exception {

        String state = this.memberService.checkWithDraw(member.getId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", state);

        return map;
    }

    // 인증메일 발송
    @GetMapping("/member_sendmail")
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
    @GetMapping("/member_mailcheck")
    @ResponseBody
    public Map memberMailSender(HttpServletRequest req) throws MessagingException, IOException {

        HttpSession session = req.getSession();
        int random = (int) session.getAttribute("random");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("random", random);

        return map;
    }

    // 아이디 중복체크
    @GetMapping("/member_idcheck")
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
    @GetMapping("/member_nickcheck")
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
    public String memberWithdarw(HttpServletRequest req) {
        HttpSession session = req.getSession();
        MemberVo member = (MemberVo) session.getAttribute("member");
        memberService.reviseMemberState(member.getMemNo(), "2");
        session.invalidate();
        return "redirect:/";
    }

    // 로그인 폼
    @GetMapping("/login")
    public String memberLoginForm() {
        return "page/member_login";
    }

    // 로그인 작동
    @PostMapping("/login")
    public String memberLogin(@Valid MemberIdPwVo member, @RequestParam(value = "redirectUrl", required = false) String redirectUrl, HttpServletRequest req, Model model) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", member.getId());
        map.put("pwd", member.getPwd());
        MemberVo member1 = this.memberService.loginMember(map);

        HttpSession session = req.getSession();

        if (member1.getId() == null) {
            model.addAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "page/member_login";
        } else if (Integer.parseInt(member1.getState()) > 1) {
            model.addAttribute("message", "탈퇴한 회원입니다.");
            return "page/member_login";
        } else {
            this.memberService.visitCount(member.getId());
            session.setAttribute("member", member1);
            session.setMaxInactiveInterval(180);

            //요청을 통한 로그인 페이지 접근시, 다시 되돌아감
            if (redirectUrl != "") {
                return "redirect:" + redirectUrl;
            } else {
                return "redirect:/";
            }

        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/"; // 주소 요청으로 변경
    }

    // 회원 정보 상세조회 + 수정 폼
    @GetMapping("/member")
    public String memberDetail(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();

        // 로그인 상태인 경우
        if (session != null && session.getAttribute("member") != null && !session.getAttribute("member").equals("")) {

            // 로그인 세션에 저장된 회원 정보 가져오기
            int memNo = ((MemberVo) session.getAttribute("member")).getMemNo();

            // DB에서 회원정보 상세 가져오기
            MemberVo memberDetail = memberService.retrieveMember(memNo);

            model.addAttribute("memberDetail", memberDetail);

            return "page/member_modify";
        } else { // 로그인 세션이 존재하지 않는 경우 (로그인 상태가 아닌 경우)

            // 로그인 페이지로 이동
            return "redirect:/member_loginform";

        }
    }

    // 회원 정보 수정 작업
    @PostMapping("/member/modify")
    public String memberModity(@ModelAttribute("member") MemberJoinVo member) {

        // 회원 정보 수정
        memberService.reviseMember(member);

        return "redirect:/member";
    }

}