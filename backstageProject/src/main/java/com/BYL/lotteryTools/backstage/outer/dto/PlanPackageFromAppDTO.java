package com.BYL.lotteryTools.backstage.outer.dto;




public class PlanPackageFromAppDTO 
{
	private int id;
    
	private String userId;
	
	private String provinceCode;
	
	private String provinceName;
	
	private String lotteryType;
	
	private String lotteryNumber;//开奖号码个数
	
	private String kjNumber;//开奖号
	
	private String stage;//期号
	
	private String serialNum;
	
	
	
	private String cost;
	
	private String count;
	
	private String lotteryPlayId;
	
	private String createTimeStr;
	
	
	
	

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getLotteryPlayId() {
		return lotteryPlayId;
	}

	public void setLotteryPlayId(String lotteryPlayId) {
		this.lotteryPlayId = lotteryPlayId;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public String getKjNumber() {
		return kjNumber;
	}

	public void setKjNumber(String kjNumber) {
		this.kjNumber = kjNumber;
	}
	
	
	
}
