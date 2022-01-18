package com.example.member.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/*
작성일 : 2021-12-31
작성자 : 김소진
내용 : 회원 아이디, 비밀번호 유효성 검사 VO
 */
@Getter
@Setter
public class MemberIdPwVo {
    @NotBlank(message = "ID를 입력하세요.")
    @Email(message = "이메일 형식으로 작성하세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String pwd;

}