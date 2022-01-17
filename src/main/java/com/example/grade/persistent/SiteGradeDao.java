package com.example.grade.persistent;


import com.example.grade.model.SiteGradeVo;

import java.util.List;

public interface SiteGradeDao {
    List<SiteGradeVo> selectSiteGradeList();

    List<SiteGradeVo> selectSiteGradeToUser();

    SiteGradeVo selectSiteGrade(int grade);

    void updateSiteGrade(SiteGradeVo sitegrade);

    List<SiteGradeVo> selectPossibleGrade(int memNos);
}
