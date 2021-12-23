package com.example.vo;

import com.example.paging.Criteria;
import com.example.paging.PaginationInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonVo extends Criteria{
	/** 페이징 정보 */
	private PaginationInfo paginationInfo;
}
