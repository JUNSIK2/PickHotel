package com.example.grade.service;

import com.example.grade.model.GradeUpVo;

import java.util.List;

public interface GradeUpService {
    //	등업 신청 건 추가
    void registerGradeUp(GradeUpVo gradeup);

    //	등업 신청 건 상세조회
    GradeUpVo selectOneGradeUp(int gradeno);

    //	회원별 등업 신청 건 목록조회
    List<GradeUpVo> selectGradeUp(int memNo);

    //	등업 신청 건 전체 목록조회
    List<GradeUpVo> selectAllGradeUps();

    //	회원별 대기중인 등업 신청건이 있는지 확인
    int checkedGradeUp(int memNo);

    // 등업 신청건 '수락' 상태 변경
    void acceptGradeUp(int gradeno);

    // 등업 신청건 '거절' 상태 변경
    void resetGradeUp(int gradeno);

    //	등업 신청 건 삭제(취소)
    void deleteGradeUp(int gradeno);
}
