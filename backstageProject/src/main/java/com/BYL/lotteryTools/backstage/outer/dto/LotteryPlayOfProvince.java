package com.BYL.lotteryTools.backstage.outer.dto;

public class LotteryPlayOfProvince 
{
	private String lotteryPlayId;//区域彩种所属的父级彩种id
	
	private String lotteryPlayName;//区域彩种名称
	
	private String lotteryType;//彩种类型id
	
	private String lotteryTypeName;//彩种类型：体彩，福彩
	
	private String numberLength;//号码池号码个数，3,11,12
	
	private String provinceCode;//区域彩种省份code
	
	private String lineCount;//每天开出的最大期数
	
	private String lotteryNumber;//开奖号码个数
	
	
	
	


	public String getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public String getLineCount() {
		return lineCount;
	}

	public void setLineCount(String lineCount) {
		this.lineCount = lineCount;
	}

	public String getLotteryPlayId() {
		return lotteryPlayId;
	}

	public void setLotteryPlayId(String lotteryPlayId) {
		this.lotteryPlayId = lotteryPlayId;
	}

	public String getLotteryPlayName() {
		return lotteryPlayName;
	}

	public void setLotteryPlayName(String lotteryPlayName) {
		this.lotteryPlayName = lotteryPlayName;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getLotteryTypeName() {
		return lotteryTypeName;
	}

	public void setLotteryTypeName(String lotteryTypeName) {
		this.lotteryTypeName = lotteryTypeName;
	}

	public String getNumberLength() {
		return numberLength;
	}

	public void setNumberLength(String numberLength) {
		this.numberLength = numberLength;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	
}
