package com.example.member.model;

import com.example.common.paging.CommonVo;
import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
작성일 : 2021-12-31
작성자 : 김소진
내용 : 회원 상태 관리를 위한 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberAndMemberStateVo extends CommonVo {
    private String id;
    private String nick;
    private int memNo;
    private int stateNo;
    private String reason;
    private String withdrawDate;
    private String state;
}
