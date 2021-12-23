package com.example.vo;

public class RoomVo {
	private int no;
	private int roomCode;
	private String roomAddress;
	private String originalFileName;
	private String systemFileName;
	
	
	public RoomVo() {
		super();
	}


	public RoomVo(int no, int roomCode, String roomAddress, String originalFileName, String systemFileName) {
		super();
		this.no = no;
		this.roomCode = roomCode;
		this.roomAddress = roomAddress;
		this.originalFileName = originalFileName;
		this.systemFileName = systemFileName;
	}


	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}


	public int getRoomCode() {
		return roomCode;
	}


	public void setRoomCode(int roomCode) {
		this.roomCode = roomCode;
	}


	public String getRoomAddress() {
		return roomAddress;
	}


	public void setRoomAddress(String roomAddress) {
		this.roomAddress = roomAddress;
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
	
}
