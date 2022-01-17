package com.example.demo;

import com.example.grade.service.SiteGradeService;
import com.example.member.persistent.MemberDao;
import com.example.member.service.MemberService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    private SiteGradeService siteGradeService;

    @Test
    void test1() {
        assertNotNull(sqlSessionTemplate);
    }

    //	@Test
//	void test2() {
//		MemberVo memberVo = new MemberVo("thwls", "pspsp", "소진짱", "여자", "F", "ㅇㅇ", "2021-10-29");
//		memberService.registerMember(memberVo);
//	}
//    @Test
//    void test3() {
//        //siteGradeService.retriveSiteGradeList();
//        siteGradeService.retriveSiteGrade(2);
//    }

}