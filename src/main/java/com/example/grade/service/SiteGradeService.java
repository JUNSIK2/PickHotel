package com.example.grade.service;


import com.example.grade.model.SiteGradeVo;

import java.util.List;

public interface SiteGradeService {
    List<SiteGradeVo> retriveSiteGradeList();

    List<SiteGradeVo> retriveSiteGradeToUser();

    SiteGradeVo retriveSiteGrade(int grade);

    void siteGradeModify(SiteGradeVo sitegrade);

    List<SiteGradeVo> retrivePossibleGrade(int memNo);
}
