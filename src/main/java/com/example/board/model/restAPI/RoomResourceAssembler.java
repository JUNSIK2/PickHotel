//package com.example.common.restAPI;
//
//import com.example.board.controller.RoomController;
//import com.example.board.model.RoomVo;
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.RepresentationModelAssembler;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
///*
//    작성자 : 김소진
//    작성일 : 2021-12-31
//    내용 : 강사님 코드 - HATEOAS 설정 - REST API 화면 구현을 위한 hateoas 링크 셋팅
//*/
//
//@Component
//public class RoomResourceAssembler implements RepresentationModelAssembler<RoomVo, EntityModel<RoomVo>> {
//
//    @Override
//    public EntityModel<RoomVo> toModel(RoomVo room) {
//        return EntityModel.of(room,
//                linkTo(methodOn(RoomController.class).retriveRoomById(room.getRoomNo())).withSelfRel(),
//                linkTo(methodOn(RoomController.class).removeRoom(room.getRoomNo())).withRel("remove-room"),
//                linkTo(methodOn(RoomController.class).createRoom(room)).withRel("create-room"),
//                linkTo(methodOn(RoomController.class).updateRoom(room)).withRel("update-room"),
//                linkTo(methodOn(RoomController.class).retriveAllRooms()).withRel("all-rooms"));
//    }
//
//    @Override
//    public CollectionModel<EntityModel<RoomVo>> toCollectionModel(Iterable<? extends RoomVo> rooms) {
//        return RepresentationModelAssembler.super.toCollectionModel(rooms)
//                .add(linkTo(methodOn(RoomController.class).retriveAllRooms()).withRel("all-rooms"));
//    }
//
//
//}
