package com.example.member.model;

import com.example.common.paging.CommonVo;
import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
작성일 : 2021-12-31
작성자 : 김소진
내용 : 회원 목록 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberVo extends CommonVo {
    private int memNo;
    private String id;
    private String pwd;
    private String name;
    private String nick;
    private String gender;
    private String hp;
    private String birth;
    private String joinDate;
    private String state;
    private int boardCount;
    private int commentCount;
    private int visitCount;
    private int grade;
    private String gradeName;

    public MemberVo(int memNo, String state) {
        super();
        this.memNo = memNo;
        this.state = state;
    }

    public MemberVo(int memNo, int grade) {
        super();
        this.memNo = memNo;
        this.grade = grade;
    }

    public MemberVo(int grade) {
        super();
        this.grade = grade;
    }
}