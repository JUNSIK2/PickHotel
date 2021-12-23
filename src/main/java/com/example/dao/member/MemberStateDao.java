package com.example.dao.member;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.vo.MemberAndMemberStateVo;
import com.example.vo.MemberStateVo;
import com.example.vo.MemberVo;

@Repository("memberStateDao")
public class MemberStateDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	// 강제탈퇴 회원목록 조회
	public List<MemberAndMemberStateVo> selectMemberBlockList(MemberStateVo params) {
		List<MemberAndMemberStateVo> memberBlockList = this.sqlSessionTemplate
				.selectList("MemberState.selectMemberBlockList", params);
		return memberBlockList;
	}

	// 강제탈퇴 회원 수 조회
	public int selectMemberBlockTotalCount(MemberStateVo params) {
		return this.sqlSessionTemplate.selectOne("MemberState.selectMemberBlockTotalCount", params);
	}

	// 강제 탈퇴 정보 상세조회
	public MemberAndMemberStateVo selectMemberBlock(int stateNo) {
		MemberAndMemberStateVo memberBlock = this.sqlSessionTemplate.selectOne("MemberState.selectMemberBlock",
				stateNo);
		return memberBlock;
	}

	// 강제탈퇴 정보 등록
	public void insertMemberState(MemberStateVo memberState) {
		this.sqlSessionTemplate.insert("MemberState.insertMemberState", memberState);
	}

	// 강제탈퇴 정보 수정
	public void updateMemberState(MemberStateVo memberState) {
		this.sqlSessionTemplate.update("MemberState.updateMemberState", memberState);
	}

	// 강제탈퇴 정보 삭제
	public void delectMemberState(int memNo) {
		this.sqlSessionTemplate.update("MemberState.updateMemberState", memNo);
	}

}