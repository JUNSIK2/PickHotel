package com.example.vo;

import lombok.Data;

@Data
public class BoardVo {
	 private int boardNo;  // 게시판 번호
	 private String groupName;  // 속할 게시판 그룹명
	 private int groupNo;  // 속할 게시판 그룹 번호
	 private String title ;  // 게시판 이름
	 private String type;  //게시판 유형 (basic/review “자유게시판/리뷰게시판”)
	 private int boGrade ; //게시판 접근 등급
	 private int usePhoto; //사진 첨부 사용여부 (1 : on, 0: off)
	 private int useVideo; // 동영상 첨부 사용여부 (1 : on, 0: off)
	 private int useFile; // 파일 첨부 사용여부 (1 : on, 0: off)
	 private int useComment;  // 댓글 첨부 사용여부 (1 : on, 0: off)
	 private String creatDatetime; // 게시판 생성 일시
	 private int postCount; //게시판 내 게시글 개수

	 public BoardVo() {
		 super();
	 }
	 
	 //게시판 등록
	 public BoardVo(int groupNo, int boardNo, String title, int boGrade, String type, int usePhoto, int useVideo, int useFile, int useComment,  String creatDatetime) {
		 super();
		 this.groupNo =groupNo;
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
	 public BoardVo(int groupNo, String title, int boGrade, String type, int usePhoto, int useVideo, int useFile, int useComment){
		 super();
		 this.groupNo =groupNo;
		 this.title = title;
		 this.boGrade = boGrade;
		 this.type = type;
		 this.usePhoto = usePhoto;
		 this.useVideo = useVideo;
		 this.useFile = useFile;
		 this.useComment = useComment;
	 }
	 
}
	 
 
