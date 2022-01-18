package com.example.board.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/*
숙소 목록 취득용 리소스 클래스
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewVo {
    private int postNo;

    private int roomNo;

    @JsonProperty("roomName")
    private String roomName;

    @JsonProperty("roomDeco")
    private String roomDeco;

    private int rateLoc;
    private int rateClean;
    private int rateComu;
    private int rateChip;
    private String visitDate;
    private String recommendPlace;
    private String notRecommendPerson;
}
