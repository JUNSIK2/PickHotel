package com.example.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.member.MemberStateDao;
import com.example.paging.PaginationInfo;
import com.example.vo.MemberAndMemberStateVo;
import com.example.vo.MemberStateVo;
import com.example.vo.MemberVo;

@Service("memberStateService")
public class MemberStateService {

	@Autowired
	private MemberStateDao memberStateDao;

	// 강제탈퇴 회원목록 조회
	public List<MemberAndMemberStateVo> retrieveMemberBlockList(MemberStateVo params) {
		
		System.out.println("목록 조회 service");
		List<MemberAndMemberStateVo> memberList = Collections.emptyList();

		int memberTotalCount = this.memberStateDao.selectMemberBlockTotalCount(params);
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(memberTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (memberTotalCount > 0) {
			memberList = this.memberStateDao.selectMemberBlockList(params);
		}

		return memberList;
	}

	// 강제탈퇴 회원 수 조회
	public int retrieveMemberBlockTotalCount(MemberStateVo params) {
		return this.memberStateDao.selectMemberBlockTotalCount(params);
	}

	// 강제 탈퇴 정보 상세조회
	public MemberAndMemberStateVo retrieveMemberBlock(int stateNo) {
		MemberAndMemberStateVo memberBlock = this.memberStateDao.selectMemberBlock(stateNo);
		return memberBlock;
	}

	// 탈퇴 정보 등록
	public void registerMemberState(MemberStateVo memberState) {
		this.memberStateDao.insertMemberState(memberState);
	}

	// 탈퇴 정보 수정
	public void reviseMemberState(MemberStateVo memberState) {
		this.memberStateDao.updateMemberState(memberState);
	}

	// 탈퇴 정보 삭제
	public void removeMemberState(int memNo) {
		this.memberStateDao.delectMemberState(memNo);
	}
}