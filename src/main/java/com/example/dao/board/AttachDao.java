package com.example.dao.board;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.vo.AttachVo;

@Repository("attachDao")
public class AttachDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//파일을 등록하다.
	public void insertPostAttach(AttachVo attach) {
		this.sqlSession.insert("AttachDao.insertPostAttach", attach);
	}
	
	//파일을 삭제한다.
	public void deletePostAttach(int attaNo) {
		this.sqlSession.delete("AttachDao.deletePostAttach", attaNo);
	}
	
	//게시글 삭제에 의해 파일을 삭제한다.
	public void deleteAttachbyPost(int poNo) {
		this.sqlSession.delete("AttachDao.deleteAttachbyPost", poNo);
	}
}














