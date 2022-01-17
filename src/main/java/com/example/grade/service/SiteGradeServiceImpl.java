package com.example.grade.service;

import com.example.grade.model.SiteGradeVo;
import com.example.grade.persistent.SiteGradeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("siteGradeService")
public class SiteGradeServiceImpl implements SiteGradeService {

    @Autowired
    private SiteGradeDao siteGradeDao;

    //사이트 등급 목록 조회
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<SiteGradeVo> retriveSiteGradeList() {
        return this.siteGradeDao.selectSiteGradeList();
    }

    //사이트 등급 목록 유저에게 조회 (사용중단된 등급은 안보임)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<SiteGradeVo> retriveSiteGradeToUser() {
        return this.siteGradeDao.selectSiteGradeToUser();
    }

    //사이트 등급 상세조회
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public SiteGradeVo retriveSiteGrade(int grade) {
        return this.siteGradeDao.selectSiteGrade(grade);
    }

    //회원에 따른 등업가능한 등급 목록 조회
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<SiteGradeVo> retrivePossibleGrade(int memNo) {
        return this.siteGradeDao.selectPossibleGrade(memNo);
    }

    //사이트 등급 수정
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void siteGradeModify(SiteGradeVo sitegrade) {
        this.siteGradeDao.updateSiteGrade(sitegrade);

    }
}