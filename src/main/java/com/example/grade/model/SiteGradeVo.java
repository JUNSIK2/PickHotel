package com.example.grade.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SiteGradeVo {
    private int memGrade; // 등급
    private String memGradeName; // 등급명
    private int memGradeType; // 등업 방식( 0 : 자동, 1 : 수동 )
    private int memGradeBoard; // 등업기준_게시글수
    private int memGradeComment; // 등업기준_댓글수
    private int memGradeVisit; // 등업기준_방문일수
    private int memGradeUse; // 등급사용여부( 0 : 미사용, 1 : 사용)
}
