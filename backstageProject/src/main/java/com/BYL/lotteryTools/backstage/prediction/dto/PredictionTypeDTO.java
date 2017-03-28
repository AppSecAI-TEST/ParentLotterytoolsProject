package com.BYL.lotteryTools.backstage.prediction.dto;


public class PredictionTypeDTO {

	private String id;
	
	private String predictionName;//预测类型名称
	
	private String predictionCode;//预测类型编码
	
	private String predictionTable;//预测方案表（一个预测类型对应一张预测方案表，例如11选5的预测类型1则对应11选5预测类型1方案表的表名,每个省份的预测方案结果表各自独立）
	
	
	private Integer orderRule;//规定取多少条数据作为计算中奖率的基础分母
	
	
	
	private String liangmaTableName;//两码基础表表名
	
	private String sanmaTableName;//三码基础表表名
	
	private String simaTableName;//四码基础表表名
	
	private String liumaTableName;//六码基础表表名
	
	private String createTime;
	
	private String lotteryPlayId;
	
	private String basePredictionTypeId;
	
	private String lotteryPlayName;//区域彩种名称
	
	private String basePredictionTypeName;//基础预测类型名称
	
	
	
	
	

	public String getLotteryPlayName() {
		return lotteryPlayName;
	}

	public void setLotteryPlayName(String lotteryPlayName) {
		this.lotteryPlayName = lotteryPlayName;
	}

	public String getBasePredictionTypeName() {
		return basePredictionTypeName;
	}

	public void setBasePredictionTypeName(String basePredictionTypeName) {
		this.basePredictionTypeName = basePredictionTypeName;
	}

	public String getLotteryPlayId() {
		return lotteryPlayId;
	}

	public void setLotteryPlayId(String lotteryPlayId) {
		this.lotteryPlayId = lotteryPlayId;
	}

	public String getBasePredictionTypeId() {
		return basePredictionTypeId;
	}

	public void setBasePredictionTypeId(String basePredictionTypeId) {
		this.basePredictionTypeId = basePredictionTypeId;
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

	public String getPredictionName() {
		return predictionName;
	}

	public void setPredictionName(String predictionName) {
		this.predictionName = predictionName;
	}

	public String getPredictionCode() {
		return predictionCode;
	}

	public void setPredictionCode(String predictionCode) {
		this.predictionCode = predictionCode;
	}

	public String getPredictionTable() {
		return predictionTable;
	}

	public void setPredictionTable(String predictionTable) {
		this.predictionTable = predictionTable;
	}

	public Integer getOrderRule() {
		return orderRule;
	}

	public void setOrderRule(Integer orderRule) {
		this.orderRule = orderRule;
	}

	public String getLiangmaTableName() {
		return liangmaTableName;
	}

	public void setLiangmaTableName(String liangmaTableName) {
		this.liangmaTableName = liangmaTableName;
	}

	public String getSanmaTableName() {
		return sanmaTableName;
	}

	public void setSanmaTableName(String sanmaTableName) {
		this.sanmaTableName = sanmaTableName;
	}

	public String getSimaTableName() {
		return simaTableName;
	}

	public void setSimaTableName(String simaTableName) {
		this.simaTableName = simaTableName;
	}

	public String getLiumaTableName() {
		return liumaTableName;
	}

	public void setLiumaTableName(String liumaTableName) {
		this.liumaTableName = liumaTableName;
	}
	
	
}
