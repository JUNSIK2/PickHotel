package com.example.vo;

public class ReviewVo {
	private int no;
	private int roomNo;
	private int rate_loc;
	private int rate_clean;
	private int rate_comu;
	private int rate_chip;
	private String visitDate;
	private String recommendPlace;
	private String notRecommendPerson;
	
	
	public ReviewVo() {
		super();
	}


	public ReviewVo(int no, int roomNo, int rate_loc, int rate_clean, int rate_comu, int rate_chip, String visitDate,
			String recommendPlace, String notRecommendPerson) {
		super();
		this.no = no;
		this.roomNo = roomNo;
		this.rate_loc = rate_loc;
		this.rate_clean = rate_clean;
		this.rate_comu = rate_comu;
		this.rate_chip = rate_chip;
		this.visitDate = visitDate;
		this.recommendPlace = recommendPlace;
		this.notRecommendPerson = notRecommendPerson;
	}


	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}


	public int getRoomNo() {
		return roomNo;
	}


	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}


	public int getRate_loc() {
		return rate_loc;
	}


	public void setRate_loc(int rate_loc) {
		this.rate_loc = rate_loc;
	}


	public int getRate_clean() {
		return rate_clean;
	}


	public void setRate_clean(int rate_clean) {
		this.rate_clean = rate_clean;
	}


	public int getRate_comu() {
		return rate_comu;
	}


	public void setRate_comu(int rate_comu) {
		this.rate_comu = rate_comu;
	}


	public int getRate_chip() {
		return rate_chip;
	}


	public void setRate_chip(int rate_chip) {
		this.rate_chip = rate_chip;
	}


	public String getVisitDate() {
		return visitDate;
	}


	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}


	public String getRecommendPlace() {
		return recommendPlace;
	}


	public void setRecommendPlace(String recommendPlace) {
		this.recommendPlace = recommendPlace;
	}


	public String getNotRecommendPerson() {
		return notRecommendPerson;
	}


	public void setNotRecommendPerson(String notRecommendPerson) {
		this.notRecommendPerson = notRecommendPerson;
	}
	
	
	
	
	
	


}
