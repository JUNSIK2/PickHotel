package com.example.grade.service;

import com.example.grade.model.GradeUpVo;
import com.example.grade.persistent.GradeUpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gradeUpService")
public class GradeUpServiceImpl implements GradeUpService {

    @Autowired
    private GradeUpDao gradeUpDao;

    //	등업 신청 건 추가
    @Override
    public void registerGradeUp(GradeUpVo gradeup) {
        this.gradeUpDao.insertGradeUp(gradeup);

    }

    //	등업 신청 건 상세조회
    @Override
    public GradeUpVo selectOneGradeUp(int gradeno) {
        return this.gradeUpDao.selectOneGradeUp(gradeno);
    }

    //	회원별 등업 신청 건 목록조회
    @Override
    public List<GradeUpVo> selectGradeUp(int memNo) {
        return this.gradeUpDao.selectGradeUp(memNo);
    }

    //	등업 신청 건 전체 목록조회
    @Override
    public List<GradeUpVo> selectAllGradeUps() {
        return this.gradeUpDao.selectAllGradeUp();
    }

    //	회원별 대기중인 등업 신청건이 있는지 확인
    @Override
    public int checkedGradeUp(int memNo) {
        return this.gradeUpDao.checkedGradeUp(memNo);
    }

    // 등업 신청건 '수락' 상태 변경
    @Override
    public void acceptGradeUp(int gradeno) {
        this.gradeUpDao.acceptGradeUp(gradeno);
    }

    // 등업 신청건 '거절' 상태 변경
    @Override
    public void resetGradeUp(int gradeno) {
        this.gradeUpDao.resetGradeUp(gradeno);
    }

    //	등업 신청 건 삭제(취소)
    @Override
    public void deleteGradeUp(int gradeno) {
        this.gradeUpDao.deleteGradeUp(gradeno);
    }
}
