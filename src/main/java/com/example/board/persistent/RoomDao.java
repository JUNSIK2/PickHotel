package com.example.board.persistent;

import com.example.board.model.RoomVo;

import java.util.List;

public interface RoomDao {
    //숙소 등록
    void insertRoom(RoomVo room);

    //숙소 목록 조회
    List<RoomVo> selectRoomList();

    //숙소 상세 조회
    RoomVo selectRoom(int roomNo);

    //숙소 수정
    void updateRoom(RoomVo room);

    //숙소 삭제
    void deleteRoom(int roomNo);
}
