package com.BYL.lotteryTools.backstage.prediction.dto;



public class OriginDataRuleDTO {

	private String id;
	
	private String ruleName;//预测类型名称
	
	private String type;//:0：当前开奖号码1:关联期号方式2.期号方式
	
	private String locationOrContain;//0:定位1：包含
	
	
	private String ciLocationNumber;//需要定位的位置，若要取第一位则是1，若要取第一位和第二位开奖号码，则要填写1,2
	
	
	private String ciRuleFiled;//筛选条件字段，同彩种维护中需要计算字段的维护方式，筛选的字段用","间隔
	
	private String liLocationNumber;//上期需要定位的位置，若要取第一位则是1，若要取第一位和第二位开奖号码，则要填写1,2
	
	
	private String liRuleFiled;//上期筛选条件字段，同彩种维护中需要计算字段的维护方式，筛选的字段用","间隔
	
	
	private String ruleFiled;//筛选条件字段，同彩种维护中需要计算字段的维护方式，筛选的字段用","间隔
	
	private String cycle;//周期，间隔周期获取同样期号的开奖数据
	
	private String createTime;
	
	

	public String getLocationOrContain() {
		return locationOrContain;
	}

	public void setLocationOrContain(String locationOrContain) {
		this.locationOrContain = locationOrContain;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getCiLocationNumber() {
		return ciLocationNumber;
	}

	public void setCiLocationNumber(String ciLocationNumber) {
		this.ciLocationNumber = ciLocationNumber;
	}

	public String getCiRuleFiled() {
		return ciRuleFiled;
	}

	public void setCiRuleFiled(String ciRuleFiled) {
		this.ciRuleFiled = ciRuleFiled;
	}

	public String getLiLocationNumber() {
		return liLocationNumber;
	}

	public void setLiLocationNumber(String liLocationNumber) {
		this.liLocationNumber = liLocationNumber;
	}

	public String getLiRuleFiled() {
		return liRuleFiled;
	}

	public void setLiRuleFiled(String liRuleFiled) {
		this.liRuleFiled = liRuleFiled;
	}

	public String getRuleFiled() {
		return ruleFiled;
	}

	public void setRuleFiled(String ruleFiled) {
		this.ruleFiled = ruleFiled;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	
	
}
