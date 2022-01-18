package com.example.member.model;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/*
작성일 : 2021-12-31
작성자 : 김소진
내용 : 회원가입 유효성 검사를 위한 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinVo {

    private int memNo;
    @NotBlank(message = "ID를 입력하세요.")
    @Email(message = "이메일 형식으로 작성하세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String pwd;

    @NotBlank(message = "유저 이름을 입력하세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력하세요.")
    private String nick;

    @NotBlank(message = "성별을 선택하세요.")
    private String gender;

    @NotBlank(message = "휴대폰번호를 입력하세요.")
    private String hp;

    @NotBlank(message = "생년월일을 입력하세요.")
    private String birth;

    private String joinDate;
    private String state;
    private int boardCount;
    private int commentCount;
    private int visitCount;
    private int grade;
}