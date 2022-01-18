package com.example.board.service;

import com.example.board.model.ReportVo;

import java.util.List;

public interface ReprotService {
    //신고 게시글/댓글 갯수 조회
    int retrieveReportListCount();

    //블라인드 신고 게시글/댓글 갯수 조회
    int retrieveBlindReportListCount();

    //블라인드 신고 게시글/댓글 목록 조회
    List<ReportVo> retrieveReportList(int startRow, int postSize);

    //신고 게시글/댓글 목록 조회
    List<ReportVo> retrieveBlindReportList(List<ReportVo> reports);

    //신고 게시글/댓글 등록
    void registerReport(ReportVo report);

    //블라인드
    void blindReport(int no, int onOff);
}
