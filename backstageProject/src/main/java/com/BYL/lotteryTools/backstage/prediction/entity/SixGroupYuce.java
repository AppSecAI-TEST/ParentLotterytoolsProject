package com.BYL.lotteryTools.backstage.prediction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 任三精选六组预测实体
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月26日 下午4:42:52
 */
@Entity
public class SixGroupYuce {
	
	@Id
	@Column(name="ID")
	private int Id;

	@Column(name="PREDICTION_TYPE")
	private String predictionType;
	
	@Column(name="EXPERT_ID")
	private String expertId;
	
	@Column(name="ISSUE_NUMBER")
	private String issueNumber;
	
	
	@Column(name="IS_CHARGE")
	private String isCharge;
	
	@Column(name="MONEY")
	private String money;
	
	@Column(name="DROWN_NUMBER")
	private String drownNumber;
	
	@Column(name="EXPERT_LEVEL")
	private String expertLevel;
	
	//others
	
	@Column(name="WIN_RATE")
	private String winRate;

	@Column(name="ZJGROUPS")
	private String zjgroups;

	@Column(name="STATUS")
	private String status;
	
	@Column(name="GROUP1")
	private String group1;
	
	@Column(name="GROUP2")
	private String group2;
	
	@Column(name="GROUP3")
	private String group3;
	
	@Column(name="GROUP4")
	private String group4;
	
	@Column(name="GROUP5")
	private String group5;
	
	@Column(name="GROUP6")
	private String group6;
	
	@Column(name="EXPERT_NAME")
	private String expertName;
	
	

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getPredictionType() {
		return predictionType;
	}

	public void setPredictionType(String predictionType) {
		this.predictionType = predictionType;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(String isCharge) {
		this.isCharge = isCharge;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getDrownNumber() {
		return drownNumber;
	}

	public void setDrownNumber(String drownNumber) {
		this.drownNumber = drownNumber;
	}

	public String getExpertLevel() {
		return expertLevel;
	}

	public void setExpertLevel(String expertLevel) {
		this.expertLevel = expertLevel;
	}

	public String getWinRate() {
		return winRate;
	}

	public void setWinRate(String winRate) {
		this.winRate = winRate;
	}

	public String getZjgroups() {
		return zjgroups;
	}

	public void setZjgroups(String zjgroups) {
		this.zjgroups = zjgroups;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGroup1() {
		return group1;
	}

	public void setGroup1(String group1) {
		this.group1 = group1;
	}

	public String getGroup2() {
		return group2;
	}

	public void setGroup2(String group2) {
		this.group2 = group2;
	}

	public String getGroup3() {
		return group3;
	}

	public void setGroup3(String group3) {
		this.group3 = group3;
	}

	public String getGroup4() {
		return group4;
	}

	public void setGroup4(String group4) {
		this.group4 = group4;
	}

	public String getGroup5() {
		return group5;
	}

	public void setGroup5(String group5) {
		this.group5 = group5;
	}

	public String getGroup6() {
		return group6;
	}

	public void setGroup6(String group6) {
		this.group6 = group6;
	}

	
	
	


	
}
