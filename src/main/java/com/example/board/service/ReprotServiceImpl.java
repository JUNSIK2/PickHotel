package com.example.board.service;

import com.example.board.model.ReportVo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ReprotServiceImpl implements ReprotService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public int retrieveReportListCount() {
        return 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public int retrieveBlindReportListCount() {
        return 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<ReportVo> retrieveReportList(int startRow, int postSize) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {RuntimeException.class})
    public List<ReportVo> retrieveBlindReportList(List<ReportVo> reports) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void registerReport(ReportVo report) {
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class})
    public void blindReport(int no, int onOff) {
    }
}
