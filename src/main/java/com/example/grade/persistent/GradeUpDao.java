package com.example.grade.persistent;

import com.example.grade.model.GradeUpVo;

import java.util.List;

public interface GradeUpDao {
    void insertGradeUp(GradeUpVo gradeup);

    GradeUpVo selectOneGradeUp(int gradeno);

    List<GradeUpVo> selectGradeUp(int memNo);

    List<GradeUpVo> selectAllGradeUp();

    void deleteGradeUp(int gradeno);

    int checkedGradeUp(int memNo);

    void acceptGradeUp(int gradeno);

    void resetGradeUp(int gradeno);

}
