package com.example.member.service;

import com.example.common.paging.PaginationInfo;
import com.example.member.model.MemberJoinVo;
import com.example.member.model.MemberVo;
import com.example.member.persistent.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service("cityService")
public class MemberService {

    @Autowired
    private MemberDao memberDao;

    // 탈퇴회원 조회
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public String checkWithDraw(String id) {
        return this.memberDao.SelectWithDraw(id);
    }

    // 아이디 찾기
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<MemberVo> retrieveMemberId(Map map) {
        return this.memberDao.selectNBMemberList(map);
    }

    // 비밀번호 변경
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void modifyPw(Map map) {
        this.memberDao.UpdatePwMember(map);
    }

    // 회원가입
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void registerMember(Map map) {
        this.memberDao.insertMember(map);
    }

    // 회원가입 Role 추가
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void registerRole(String id) {
        this.memberDao.insertRole(id);
    }

    // 아이디 중복체크
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public String retrieveIdCheck(String id) {
        return this.memberDao.selectIdCheck(id);
    }

    // 닉네임 중복체크
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public String retrieveNickCheck(String nick) {
        return this.memberDao.selectNickCheck(nick);
    }

    // 회원 수정
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void reviseMember(MemberJoinVo member) {
        this.memberDao.updateMember(member);
    }

    // 회원 상태 변경
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void reviseMemberState(int memNo, String state) {
        this.memberDao.updateMemberState(memNo, state);
    }

    // 회원 등급 변경
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void reviseMemberGrade(int memNo, int grade) {
        this.memberDao.updateMemberGrade(memNo, grade);
    }

    // 회원 조회
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public MemberVo retrieveMember(int memNo) {
        return this.memberDao.selectMember(memNo);
    }

    // 회원 전체조회
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<MemberVo> retrieveMemberList(MemberVo params) {
        List<MemberVo> memberList = Collections.emptyList();

        int memberTotalCount = this.memberDao.selectMemberTotalCount(params);
        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(memberTotalCount);

        params.setPaginationInfo(paginationInfo);

        if (memberTotalCount > 0) {
            memberList = this.memberDao.selectMemberList(params);
        }

        return memberList;
    }

    //회원등급에 회원 존재여부 확인
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public int checkMemberGrade(int memGrade) {
        return this.memberDao.checkMemberGrade(memGrade);
    }

    // 회원 게시글 갯수 증감
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void reviseBoardCount(int memNo, int updateCount) {
        this.memberDao.updateBoardPost(memNo, updateCount);
    }

    // 방문횟수 카운트+1
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public int reviseVisitCount(int memNo) {
        return this.memberDao.updateVisitCount(memNo);
    }

    // 회원 댓글수 증감
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void reviseCommentCount(int memNo, int updateCount) {
        this.memberDao.updateCommentCount(memNo, updateCount);
    }

}