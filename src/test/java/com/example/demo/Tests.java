package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dao.board.CommentDao;
import com.example.dao.member.MemberDao;
import com.example.service.MemberService;
import com.example.vo.CommentVo;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@Log
class Tests {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private CommentDao commentDao;

	@Test
	void test1() {
		assertNotNull(sqlSessionTemplate);
	}

//	@Test
//	void test2() {
//		MemberVo memberVo = new MemberVo("thwls", "pspsp", "소진짱", "여자", "F", "ㅇㅇ", "2021-10-29");
//		memberService.registerMember(memberVo);
//	}
	@Test
	void test3() {
		CommentVo commentVo = new CommentVo(1, 1, "내용입니다~~", 0, 0);
		commentDao.insertComment(commentVo);
		//memberService.retrieveMemberBlockList();
	}

}
