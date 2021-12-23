package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.grade.GradeUpDao;
import com.example.vo.GradeUpVo;

@Service("gradeUpService")
public class GradeUpServiceImpl implements GradeUpService {

	@Autowired
	private GradeUpDao gradeUpDao;

//	등업 신청
	@Override
	public void registerGradeUp(GradeUpVo gradeup) {
		this.gradeUpDao.insertGradeUp(gradeup);

	}
	
	@Override
	public GradeUpVo selectOneGradeUp(int gradeno) {
		return this.gradeUpDao.selectOneGradeUp(gradeno);
	}
	
	@Override
	public List<GradeUpVo> selectGradeUp(int memNo) {
		return this.gradeUpDao.selectGradeUp(memNo);
	}

	@Override
	public List<GradeUpVo> selectAllGradeUps() {
		return this.gradeUpDao.selectAllGradeUp();
	}

	@Override
	public void deleteGradeUp(int gradeno) {
		this.gradeUpDao.deleteGradeUp(gradeno);

	}

	@Override
	public int checkedGradeUp(int memNo) {
		return this.gradeUpDao.checkedGradeUp(memNo);
	}

	// 등업 수락
	@Override
	public void acceptGradeUp(int gradeno) {
		this.gradeUpDao.acceptGradeUp(gradeno);
	}

	// 등업 거절
	@Override
	public void resetGradeUp(int gradeno) {
		this.gradeUpDao.resetGradeUp(gradeno);
	}
}
