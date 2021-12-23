package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.BoardService;
import com.example.service.GradeUpService;
import com.example.service.MemberService;
import com.example.service.MemberStateService;
import com.example.vo.BoardVo;
import com.example.vo.GradeUpVo;
import com.example.vo.MemberAndMemberStateVo;
import com.example.vo.MemberStateVo;
import com.example.vo.MemberVo;


@Controller
public class AdminController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberStateService memberStateService;

	@Autowired
	private GradeUpService gradeUpService;

	@Autowired
	private BoardService boardService;

//	@GetMapping()
//	public String intranetIndex() {
//		//return "page/intranet/index";
//		//return "page/intranet/member_list";
//	}

	// ######## 회원 목록 ########
	// 회원 목록
	@GetMapping(value = { "/intranet", "/intranet/index", "/intranet/member/list" })
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
	public String memberModify(@ModelAttribute("member") MemberVo member) {
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

	// ######## 등업 신청 건 관리 ########
	// 등업신청목록조회(관리자)
	@GetMapping("/intranet/member/gradeup_list")
	public String list(Model model) {

		List<GradeUpVo> gradeUps = this.gradeUpService.selectAllGradeUps();
		model.addAttribute("GradeUps", gradeUps);
		return "page/intranet/member_gradeup_list";
	}

	// 등업상태 변경
	@ResponseBody
	@PostMapping("/intranet/gradeup_update")
	public List<GradeUpVo> updateGradeUp(@RequestParam("gradenoList") List<Integer> gradenos,
			@RequestParam("gradeupstate") String gradeupstate, Model model) {

		if (gradeupstate.equals("accept")) {

			for (int gradeno : gradenos) {

				// 해당 등업 신청건의 정보 가져오기
				GradeUpVo gradeUpVo = gradeUpService.selectOneGradeUp(gradeno);
				int memNo = gradeUpVo.getMemNo();
				int aftergrade = gradeUpVo.getAftergrade();

				// 해당 회원이 등업이 가능한 조건인지 확인 할 것

				// 회원 등급 변경
				memberService.reviseMemberGrade(memNo, aftergrade);

				// 등업 건 승인 처리
				gradeUpService.acceptGradeUp(gradeno);
			}
		} else if (gradeupstate.equals("reset")) {
			for (int gradeno : gradenos) {
				/// 등업 건 거절 처리
				gradeUpService.resetGradeUp(gradeno);
			}
		}

		List<GradeUpVo> list = this.gradeUpService.selectAllGradeUps();
		return list;
	}

	// ######## 회원 강퇴 ########
	// 강퇴 회원 목록
	@GetMapping("/intranet/member/block_list")
	public String memberBlockList(@ModelAttribute("params") MemberStateVo params, Model model) {
		List<MemberAndMemberStateVo> members = memberStateService.retrieveMemberBlockList(params);
		model.addAttribute("members", members);
		return "page/intranet/member_block_list";
	}

	// 회원 강퇴 사유 작성
	@RequestMapping(value = "/intranet/member/block_write", method = { RequestMethod.GET, RequestMethod.POST })
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

			// 강제 탈퇴 건PK값으로 회원PK 조회
			memNo = memberStateService.retrieveMemberBlock(stateNo).getMemNo();

			// 회원 상태 "탈퇴" 변경
			memberService.reviseMemberState(memNo, "2");
		}

		List<MemberAndMemberStateVo> list = memberStateService.retrieveMemberBlockList(params);
		return list;
	}

	// ######## 게시판 그룹 관리 ########
	@GetMapping("/intranet/boardgroup_list")
	public String boardGroupList() {
		return "page/intranet/boardgroup_list";
	}

	// ######## 게시판 관리 ########
	// 게시판 만들기 폼
	@GetMapping("/intranet/board/write")
	public String boardWrite() {

		return "page/intranet/board_write";
	}

	// 게시판 등록 눌렀을 때 작동
	@PostMapping("/intranet/board/add")
	public String boardAdd(BoardVo board) {
		// 게시판 정보 추가
		boardService.insertBoard(board);
		return "redirect:/intranet/board/list";
	}

	/* 게시판 목록 */
	@GetMapping("/intranet/board/list")
	public String boardList(Model model) {
		List<BoardVo> boardList = boardService.retrieveBoardList();
		model.addAttribute("boardList", boardList);
		return "page/intranet/board_list";
	}

	// 게시판 상세 조회(수정 폼 보기)
	@GetMapping({ "/intranet/board/{boardNo}", "/intranet/board/" })
	public String boardDetail(@PathVariable(required = false) Integer boardNo, Model model) {
		if (boardNo == null)
			boardNo = 1;
		BoardVo board = boardService.selectBoard(boardNo);

		model.addAttribute("board", board);

		return "page/intranet/board_modify";
	}

	// 게시판 수정하기 버튼 눌렀을때
	@PostMapping("/intranet/board/modify")
	public String boardModify(BoardVo board) {

		boardService.updateBoard(board);

		return "redirect:/intranet/board/list";
	}

	// 게시판 삭제하기 버튼 눌렀을때
	@ResponseBody
	@GetMapping({ "/intranet/board_delete/{boardNo}", "/intranet/board_delete/" })
	public String boardDelete(@PathVariable(required = false) Integer boardNo) {
		if (boardNo == null)
			boardNo = 1;
		boardService.deleteBoard(boardNo);

		return "success";
	}

	// ######## 이벤트 관리 ########
	@GetMapping("/intranet/event_list")
	public String eventList() {
		return "page/intranet/event_list";
	}

	// ######## 통계 관리 ########
	@GetMapping("/intranet/statistics")
	public String statistics() {
		return "page/intranet/statistics";
	}

}
