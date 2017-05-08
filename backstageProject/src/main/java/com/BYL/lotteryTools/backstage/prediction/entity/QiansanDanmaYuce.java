package com.BYL.lotteryTools.backstage.prediction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 前三胆码预测实体
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月26日 下午4:42:52
 */
@Entity
public class QiansanDanmaYuce {
	
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
	private String danmaone;
	
	@Column(name="DANMA_TWO")
	private String danmatwo;
	
	@Column(name="SHAMA_ONE")
	private String shamaone;
	
	@Column(name="IS_CHARGE")
	private String isCharge;
	
	@Column(name="MONEY")
	private String money;
	
	@Column(name="WIN_RATE_DUDAN")
	private String winRateDudan;
	
	
	@Column(name="WIN_RATE_SHUANGDAN")
	private String winRateShuangdan;
	
	@Column(name="WIN_RATE_DANMA")
	private String winRateDanma;
	
	@Column(name="DROWN_NUMBER")
	private String drownNumber;
	
	@Column(name="DUDAN_STATUS")
	private String dudanStatus;
	
	@Column(name="SHUANGDAN_STATUS")
	private String shuangdanStatus;
	
	@Column(name="DANMA_STATUS")
	private String danmaStatus;
	
	@Column(name="SHAMAER_STATUS")
	private String shamaerStatus;
	
	@Column(name="SHAMASAN_STATUS")
	private String shamasanStatus;
	
	@Column(name="WIN_RATE_SHAER")
	private String winRateShaer;
	
	@Column(name="WIN_RATE_SHASAN")
	private String winRateShasan;
	
	@Column(name="EXPERT_LEVEL")
	private String expertLevel;
	
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

	public String getDanmaone() {
		return danmaone;
	}

	public void setDanmaone(String danmaone) {
		this.danmaone = danmaone;
	}

	public String getDanmatwo() {
		return danmatwo;
	}

	public void setDanmatwo(String danmatwo) {
		this.danmatwo = danmatwo;
	}

	public String getShamaone() {
		return shamaone;
	}

	public void setShamaone(String shamaone) {
		this.shamaone = shamaone;
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

	public String getWinRateShuangdan() {
		return winRateShuangdan;
	}

	public void setWinRateShuangdan(String winRateShuangdan) {
		this.winRateShuangdan = winRateShuangdan;
	}

	public String getWinRateDanma() {
		return winRateDanma;
	}

	public void setWinRateDanma(String winRateDanma) {
		this.winRateDanma = winRateDanma;
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

	public String getShuangdanStatus() {
		return shuangdanStatus;
	}

	public void setShuangdanStatus(String shuangdanStatus) {
		this.shuangdanStatus = shuangdanStatus;
	}

	public String getDanmaStatus() {
		return danmaStatus;
	}

	public void setDanmaStatus(String danmaStatus) {
		this.danmaStatus = danmaStatus;
	}

	public String getShamaerStatus() {
		return shamaerStatus;
	}

	public void setShamaerStatus(String shamaerStatus) {
		this.shamaerStatus = shamaerStatus;
	}

	public String getShamasanStatus() {
		return shamasanStatus;
	}

	public void setShamasanStatus(String shamasanStatus) {
		this.shamasanStatus = shamasanStatus;
	}

	public String getWinRateShaer() {
		return winRateShaer;
	}

	public void setWinRateShaer(String winRateShaer) {
		this.winRateShaer = winRateShaer;
	}

	public String getWinRateShasan() {
		return winRateShasan;
	}

	public void setWinRateShasan(String winRateShasan) {
		this.winRateShasan = winRateShasan;
	}

	public String getExpertLevel() {
		return expertLevel;
	}

	public void setExpertLevel(String expertLevel) {
		this.expertLevel = expertLevel;
	}
	
	

	
	
}
