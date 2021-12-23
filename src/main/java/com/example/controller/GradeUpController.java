package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.GradeUpService;
import com.example.service.MemberService;
import com.example.vo.GradeUpVo;
import com.example.vo.MemberVo;

@Controller
public class GradeUpController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private GradeUpService gradeUpService;

	// 등업신청목록조회(사용자)
	@GetMapping("/member/gradeup_list")
	public String list(Model model, HttpServletRequest request) {

		int memNo;
		HttpSession session = request.getSession();
		try {
			MemberVo member = (MemberVo) session.getAttribute("member");
			memNo = member.getMemNo();

		} catch (Exception e) {
			return "redirect:/";
		}

		GradeUpVo gradeUp = new GradeUpVo();
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
	public int gradeup_checked(HttpServletRequest request) {

		int memNo = 2;
		// 해당 회원앞으로 대기중인 등업신청건이 몇개인지 확인 할 것
		HttpSession session = request.getSession();
		try {
			MemberVo member = (MemberVo) session.getAttribute("member");
			memNo = member.getMemNo();
		} catch (Exception e) {
			// TODO: handle exception
		}
		int yetOk = gradeUpService.checkedGradeUp(memNo);
		// 1. 신청자가 대기중인 등업신청건이 있는지 확인
		if (yetOk == 0)
			return 0;
		else
			return 1;
	}

	// 등업신청 폼 창
	@GetMapping("/member/gradeup_write")
	public String gradeup_write(Model model, HttpServletRequest request) {
		int memNo = 0;
		int grade = 0;
		String nick = "";

		HttpSession session = request.getSession();
		try {
			MemberVo member = (MemberVo) session.getAttribute("member");
			memNo = member.getMemNo();
			grade = member.getGrade();
			nick = member.getNick();

		} catch (Exception e) {
			return "redirect:/";
		}

		// 2.가능한 등급 목록 정보 받아올것'

		// 3. 현재 회원의 정보를 받아와야함
		MemberVo member = memberService.retrieveMember(memNo);
		int boardCount = member.getBoardCount();
		int commentCount = member.getCommentCount();
		int visitCount = member.getVisitCount();
		String joinDate = member.getJoinDate();

		GradeUpVo gradeUp = new GradeUpVo();
		gradeUp.setNick(nick);
		gradeUp.setMemNo(memNo);
		gradeUp.setBeforegrade(grade);
		gradeUp.setBoardCount(boardCount);
		gradeUp.setCommentCount(commentCount);
		gradeUp.setVisitCount(visitCount);
		gradeUp.setJoinDate(joinDate);

		model.addAttribute("gradeUp", gradeUp);
		try {
			if (gradeUpService.checkedGradeUp(memNo) != 0) {
				return "redirect:/";
			} else {
				return "page/member_gradeup_write";
			}
		} catch (Exception e) {

		}

		return "page/member_gradeup_write";
	}

	// 등업신청 작동 (등업 신청 버튼 누르면 실행함)
	@PostMapping("/member/gradeup")
	public String gradeup(@ModelAttribute("GradeUpVo") GradeUpVo gradeUp, HttpServletRequest request) {

		// 세션 가져오기
		int memNo = 2;
		int beforegrade = 1;
		HttpSession session = request.getSession();
		try {
			MemberVo member = (MemberVo) session.getAttribute("member");
			memNo = member.getMemNo();
		} catch (Exception e) {
			// TODO: handle exception
		}

		gradeUp.setMemNo(memNo);
		gradeUp.setBeforegrade(beforegrade);

		gradeUpService.registerGradeUp(gradeUp);

		return "redirect:/member/gradeup_list";

	}

	// 등업신청 삭제
	@ResponseBody
	@GetMapping("/member/gradeup_delete/{gradeno}")
	public String gradeupdelete(@PathVariable("gradeno") int gradeno, Model model) {

		gradeUpService.deleteGradeUp(gradeno);

		model.addAttribute("delete", gradeno);
		// 세션으로 본인임을 확인.

		return "success";
	}

}
