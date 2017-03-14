package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto;

import java.math.BigDecimal;

public class LotterybuyerOrExpertDTO {

	private String id;
	
	private String code;//用户名
	
	private String name;//姓名
	
	private String password;//密码
	
	private String telephone;
	
	private String isPhone;//是否为手机用户（0：手机用户 1：其他用户）
	
	private String provinceCode;
	
	private String cityCode;
	
	private String regionCode;
	
	private String address;
	
	private String coordinate;//坐标
	
	private String isLotteryBuyer;//是否为彩民 1：彩民 0：非彩民
	
	private String isExpert;//是否为彩民 1：彩民 0：非彩民
	
	private BigDecimal handSel;//彩金余额
	
	private BigDecimal colorCoins;//彩币余额

	private String createTimeStr;
	
	
	

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIsPhone() {
		return isPhone;
	}

	public void setIsPhone(String isPhone) {
		this.isPhone = isPhone;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getIsLotteryBuyer() {
		return isLotteryBuyer;
	}

	public void setIsLotteryBuyer(String isLotteryBuyer) {
		this.isLotteryBuyer = isLotteryBuyer;
	}

	public String getIsExpert() {
		return isExpert;
	}

	public void setIsExpert(String isExpert) {
		this.isExpert = isExpert;
	}

	public BigDecimal getHandSel() {
		return handSel;
	}

	public void setHandSel(BigDecimal handSel) {
		this.handSel = handSel;
	}

	public BigDecimal getColorCoins() {
		return colorCoins;
	}

	public void setColorCoins(BigDecimal colorCoins) {
		this.colorCoins = colorCoins;
	}
	
	
}
