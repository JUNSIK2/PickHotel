package com.example.board.controller;

import com.example.board.model.Link;
import com.example.board.model.RoomResponse;
import com.example.board.model.RoomVo;
import com.example.board.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
사용자 API
 */
@RestController
@Validated
public class RoomController {
    @Autowired
    private RoomService roomService;

    //방 쓰기 폼
    @GetMapping("/room/write")
    public ModelAndView roomWriteForm() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/page/room_write");
        return mv;
    }

    //방 작성
    @PostMapping(value = "/roomUpload", produces = "application/json; charset=UTF-8")
    public ModelAndView roomWrite(@RequestParam(value = "roomName", required = false) String roomName,
                                  @RequestParam(value = "roomDeco", required = false) String roomDeco,
                                  @RequestParam(value = "roomAddress", required = false) String roomAddress,
                                  @RequestParam(value = "images", required = false) MultipartFile image) throws IOException {

        RoomVo room = new RoomVo();

        if (image != null) {
            String originalFileName = image.getOriginalFilename();
            String extName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
            String systemFileName = getSaveFileName(extName);
            room.setSystemFileName(systemFileName);
            room.setOriginalFileName(originalFileName);

            byte[] data = image.getBytes();
            room.setImageBytes(data);
        }

        room.setRoomName(roomName);
        room.setRoomDeco(roomDeco);
        room.setRoomAddress(roomAddress);
        this.roomService.registerRoom(room);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/room/list");
        return mv;
    }

    //방 목록 조회
    @GetMapping("/room/list")
    public ModelAndView roomList() {

        ModelAndView mv = new ModelAndView();
        RoomResponse roomResponse = this.roomService.retrieveRooms();

        List<RoomVo> content = roomResponse.getContent();
        for (RoomVo room : content) {
            System.out.println("no : " + room.getNo());
            System.out.println("roomName : " + room.getRoomName());
            System.out.println("roomDeco : " + room.getRoomDeco());
            System.out.println("roomAddress : " + room.getRoomAddress());
            System.out.println("systemFileName : " + room.getSystemFileName());
            System.out.println("originalFileName : " + room.getOriginalFileName());

            List<Link> linkList = room.getLinks();
            for (Link link : linkList) {
                System.out.println("rel : " + link.getRel());
                System.out.println("href : " + link.getHref());
            }
        }

        mv.addObject("roomResponse", roomResponse);
        mv.setViewName("/page/room_list");
        return mv;
    }

    //방 상세조회
    @GetMapping("/room/{roomNo}")
    public ModelAndView roomDetail(@PathVariable("roomNo") int roomNo) {

        RoomVo room = this.roomService.retrieveRoom(roomNo);
        ModelAndView mv = new ModelAndView();

        mv.addObject("room", room);
        mv.setViewName("/page/room_detail");
        return mv;
    }

    //방 삭제
    @DeleteMapping("/room/{roomNo}")
    public Map roomRemove(@PathVariable("roomNo") int roomNo) {

        this.roomService.removeRoom(roomNo);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", "success");
        return map;
    }


    // 현재 시간을 기준으로 파일 이름 생성
    private String getSaveFileName(String extName) {
        String fileName = "";

        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += calendar.get(Calendar.MONTH);
        fileName += calendar.get(Calendar.DATE);
        fileName += calendar.get(Calendar.HOUR);
        fileName += calendar.get(Calendar.MINUTE);
        fileName += calendar.get(Calendar.SECOND);
        fileName += calendar.get(Calendar.MILLISECOND);
        fileName += extName;

        return fileName;
    }


}