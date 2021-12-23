package com.example.dao.grade;

import java.util.List;

import com.example.vo.GradeUpVo;

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
