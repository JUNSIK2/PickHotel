package com.example.vo;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberAndMemberStateVo extends CommonVo{
	private String id;
	private String nick;
	private int memNo;
	private int stateNo;
	private String reason;
	private String withdrawDate;
	private String state;
}
