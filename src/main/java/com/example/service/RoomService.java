package com.example.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.board.RoomDao;
import com.example.vo.RoomVo;

@Service("roomService")
public class RoomService {
	
	@Autowired
	private RoomDao roomDao;
	
	public ArrayList<RoomVo> retrieveRoomList(){
		return roomDao.selectRoomList();
	}
}
