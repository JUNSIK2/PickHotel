package com.example.board.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentVo {
    private int comNo;
    private int postNo;
    private int memNo;
    private String content;
    private int order;
    private int comClass;
    private int parents;
    private int parentMemNo;
    private String parentMemNick;
    private int isblind;
    private String writeday;
    private String memNick;

    private int isDelect;

    //    삭제 댓글 목록 출력시
    public CommentVo(int isDelect) {
        super();
        this.isDelect = isDelect;
    }

    //    댓글 블라인드 처리시 사용
    public CommentVo(int comNo, int isblind) {
        super();
        this.comNo = comNo;
        this.isblind = isblind;
    }

    //    댓글 수정시 사용
    public CommentVo(int comNo, String content) {
        super();
        this.comNo = comNo;
        this.content = content;
    }

    //	댓글 작성시 사용
    public CommentVo(int postNo, int memNo, String content, int order, int parents) {
        super();
        this.postNo = postNo;
        this.memNo = memNo;
        this.content = content;
        this.order = order;
        this.parents = parents;
    }

    //	댓글 목록 조회시 사용
    public CommentVo(int postNo, int memNo, String content, int order, int parents, int isblind, String writeday) {
        super();
        this.postNo = postNo;
        this.memNo = memNo;
        this.content = content;
        this.order = order;
        this.parents = parents;
        this.isblind = isblind;
        this.writeday = writeday;
    }

}
