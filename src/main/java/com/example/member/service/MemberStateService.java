package com.example.member.service;

import com.example.common.paging.PaginationInfo;
import com.example.member.model.MemberAndMemberStateVo;
import com.example.member.model.MemberStateVo;
import com.example.member.persistent.MemberStateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("memberStateService")
public class MemberStateService {

    @Autowired
    private MemberStateDao memberStateDao;

    // 강제탈퇴 회원목록 조회
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<MemberAndMemberStateVo> retrieveMemberBlockList(MemberStateVo params) {
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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public int retrieveMemberBlockTotalCount(MemberStateVo params) {
        return this.memberStateDao.selectMemberBlockTotalCount(params);
    }

    // 강제 탈퇴 정보 상세조회
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public MemberAndMemberStateVo retrieveMemberBlock(int stateNo) {
        MemberAndMemberStateVo memberBlock = this.memberStateDao.selectMemberBlock(stateNo);
        return memberBlock;
    }

    // 탈퇴 정보 등록
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void registerMemberState(MemberStateVo memberState) {
        this.memberStateDao.insertMemberState(memberState);
    }

    // 탈퇴 정보 수정
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void reviseMemberState(MemberStateVo memberState) {
        this.memberStateDao.updateMemberState(memberState);
    }

    // 탈퇴 정보 삭제
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void removeMemberState(int memNo) {
        this.memberStateDao.delectMemberState(memNo);
    }
}