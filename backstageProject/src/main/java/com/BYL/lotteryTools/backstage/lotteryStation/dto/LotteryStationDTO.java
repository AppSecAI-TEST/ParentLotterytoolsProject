package com.BYL.lotteryTools.backstage.lotteryStation.dto;


public class LotteryStationDTO 
{
	private String id;
	
	private String stationOwner;//站主
	
	
	private String telephone;//站主手机
	
	private String stationNumber;//站点号
	
	private String code;//登录名
	
	private String password;//密码
	
	private String province;//省
	
	private String city;//市
	
	private String country;//区
	
	private String coordinate;//坐标
	
	private String address;//详细地址
	
	private String stationInterview;//彩站介绍
	
	private String openDoorTimeStr;//开始营业时间
	
	private String closeDoorTimeStr;//停止营业时间
	
	private String createTimeStr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStationOwner() {
		return stationOwner;
	}

	public void setStationOwner(String stationOwner) {
		this.stationOwner = stationOwner;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStationInterview() {
		return stationInterview;
	}

	public void setStationInterview(String stationInterview) {
		this.stationInterview = stationInterview;
	}

	public String getOpenDoorTimeStr() {
		return openDoorTimeStr;
	}

	public void setOpenDoorTimeStr(String openDoorTimeStr) {
		this.openDoorTimeStr = openDoorTimeStr;
	}

	public String getCloseDoorTimeStr() {
		return closeDoorTimeStr;
	}

	public void setCloseDoorTimeStr(String closeDoorTimeStr) {
		this.closeDoorTimeStr = closeDoorTimeStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	
}
