package com.example.grade.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeUpVo {
    private int memNo;
    private String id;
    private String nick;
    private int grade; //현재 회원 등급
    private int boardCount;
    private int commentCount;
    private int visitCount;
    private int gradeno; // 등업 신청건 pk
    private int aftergrade; // 등업 신청 등급
    private int beforegrade;// 등업 신청시 현재 등급
    private String joinDate; // 회원 가입일
    private String datetime; // 등업 신청일
    private String gradeupstate;
    private String gradeupstateP; //상태 한글 출력을 위함

}
