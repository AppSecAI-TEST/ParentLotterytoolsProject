package com.BYL.lotteryTools.backstage.lotteryGroup.dto;

import org.springframework.web.multipart.MultipartFile;


public class LotteryGroupDTO {

	private String id;//群id，群融云id
	
	private String name;//群名称
	
	private String groupLevel;//群等级，与群等级表关联
	
	private String groupRobotID;//机器人id（与用户表关联）,区域彩种高频一个机器人
	
	private String introduction;//群简介
	
	private String touXiang;//头像图片
	
	private MultipartFile touXiangImg;//头像图片
	
	private String ownerId;//群主id
	
	private String lotteryType;//群分类，1：体彩，2：福彩 3：竞彩
	
	private String province;//省
	
	private String city;//市
	
	private String upLevel;//升级
	
	
	
	

	public String getUpLevel() {
		return upLevel;
	}

	public void setUpLevel(String upLevel) {
		this.upLevel = upLevel;
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

	public MultipartFile getTouXiangImg() {
		return touXiangImg;
	}

	public void setTouXiangImg(MultipartFile touXiangImg) {
		this.touXiangImg = touXiangImg;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(String groupLevel) {
		this.groupLevel = groupLevel;
	}

	public String getGroupRobotID() {
		return groupRobotID;
	}

	public void setGroupRobotID(String groupRobotID) {
		this.groupRobotID = groupRobotID;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getTouXiang() {
		return touXiang;
	}

	public void setTouXiang(String touXiang) {
		this.touXiang = touXiang;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	
}
