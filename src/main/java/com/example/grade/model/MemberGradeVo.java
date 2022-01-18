package com.example.grade.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberGradeVo {

    private int memGrade; // 등급
    private String memGradeName; // 등급명
    private boolean memGradeType; // 등업 방식( 0 : 자동, 1 : 수동 )
    private int memGradeBoard; // 등업기준_게시글수
    private int memGradeComment; // 등업기준_댓글수
    private int memGradeVisit; // 등업기준_방문일수
    private boolean memGradeUse; // 등급사용여부( 0 : 미사용, 1 : 사용)

}
