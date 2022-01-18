package com.example.board.model;

import com.example.common.paging.CommonVo;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardVo extends CommonVo {
    private int boardNo;  // 게시판 번호
    private String groupName;  // 속할 게시판 그룹명

    @NotNull(message = "게시판 그룹을 선택하세요.")
    private Integer groupNo;  // 속할 게시판 그룹 번호

    @NotEmpty(message = "게시판 이름을 입력하세요.")
    private String title;  // 게시판 이름

    @NotNull(message = "게시판 유형을 선택하세요.")
    private String type;  //게시판 유형 (basic/review “자유게시판/리뷰게시판”)

    private Integer usePhoto; //사진 첨부 사용여부 (1 : on, 0: off)

    private Integer useVideo; // 동영상 첨부 사용여부 (1 : on, 0: off)

    private Integer useFile; // 파일 첨부 사용여부 (1 : on, 0: off)

    private Integer useComment;  // 댓글 사용여부 (1 : on, 0: off)
    private String creatDatetime; // 게시판 생성 일시
    private int postCount; //게시판 내 게시글 개수

    @Max(value = 5, message = "게시판 접근 등급을 5등급이하 선택하세요.")
    //@Size(min = 1, max = 5, )
    private int boGrade; //게시판 접근 등급

    //게시판 등록
    public BoardVo(int groupNo, int boardNo, String title, int boGrade, String type, int usePhoto, int useVideo, int useFile, int useComment, String creatDatetime) {
        super();
        this.groupNo = groupNo;
        this.boardNo = boardNo;
        this.title = title;
        this.boGrade = boGrade;
        this.type = type;
        this.usePhoto = usePhoto;
        this.useVideo = useVideo;
        this.useFile = useFile;
        this.useComment = useComment;
        this.creatDatetime = creatDatetime;
    }

    //게시판 수정
    public BoardVo(int groupNo, String title, int boGrade, String type, int usePhoto, int useVideo, int useFile, int useComment) {
        super();
        this.groupNo = groupNo;
        this.title = title;
        this.boGrade = boGrade;
        this.type = type;
        this.usePhoto = usePhoto;
        this.useVideo = useVideo;
        this.useFile = useFile;
        this.useComment = useComment;
    }

}
	 
 
