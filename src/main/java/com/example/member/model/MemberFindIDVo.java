package com.example.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/*
작성일 : 2021-12-31
작성자 : 김소진
내용 : 회원 아이디 찾기 유효성 검사를 위한 VO
 */
@Getter
@Setter
public class MemberFindIDVo {
    @NotBlank(message = "유저 이름을 입력하세요.")
    private String name;

    @NotBlank(message = "생년월일을 입력하세요.")
    private String birth;

}