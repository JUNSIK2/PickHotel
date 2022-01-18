package com.example.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/*
작성일 : 2021-12-31
작성자 : 김소진
내용 : 회원 아이디 유효성 검사 VO
 */
@Getter
@Setter
public class MemberNickVo {
    @NotBlank(message = "닉네임을 입력하세요.")
    private String nick;

}