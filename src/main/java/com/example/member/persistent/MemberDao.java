package com.example.member.persistent;

import com.example.member.model.MemberJoinVo;
import com.example.member.model.MemberVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("memberDao")
public class MemberDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    // 탈퇴회원 조회
    public String SelectWithDraw(String id) {
        return this.sqlSessionTemplate.selectOne("MemberDao.SelectWithDraw", id);
    }

    // 아이디 찾기
    public List<MemberVo> selectNBMemberList(Map map) {
        return this.sqlSessionTemplate.selectList("MemberDao.SelectNBMemberList", map);
    }

    // 비밀번호 변경
    public void UpdatePwMember(Map map) {
        this.sqlSessionTemplate.update("MemberDao.UpdatePwMember", map);
    }

    // 회원가입
    public void insertMember(Map map) {
        this.sqlSessionTemplate.insert("MemberDao.InsertMember", map);
    }

    // 회원가입 룰 추가
    public void insertRole(String id) {
        this.sqlSessionTemplate.insert("MemberDao.InsertRole", id);
    }

    // 아이디 중복체크
    public String selectIdCheck(String id) {
        return this.sqlSessionTemplate.selectOne("MemberDao.selectIdCheck", id);
    }

    // 닉네임 중복체크
    public String selectNickCheck(String nick) {
        return this.sqlSessionTemplate.selectOne("MemberDao.selectNickCheck", nick);
    }

    //회원등급에 회원 존재여부 확인
    public int checkMemberGrade(int memGrade) {
        return this.sqlSessionTemplate.selectOne("MemberDao.checkMemberGrade", memGrade);
    }

    // 회원 목록을 조회한다
    public List<MemberVo> selectMemberList(MemberVo params) {
        return this.sqlSessionTemplate.selectList("MemberDao.selectMemberList", params);
    }

    // 회원수를 조회한다
    public int selectMemberTotalCount(MemberVo params) {
        return this.sqlSessionTemplate.selectOne("MemberDao.selectMemberTotalCount", params);
    }

    // 회원 상세조회
    public MemberVo selectMember(int memNo) {
        System.out.println("selectMember 실행");
        return this.sqlSessionTemplate.selectOne("MemberDao.selectMember", memNo);
    }

    // 회원 수정
    public void updateMember(MemberJoinVo member) {
        this.sqlSessionTemplate.update("MemberDao.updateMember", member);
    }

    // 회원 상태 변경
    public void updateMemberState(int memNo, String state) {
        MemberVo memberVo = new MemberVo(memNo, state);
        this.sqlSessionTemplate.update("MemberDao.updateMemberState", memberVo);
    }

    // 회원 등급 변경
    public void updateMemberGrade(int memNo, int grade) {
        MemberVo memberVo = new MemberVo(memNo, grade);
        this.sqlSessionTemplate.update("MemberDao.updateMemberGrade", memberVo);
    }

    // 회원 게시글 갯수 증감
    public void updateBoardPost(int memNo, int updateCount) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("memNo", memNo);
        paramMap.put("updateCount", updateCount);

        sqlSessionTemplate.update("MemberDao.updateBoardCount", paramMap);
    }

    // 방문횟수 카운트+1
    public int updateVisitCount(int memNo) {
        sqlSessionTemplate.update("MemberDao.updateVisitCount", memNo);

        return memNo;
    }

    // 회원 댓글수 증감
    public void updateCommentCount(int memNo, int updateCount) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("memNo", memNo);
        paramMap.put("updateCount", updateCount);

        sqlSessionTemplate.update("MemberDao.updateCommentCount", paramMap);
    }

}