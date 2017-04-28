package com.BYL.lotteryTools.backstage.prediction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 任胆杀预测实体
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月26日 下午4:42:52
 */
@Entity
public class RenDanmaYuce {
	
	@Id
	@Column(name="ID")
	private int Id;

	@Column(name="PREDICTION_TYPE")
	private String predictionType;
	
	@Column(name="EXPERT_ID")
	private String expertId;
	
	@Column(name="ISSUE_NUMBER")
	private String issueNumber;
	
	@Column(name="DANMA_ONE")
	private String danmaOne;
	
	@Column(name="SHAMA_ONE")
	private String shamaOne;
	
	@Column(name="IS_CHARGE")
	private String isCharge;
	
	@Column(name="MONEY")
	private String money;
	
	@Column(name="WIN_RATE_DUDAN")
	private String winRateDudan;
	
	
	@Column(name="DROWN_NUMBER")
	private String drownNumber;
	
	@Column(name="DUDAN_STATUS")
	private String dudanStatus;
	
	
	@Column(name="SHAMAYI_STATUS")
	private String shamayiStatus;
	
	@Column(name="WIN_RATE_SHAYI")
	private String winRateShayi;
	
	@Column(name="EXPERT_LEVEL")
	private String expertLevel;

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

	public String getDanmaOne() {
		return danmaOne;
	}

	public void setDanmaOne(String danmaOne) {
		this.danmaOne = danmaOne;
	}

	public String getShamaOne() {
		return shamaOne;
	}

	public void setShamaOne(String shamaOne) {
		this.shamaOne = shamaOne;
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

	public String getWinRateDudan() {
		return winRateDudan;
	}

	public void setWinRateDudan(String winRateDudan) {
		this.winRateDudan = winRateDudan;
	}

	public String getDrownNumber() {
		return drownNumber;
	}

	public void setDrownNumber(String drownNumber) {
		this.drownNumber = drownNumber;
	}

	public String getDudanStatus() {
		return dudanStatus;
	}

	public void setDudanStatus(String dudanStatus) {
		this.dudanStatus = dudanStatus;
	}

	public String getShamayiStatus() {
		return shamayiStatus;
	}

	public void setShamayiStatus(String shamayiStatus) {
		this.shamayiStatus = shamayiStatus;
	}

	public String getWinRateShayi() {
		return winRateShayi;
	}

	public void setWinRateShayi(String winRateShayi) {
		this.winRateShayi = winRateShayi;
	}

	public String getExpertLevel() {
		return expertLevel;
	}

	public void setExpertLevel(String expertLevel) {
		this.expertLevel = expertLevel;
	}

	
	
	
	
}
