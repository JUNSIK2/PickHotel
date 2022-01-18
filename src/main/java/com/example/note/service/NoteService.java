package com.example.note.service;

import com.example.common.paging.PaginationInfo;
import com.example.note.model.NoteVo;
import com.example.note.persistent.NoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service("noteService")
public class NoteService {
    @Autowired
    private NoteDao noteDao;

    // 쪽지 저장
    public void registerNote(int noteSendMbNo, String noteCon, List<Integer> getMbNos){
        this.noteDao.insertNote(noteCon);
        int noteNo = this.noteDao.selectLastInsertID();

        for(Integer noteGetMbNo : getMbNos){
            this.noteDao.insertNoteIndex(noteNo, noteGetMbNo, noteSendMbNo);
        }
    }

    // 멤버 아이디로 멤버 번호찾기
    public int retrieveMbNo(String mbId){
        return this.noteDao.selectMbNo(mbId);
    }

    public List<Integer> retrieveMbNos(int noteNo){
        return this.noteDao.selectMbNos(noteNo);
    }

    // 받은 사람 1명만
    public int retrieveOneGetMbNo(int mbNo, int noteNo){
        return this.noteDao.selectOneGetMbNo(mbNo, noteNo);
    }

    // 받은 사람 모두 다
    public List<Integer> retrieveAllGetMbNo(int mbNo, int noteNo){
        return this.noteDao.selectAllGetMbNo(mbNo, noteNo);
    }

    // 쪽지 상세 조회
    public String retrieveDetailNote(int noteNo){
        return this.noteDao.selectDetailNote(noteNo);
    }

    // 수신 쪽지 목록 조회
    public List<NoteVo> retrieveReceiveNoteList(NoteVo params){
        List<NoteVo> noteList = new ArrayList<NoteVo>();

        int noteCount = this.noteDao.selectReceiveNoteCount(params);
        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(noteCount);

        params.setPaginationInfo(paginationInfo);

        if(noteCount > 0) {
            noteList = this.noteDao.selectReceiveNoteList(params);
        }

        return noteList;
    }


    // 발신 쪽지 목록 조회
    public List<NoteVo> retrieveSendNoteList(NoteVo params){
        List<NoteVo> noteList = new ArrayList<NoteVo>();

        int noteCount = this.noteDao.selectSendNoteCount(params);
        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(noteCount);

        params.setPaginationInfo(paginationInfo);

        if(noteCount > 0) {
            noteList = this.noteDao.selectSendNoteList(params);
        }

        return noteList;
    }

    // 보관함 쪽지 목록 조회
    public List<NoteVo> retrieveSaveNoteList(NoteVo params){
        List<NoteVo> noteList = new ArrayList<NoteVo>();

        int noteCount = this.noteDao.selectSendNoteCount(params);
        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(noteCount);

        params.setPaginationInfo(paginationInfo);

        if(noteCount > 0) {
            noteList = this.noteDao.selectSaveNoteList(params);
        }

        return noteList;
    }

    // 선택된 수신 쪽지 삭제처리
    public void removeReceiveNotes(int mbNo, int noteNo){
        this.noteDao.deleteReceiveNotes(mbNo, noteNo);
    }

    // 선택된 발신 쪽지 삭제처리
    public void removeSendNotes(int mbNo, int noteNo){
        this.noteDao.deleteSendNotes(mbNo, noteNo);
    }

    // 선택된 보관함 수신 쪽지 삭제처리
    public void removeSaveReceiveNotes(int mbNo, int noteNo){
        this.noteDao.deleteSaveReceiveNotes(mbNo, noteNo);
    }

    // 선택된 보관함 발신 쪽지 삭제처리
    public void removeSaveSendNotes(int mbNo, int noteNo) {
        this.noteDao.deleteSaveSendNotes(mbNo, noteNo);
    }

    // 선택된 받은쪽지 보관함 저장
    public void reviseSaveRetrieveNotes(int mbNo, int noteNo) {
        this.noteDao.updateSaveRetrieveNotes(mbNo, noteNo);
    }

    // 선택된 보낸쪽지 보관함 저장
    public void reviseSaveSendNotes(int mbNo, int noteNo) {
        this.noteDao.updateSaveSendNotes(mbNo, noteNo);
    }

    // 있는 회원 확인
    public int checkMbId(String mbId){
        return this.noteDao.checkMbId(mbId);
    }
}
