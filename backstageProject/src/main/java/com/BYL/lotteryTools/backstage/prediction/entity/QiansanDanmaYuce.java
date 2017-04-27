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
	private String PREDICTION_TYPE;
	
	@Column(name="EXPERT_ID")
	private String EXPERT_ID;
	
	@Column(name="ISSUE_NUMBER")
	private String ISSUE_NUMBER;
	
	@Column(name="DANMA_ONE")
	private String DANMA_ONE;
	
	@Column(name="DANMA_TWO")
	private String DANMA_TWO;
	
	@Column(name="SHAMA_ONE")
	private String SHAMA_ONE;
	
	@Column(name="IS_CHARGE")
	private String IS_CHARGE;
	
	@Column(name="MONEY")
	private String MONEY;
	
	@Column(name="WIN_RATE_DUDAN")
	private String WIN_RATE_DUDAN;
	
	
	@Column(name="WIN_RATE_SHUANGDAN")
	private String WIN_RATE_SHUANGDAN;
	
	@Column(name="WIN_RATE_DANMA")
	private String WIN_RATE_DANMA;
	
	@Column(name="DROWN_NUMBER")
	private String DROWN_NUMBER;
	
	@Column(name="DUDAN_STATUS")
	private String DUDAN_STATUS;
	
	@Column(name="SHUANGDAN_STATUS")
	private String SHUANGDAN_STATUS;
	
	@Column(name="DANMA_STATUS")
	private String DANMA_STATUS;
	
	@Column(name="SHAMAER_STATUS")
	private String SHAMAER_STATUS;
	
	@Column(name="SHAMASAN_STATUS")
	private String SHAMASAN_STATUS;
	
	@Column(name="WIN_RATE_SHAER")
	private String WIN_RATE_SHAER;
	
	@Column(name="WIN_RATE_SHASAN")
	private String WIN_RATE_SHASAN;
	
	@Column(name="EXPERT_LEVEL")
	private String EXPERT_LEVEL;
	
	

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getPREDICTION_TYPE() {
		return PREDICTION_TYPE;
	}

	public void setPREDICTION_TYPE(String pREDICTION_TYPE) {
		PREDICTION_TYPE = pREDICTION_TYPE;
	}

	public String getEXPERT_ID() {
		return EXPERT_ID;
	}

	public void setEXPERT_ID(String eXPERT_ID) {
		EXPERT_ID = eXPERT_ID;
	}

	public String getISSUE_NUMBER() {
		return ISSUE_NUMBER;
	}

	public void setISSUE_NUMBER(String iSSUE_NUMBER) {
		ISSUE_NUMBER = iSSUE_NUMBER;
	}

	public String getDANMA_ONE() {
		return DANMA_ONE;
	}

	public void setDANMA_ONE(String dANMA_ONE) {
		DANMA_ONE = dANMA_ONE;
	}

	public String getDANMA_TWO() {
		return DANMA_TWO;
	}

	public void setDANMA_TWO(String dANMA_TWO) {
		DANMA_TWO = dANMA_TWO;
	}

	public String getSHAMA_ONE() {
		return SHAMA_ONE;
	}

	public void setSHAMA_ONE(String sHAMA_ONE) {
		SHAMA_ONE = sHAMA_ONE;
	}

	public String getIS_CHARGE() {
		return IS_CHARGE;
	}

	public void setIS_CHARGE(String iS_CHARGE) {
		IS_CHARGE = iS_CHARGE;
	}

	public String getMONEY() {
		return MONEY;
	}

	public void setMONEY(String mONEY) {
		MONEY = mONEY;
	}

	public String getWIN_RATE_DUDAN() {
		return WIN_RATE_DUDAN;
	}

	public void setWIN_RATE_DUDAN(String wIN_RATE_DUDAN) {
		WIN_RATE_DUDAN = wIN_RATE_DUDAN;
	}

	public String getWIN_RATE_SHUANGDAN() {
		return WIN_RATE_SHUANGDAN;
	}

	public void setWIN_RATE_SHUANGDAN(String wIN_RATE_SHUANGDAN) {
		WIN_RATE_SHUANGDAN = wIN_RATE_SHUANGDAN;
	}

	public String getWIN_RATE_DANMA() {
		return WIN_RATE_DANMA;
	}

	public void setWIN_RATE_DANMA(String wIN_RATE_DANMA) {
		WIN_RATE_DANMA = wIN_RATE_DANMA;
	}

	public String getDROWN_NUMBER() {
		return DROWN_NUMBER;
	}

	public void setDROWN_NUMBER(String dROWN_NUMBER) {
		DROWN_NUMBER = dROWN_NUMBER;
	}

	public String getDUDAN_STATUS() {
		return DUDAN_STATUS;
	}

	public void setDUDAN_STATUS(String dUDAN_STATUS) {
		DUDAN_STATUS = dUDAN_STATUS;
	}

	public String getSHUANGDAN_STATUS() {
		return SHUANGDAN_STATUS;
	}

	public void setSHUANGDAN_STATUS(String sHUANGDAN_STATUS) {
		SHUANGDAN_STATUS = sHUANGDAN_STATUS;
	}

	public String getDANMA_STATUS() {
		return DANMA_STATUS;
	}

	public void setDANMA_STATUS(String dANMA_STATUS) {
		DANMA_STATUS = dANMA_STATUS;
	}

	public String getSHAMAER_STATUS() {
		return SHAMAER_STATUS;
	}

	public void setSHAMAER_STATUS(String sHAMAER_STATUS) {
		SHAMAER_STATUS = sHAMAER_STATUS;
	}

	public String getSHAMASAN_STATUS() {
		return SHAMASAN_STATUS;
	}

	public void setSHAMASAN_STATUS(String sHAMASAN_STATUS) {
		SHAMASAN_STATUS = sHAMASAN_STATUS;
	}

	public String getWIN_RATE_SHAER() {
		return WIN_RATE_SHAER;
	}

	public void setWIN_RATE_SHAER(String wIN_RATE_SHAER) {
		WIN_RATE_SHAER = wIN_RATE_SHAER;
	}

	public String getWIN_RATE_SHASAN() {
		return WIN_RATE_SHASAN;
	}

	public void setWIN_RATE_SHASAN(String wIN_RATE_SHASAN) {
		WIN_RATE_SHASAN = wIN_RATE_SHASAN;
	}

	public String getEXPERT_LEVEL() {
		return EXPERT_LEVEL;
	}

	public void setEXPERT_LEVEL(String eXPERT_LEVEL) {
		EXPERT_LEVEL = eXPERT_LEVEL;
	}
	
	
	
}
