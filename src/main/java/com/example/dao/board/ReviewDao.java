package com.example.dao.board;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.vo.ReviewVo;

@Repository("reviewDao")
public class ReviewDao {
	@Autowired
	private SqlSession sqlSession;
	
	// 리뷰를 등록하다.
	public void insertReview(ReviewVo review) {
		this.sqlSession.insert("ReviewDao.insertReview", review);
	}
	
	// 리뷰를 업데이트하다.
	public void updateReview(ReviewVo review) {
		this.sqlSession.update("ReviewDao.updateReview", review);
	}
	
	//리뷰를 삭제한다.
	
}













