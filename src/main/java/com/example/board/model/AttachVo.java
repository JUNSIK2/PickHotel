package com.example.board.model;

public class AttachVo {
    private int no;
    private int postNo;
    private String originalFileName;
    private String systemFileName;
    private int fileSize;
    private int fileType;

    public AttachVo() {
        super();
    }


    public AttachVo(String originalFileName, String systemFileName, int fileSize) {
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
        this.fileSize = fileSize;
    }

    public AttachVo(int no, int postNo, String originalFileName, String systemFileName, int fileSize, int fileType) {
        super();
        this.no = no;
        this.postNo = postNo;
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    public AttachVo(int postNo, String originalFileName, String systemFileName, int fileSize, int fileType) {

        this.postNo = postNo;
        this.originalFileName = originalFileName;
        this.systemFileName = systemFileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getPostNo() {
        return postNo;
    }

    public void setPostNo(int postNo) {
        this.postNo = postNo;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getSystemFileName() {
        return systemFileName;
    }

    public void setSystemFileName(String systemFileName) {
        this.systemFileName = systemFileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

}
