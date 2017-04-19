package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public class LotterybuyerOrExpertDTO {

	private String id;
	
	private String code;//用户名
	
	private String name;//姓名
	
	private String touXiang;//头像
	
	private String password;//密码
	
	private String telephone;
	
	private String isPhone;//是否为手机用户（0：手机用户 1：其他用户）
	
	private String isStationOwner;//是否为站主1：是0:不是
	
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
	
	private String yanzhengma;//验证码
	
	private String idNumberFrontImgId;//身份证正面图片id
	
	private String idNumberBackImgId;//身份证反面图片id
	
	private MultipartFile idNumberFrontImg;//身份证正面图片
	
	private MultipartFile idNumberBackImg;//身份证背面图片
	
	private String token;//用户融云token
	
	private String cailiaoName;//彩聊名
	
	private String sex;//性别
	
	private String signature;//个性签名
	
	
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCailiaoName() {
		return cailiaoName;
	}

	public void setCailiaoName(String cailiaoName) {
		this.cailiaoName = cailiaoName;
	}

	public String getTouXiang() {
		return touXiang;
	}

	public void setTouXiang(String touXiang) {
		this.touXiang = touXiang;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIdNumberFrontImgId() {
		return idNumberFrontImgId;
	}

	public void setIdNumberFrontImgId(String idNumberFrontImgId) {
		this.idNumberFrontImgId = idNumberFrontImgId;
	}

	public String getIdNumberBackImgId() {
		return idNumberBackImgId;
	}

	public void setIdNumberBackImgId(String idNumberBackImgId) {
		this.idNumberBackImgId = idNumberBackImgId;
	}

	public MultipartFile getIdNumberFrontImg() {
		return idNumberFrontImg;
	}

	public void setIdNumberFrontImg(MultipartFile idNumberFrontImg) {
		this.idNumberFrontImg = idNumberFrontImg;
	}

	public MultipartFile getIdNumberBackImg() {
		return idNumberBackImg;
	}

	public void setIdNumberBackImg(MultipartFile idNumberBackImg) {
		this.idNumberBackImg = idNumberBackImg;
	}

	public String getYanzhengma() {
		return yanzhengma;
	}

	public void setYanzhengma(String yanzhengma) {
		this.yanzhengma = yanzhengma;
	}

	public String getIsStationOwner() {
		return isStationOwner;
	}

	public void setIsStationOwner(String isStationOwner) {
		this.isStationOwner = isStationOwner;
	}

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
