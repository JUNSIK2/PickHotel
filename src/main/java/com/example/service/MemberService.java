package com.example.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.member.MemberDao;
import com.example.paging.PaginationInfo;
import com.example.vo.MemberVo;

@Service("cityService")
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	// 로그인
	public MemberVo loginMember(Map map) {
		return this.memberDao.Selectlogin(map);
	}
	
	// 방문횟수 카운트
	public void visitCount(String id) {
		this.memberDao.UpdateVisitCount(id);
	}

	// 탈퇴회원 조회
	public String checkWithDraw(String id) {
		return this.memberDao.SelectWithDraw(id);
	}

	// 아이디 찾기
	public List<MemberVo> retrieveMemberId(Map map) {
		return this.memberDao.selectNBMemberList(map);
	}

	// 비밀번호 변경
	public void modifyPw(Map map) {
		this.memberDao.UpdatePwMember(map);
	}

	// 회원가입
	public void registerMember(Map map) {
		this.memberDao.insertMember(map);
	}

	// 아이디 중복체크
	public String retrieveIdCheck(String id) {
		return this.memberDao.selectIdCheck(id);
	}

	// 닉네임 중복체크
	public String retrieveNickCheck(String nick) {
		return this.memberDao.selectNickCheck(nick);
	}

	// 회원 수정
	public void reviseMember(MemberVo member) {
		this.memberDao.updateMember(member);
	}

	// 회원 상태 변경
	public void reviseMemberState(int memNo, String state) {
		this.memberDao.updateMemberState(memNo, state);
	}

	// 회원 등급 변경
	public void reviseMemberGrade(int memNo, int grade) {
		this.memberDao.updateMemberGrade(memNo, grade);
	}

	// 회원 조회
	public MemberVo retrieveMember(int memNo) {
		return this.memberDao.selectMember(memNo);
	}

	// 회원 전체조회
	public List<MemberVo> retrieveMemberList(MemberVo params) {
		List<MemberVo> memberList = Collections.emptyList();

		int memberTotalCount = this.memberDao.selectMemberTotalCount(params);
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(memberTotalCount);

		params.setPaginationInfo (paginationInfo);

		if (memberTotalCount > 0) {
			memberList = this.memberDao.selectMemberList(params);
		}

		return memberList;
	}

	// 회원 수 조회
	public int retrieveMemberTotalCount(MemberVo params) {
		return this.memberDao.selectMemberTotalCount(params);
	}
}