package com.example.dao.board;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.vo.RoomVo;

@Repository("roomDao")
public class RoomDao {
	@Autowired
	private SqlSession sqlSession;
	
	// 방을 조회한다.
	public ArrayList<RoomVo> selectRoomList(){
		List<RoomVo> list = this.sqlSession.selectList("RoomDao.selectRoomList");
		ArrayList<RoomVo> roomList = (ArrayList<RoomVo>) list;
		return roomList;
	}
	
}
















