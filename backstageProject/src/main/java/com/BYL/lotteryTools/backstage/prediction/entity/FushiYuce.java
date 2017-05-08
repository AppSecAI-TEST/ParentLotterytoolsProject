package com.BYL.lotteryTools.backstage.prediction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 复式预测实体
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月26日 下午4:42:52
 */
@Entity
public class FushiYuce {
	
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

	@Column(name="FUSHI")
	private String fushi;

	@Column(name="STATUS")
	private String status;
	
	@Column(name="YUCE_ISSUE_START")
	private String yuceIssueStart;
	
	@Column(name="YUCE_ISSUE_STOP")
	private String yuceIssueStop;
	
	@Column(name="CYCLE")
	private String cycle;
	
	@Column(name="ZJLEVEL")
	private String zjLevel;//只有乐选四期复式有用
	
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

	public String getFushi() {
		return fushi;
	}

	public void setFushi(String fushi) {
		this.fushi = fushi;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getYuceIssueStart() {
		return yuceIssueStart;
	}

	public void setYuceIssueStart(String yuceIssueStart) {
		this.yuceIssueStart = yuceIssueStart;
	}

	public String getYuceIssueStop() {
		return yuceIssueStop;
	}

	public void setYuceIssueStop(String yuceIssueStop) {
		this.yuceIssueStop = yuceIssueStop;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getZjLevel() {
		return zjLevel;
	}

	public void setZjLevel(String zjLevel) {
		this.zjLevel = zjLevel;
	}
	
	


	
	
}
