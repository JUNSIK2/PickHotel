package com.example.board.service;

import com.example.board.model.RoomResponse;
import com.example.board.model.RoomVo;

import java.util.List;

public interface RoomService {
    List<RoomVo> retrieveRoomList();

    RoomResponse retrieveRooms();

    //방 상세 조회
   RoomVo retrieveRoom(int roomNo);

    //방 생성
    String registerRoom(RoomVo room);

    //방 삭제
    void removeRoom(int roomNo);

    //방 업데이트
    void updateRoom(RoomVo room);
}
