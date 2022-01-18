package com.example.board.service;

import com.example.board.model.AttachVo;
import com.example.board.persistent.AttachDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("attachService")
public class AttachService {
    @Autowired
    private AttachDao attachDao;

    //파일을 등록하다.
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void registerAttach(AttachVo attach) {
        this.attachDao.insertPostAttach(attach);
    }

    //게시글 번호로 파일목록을 조회한다.
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<AttachVo> retrievePostAttach(int poNo) {
        return this.attachDao.selectPostAttach(poNo);
    }

    //파일을 조회한다.
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public AttachVo retrievePostAttachByAtNo(int attaNo) {
        return this.attachDao.selectPostAttachByAtNo(attaNo);
    }

    //파일을 삭제하다.
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void removePostAttach(int attNo) {
        this.attachDao.deletePostAttach(attNo);
    }

    //게시글 삭제에 의해 파일을 삭제한다.
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void removeAttachByPost(int poNo) {
        this.attachDao.deleteAttachbyPost(poNo);
    }


}