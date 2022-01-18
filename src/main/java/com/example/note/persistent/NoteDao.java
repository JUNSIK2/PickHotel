package com.example.note.persistent;

import com.example.note.model.NoteVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("noteDao")
public class NoteDao {
    @Autowired
    private SqlSession sqlSession;

    // 쪽지 저장
    public void insertNote(String noteCon){
        this.sqlSession.insert("NoteDao.insertNote", noteCon);
    }

    // 쪽지 번호 가져오기
    public int selectLastInsertID(){
        return this.sqlSession.selectOne("NoteDao.selectLastInsertID");
    }

    // 쪽지 인덱스 저장
    public void insertNoteIndex(int noteNo, int noteGetMbNo, int noteSendMbNo){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("noteNo", noteNo);
        map.put("noteGetMbNo", noteGetMbNo);
        map.put("noteSendMbNo", noteSendMbNo);

        this.sqlSession.insert("NoteDao.insertNoteIndex", map);
    }

    // 아이디로 멤버 번호 조회
    public int selectMbNo(String mbId){
        return this.sqlSession.selectOne("NoteDao.selectMbNo", mbId);
    }

    public List<Integer> selectMbNos(int noteNo){
        return this.sqlSession.selectList("NoteDao.selectGetMoNos", noteNo);
    }

    // 받은 사람 1명만
    public int selectOneGetMbNo(int mbNo, int noteNo){
        Map<String, Integer> map = new HashMap<>();
        map.put("mbNo", mbNo);
        map.put("noteNo", noteNo);

        return this.sqlSession.selectOne("NoteDao.selectOneGetMbNo", map);
    }

    // 받은 사람 모두 다
    public List<Integer> selectAllGetMbNo(int mbNo, int noteNo){
        Map<String, Integer> map = new HashMap<>();
        map.put("mbNo", mbNo);
        map.put("noteNo", noteNo);

        return this.sqlSession.selectList("NoteDao.selectOneGetMbNo", map);
    }

    // 쪽지 상세 조회
    public String selectDetailNote(int noteNo){
        return this.sqlSession.selectOne("NoteDao.selectDetailNote", noteNo);
    }

    // 수신 쪽지 목록 조회
    public List<NoteVo> selectReceiveNoteList(NoteVo params){
        return this.sqlSession.selectList("NoteDao.selectReceiveNoteList", params);
    }

    // 수신 쪽지 카운트
    public int selectReceiveNoteCount(NoteVo params){
        return this.sqlSession.selectOne("NoteDao.selectReceiveNoteCount", params);
    }


    // 발신 쪽지 목록 조회
    public List<NoteVo> selectSendNoteList(NoteVo params){
        return this.sqlSession.selectList("NoteDao.selectSendNoteList", params);
    }

    // 발신 쪽지 카운트
    public int selectSendNoteCount(NoteVo params){
        return this.sqlSession.selectOne("NoteDao.selectSendNoteCount", params);
    }

    // 보관함 쪽지 목록 조회
    public List<NoteVo> selectSaveNoteList(NoteVo params){
        return this.sqlSession.selectList("NoteDao.selectSaveNoteList", params);
    }

    // 보관함 쪽지 카운트
    public int selectSaveNoteCount(NoteVo params){
        return this.sqlSession.selectOne("NoteDao.selectSaveNoteCount", params);
    }

    // 선택된 수신 쪽지 삭제처리
    public void deleteReceiveNotes(int mbNo, int noteNo){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("mbNo", mbNo);
        map.put("noteNo", noteNo);

        this.sqlSession.update("NoteDao.deleteReceiveNotes", map);
    }

    // 선택된 발신 쪽지 삭제처리
    public void deleteSendNotes(int mbNo, int noteNo){
        Map<String, Integer> map = new HashMap<>();
        map.put("mbNo", mbNo);
        map.put("noteNo", noteNo);

        this.sqlSession.update("NoteDao.deleteSendNotes", map);
    }

    // 선택된 보관함 수신 쪽지 삭제처리
    public void deleteSaveReceiveNotes(int mbNo, int noteNo){
        Map<String, Integer> map = new HashMap<>();
        map.put("mbNo", mbNo);
        map.put("noteNo", noteNo);

        this.sqlSession.update("NoteDao.deleteSaveReceiveNotes", map);
    }

    // 선택된 보관함 발신 쪽지 삭제처리
    public void deleteSaveSendNotes(int mbNo, int noteNo) {
        Map<String, Integer> map = new HashMap<>();
        map.put("mbNo", mbNo);
        map.put("noteNo", noteNo);

        this.sqlSession.update("NoteDao.deleteSaveSendNotes", map);
    }

    // 선택된 받은쪽지 보관함 저장
    public void updateSaveRetrieveNotes(int mbNo, int noteNo){
        Map<String, Integer> map = new HashMap<>();
        map.put("mbNo", mbNo);
        map.put("noteNo", noteNo);

        this.sqlSession.update("NoteDao.updateSaveRetrieveNotes", map);
    }

    // 선택된 보낸쪽지 보관함 저장
    public void updateSaveSendNotes(int mbNo, int noteNo){
        Map<String, Integer> map = new HashMap<>();
        map.put("mbNo", mbNo);
        map.put("noteNo", noteNo);

        this.sqlSession.update("NoteDao.updateSaveSendNotes", map);
    }

    // 있는 회원 확인
    public int checkMbId(String mbId){
        return this.sqlSession.selectOne("NoteDao.checkMbId", mbId);
    }
}
