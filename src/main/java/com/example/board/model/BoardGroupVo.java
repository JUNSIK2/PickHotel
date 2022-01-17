package com.example.board.model;

import com.example.common.paging.CommonVo;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardGroupVo extends CommonVo {


    @NotEmpty(message = "게시판 그룹 이름을 입력하세요.")
    private String groupName; //게시판 그룹 이름

    private Integer groupNo;  // 게시판 그룹 번호
    private int boardCount; // 게시판 그룹에 속한 게시판 수


    //게시판 그룹 등록, 수정
    public BoardGroupVo(int groupNo, String groupName) {
        super();
        this.groupNo = groupNo;
        this.groupName = groupName;

    }


}
