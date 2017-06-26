package com.BYL.lotteryTools.backstage.lotteryManage.dto;



public class LotteryDiPinPlayDTO  
{
	
	
	private String id;
	
	private String planName;//方案名称
	
	private String startNumber;//号码选择范围的开始号码是几，11选5是1开始，时时彩是0开始，用来确认前台的补录方案号码以几开始
	
	private String endNumber;//号码选择范围的结束号码是几，11选5是11结束，时时彩是9结束，用来确认前台的补录方案号码以几结束
	
	private String numOrChar;//彩种玩法是数字还是汉字，因为有的彩种玩法是汉字的，用此字段来区分，（0：数字，1：汉字或其他）
	
	private String repeatNum;//开奖号码是否可以重复，0：不重复 1：重复
	
	private String otherPlan;//若为其他玩法类型，存储其方案
	
	private String otherNum;//其他需要计算的字段和方法
	
	private String morePartKj;//多部分开奖，分红蓝号码
	
	
	private String moreStartNumber;//第二部分范围开始号码
	
	private String moreEndNumber;//第二部分范围结束号码
	
	private String createTimeStr;
	
	
	
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getRepeatNum() {
		return repeatNum;
	}

	public void setRepeatNum(String repeatNum) {
		this.repeatNum = repeatNum;
	}

	

	
	
	public String getMorePartKj() {
		return morePartKj;
	}

	public void setMorePartKj(String morePartKj) {
		this.morePartKj = morePartKj;
	}

	public String getMoreStartNumber() {
		return moreStartNumber;
	}

	public void setMoreStartNumber(String moreStartNumber) {
		this.moreStartNumber = moreStartNumber;
	}

	public String getMoreEndNumber() {
		return moreEndNumber;
	}

	public void setMoreEndNumber(String moreEndNumber) {
		this.moreEndNumber = moreEndNumber;
	}


	public String getPlanName() {
		return planName;
	}

	public String getOtherNum() {
		return otherNum;
	}

	public void setOtherNum(String otherNum) {
		this.otherNum = otherNum;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(String startNumber) {
		this.startNumber = startNumber;
	}

	public String getEndNumber() {
		return endNumber;
	}

	public void setEndNumber(String endNumber) {
		this.endNumber = endNumber;
	}

	public String getNumOrChar() {
		return numOrChar;
	}

	public void setNumOrChar(String numOrChar) {
		this.numOrChar = numOrChar;
	}

	public String getOtherPlan() {
		return otherPlan;
	}

	public void setOtherPlan(String otherPlan) {
		this.otherPlan = otherPlan;
	}

	
	
	
	
}
