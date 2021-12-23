package com.example.controller;

import com.example.service.MemberService;
import com.example.util.CustomMailSender;
import com.example.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

	@GetMapping("/hello")
	public String hello(){
		throw new RuntimeException();
	}

	// 아이디 찾기 폼
	@GetMapping("/member_findidform")
	public String memberFindIdForm() {
		return "page/member_findidform";
	}

	// 아이디 찾기 결과
	@PostMapping("/member_findid")
	public String memberFindId(Model model, @RequestParam(value = "name") String name,
			@RequestParam(value = "birth") String birth) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("birth", birth);

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
	public String memberAlterPwForm(Model model, @RequestParam("id") String id) {
		model.addAttribute("id", id);
		return "page/member_alterpwform";
	}

	// 비밀번호 변경
	@PostMapping("/member_alterpw")
	public String memberAlterPw(@RequestParam("id") String id, @RequestParam("pwd") String pwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pwd", pwd);
		this.memberService.modifyPw(map);

		return "page/index";
	}

	// 회원가입 폼
	@GetMapping("/member_joinform")
	public String memberJoinForm() {
		return "page/member_join";
	}

	// 회원가입
	@PostMapping("/member_join")
	public String memberJoin(@RequestParam("id") String id, @RequestParam("nick") String nick,
			@RequestParam("name") String name, @RequestParam("pwd") String pwd, @RequestParam("gender") String gender,
			@RequestParam("hp") String hp, @RequestParam("birth") String birth) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("nick", nick);
		map.put("name", name);
		map.put("pwd", pwd);
		map.put("gender", gender);
		map.put("hp", hp);
		map.put("birth", birth);

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
	public Map memberWithDarwCheck(@RequestParam("id") String id, HttpServletResponse response) throws Exception {

		String state = this.memberService.checkWithDraw(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", state);

		return map;
	}

	// 인증메일 발송
	@GetMapping("/member_sendmail")
	@ResponseBody
	public Map memberMailSender(HttpServletRequest req, @RequestParam("id") String id)
			throws MessagingException, IOException {

		int random = (int) (Math.random() * 9000) + 1000;
		String message = "인증번호를 입력해주세요. : " + random;

		customMailSender.sendMail(id, message);

		int count = 1;
		if (id != null) {
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
	public Map memberIdCheck(Model model, @RequestParam("id") String id) {

		String idCheck = this.memberService.retrieveIdCheck(id);

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
	public Map memberNickCheck(Model model, @RequestParam("nick") String nick) {

		String nickCheck = this.memberService.retrieveNickCheck(nick);

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
	@GetMapping("/member_loginform")
	public String memberLoginForm() {
		
		return "page/member_login";
	}

	// 로그인
	@PostMapping("/member_login")
	public String memberLogin(HttpServletRequest req, Model model, @RequestParam(value = "id") String id,
			@RequestParam(value = "pwd") String pwd) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pwd", pwd);
		MemberVo member = this.memberService.loginMember(map);

		HttpSession session = req.getSession();

		if (member.getId() == null) {
			model.addAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "page/member_login";
		} else if (Integer.parseInt(member.getState()) > 1) {
			model.addAttribute("message", "탈퇴한 회원입니다.");
			return "page/member_login";
		} else {
			this.memberService.visitCount(id);
			session.setAttribute("member", member);
			session.setMaxInactiveInterval(180);
			return "redirect:/";
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
	public String memberModity(@ModelAttribute("member") MemberVo member) {

		// 회원 정보 수정
		memberService.reviseMember(member);

		// 세션에 저장된 상세 정보 삭제하기

		return "redirect:/member";
	}

}
