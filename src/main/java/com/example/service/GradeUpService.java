package com.example.service;

import java.util.List;

import com.example.vo.GradeUpVo;

public interface GradeUpService {

	void registerGradeUp(GradeUpVo gradeup);
	
	GradeUpVo selectOneGradeUp(int gradeno);
	
	List<GradeUpVo> selectGradeUp(int memNo);

	List<GradeUpVo> selectAllGradeUps();

	void deleteGradeUp(int gradeno);

	int checkedGradeUp(int memNo);

	// 등업 수락
	void acceptGradeUp(int gradeno);

	// 등업 거절
	void resetGradeUp(int gradeno);

}
