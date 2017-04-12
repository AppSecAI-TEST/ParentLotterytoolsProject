package com.BYL.lotteryTools.backstage.prediction.dto;


public class BasePredictionTypeDTO {

	private String id;
	
	
	private String originDataSize;//源码数据量要求
	
	private String nPlan;//当前预测是几期计划（n期计划就获取源码后几期流码，n>=1,当前n=1时则是当期预测）
	
	private String flowDataSize;//流码数据量要求是根据源码需求和n期计划计算出的，根据源码和N期计划计算出的最大结果

	
	private String basePredictionName;//基础预测类型名称
	
	private String methodName;//预测方法池中对应的预测方法名称
	
	private String createTime;//创建时间
	
	private String originDataRuleId;
	
	private String creator;//创建人
	
	
	private String yuceFenlei;//预测分类
	
	

	
	

	public String getYuceFenlei() {
		return yuceFenlei;
	}

	public void setYuceFenlei(String yuceFenlei) {
		this.yuceFenlei = yuceFenlei;
	}

	public String getOriginDataRuleId() {
		return originDataRuleId;
	}

	public void setOriginDataRuleId(String originDataRuleId) {
		this.originDataRuleId = originDataRuleId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getOriginDataSize() {
		return originDataSize;
	}

	public void setOriginDataSize(String originDataSize) {
		this.originDataSize = originDataSize;
	}

	public String getnPlan() {
		return nPlan;
	}

	public void setnPlan(String nPlan) {
		this.nPlan = nPlan;
	}

	public String getFlowDataSize() {
		return flowDataSize;
	}

	public void setFlowDataSize(String flowDataSize) {
		this.flowDataSize = flowDataSize;
	}

	public String getBasePredictionName() {
		return basePredictionName;
	}

	public void setBasePredictionName(String basePredictionName) {
		this.basePredictionName = basePredictionName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	
	
	
}
