package com.BYL.lotteryTools.backstage.outer.dto;



public class PlanPackageFromAppDTO 
{
	private int id;
    
	private String userId;
	
	private String provinceCode;
	
	private String lotteryType;
	
	private String lotteryNumber;//开奖号码个数
	
	private String kjNumber;//开奖号

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public String getKjNumber() {
		return kjNumber;
	}

	public void setKjNumber(String kjNumber) {
		this.kjNumber = kjNumber;
	}
	
	
	
}
