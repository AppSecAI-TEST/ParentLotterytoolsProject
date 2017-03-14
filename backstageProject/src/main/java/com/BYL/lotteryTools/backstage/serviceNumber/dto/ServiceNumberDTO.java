package com.BYL.lotteryTools.backstage.serviceNumber.dto;


public class ServiceNumberDTO {

	private String id;
	
	private String serviceNumberName;//服务号名称
	
	private String code;//登录名
	
	private String password;//密码
	
	private String province;//省
	
	private String city;//市
	
	private String country;//区
	
	private String coordinate;//坐标
	
	private String address;//详细地址

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceNumberName() {
		return serviceNumberName;
	}

	public void setServiceNumberName(String serviceNumberName) {
		this.serviceNumberName = serviceNumberName;
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
	
	
}
