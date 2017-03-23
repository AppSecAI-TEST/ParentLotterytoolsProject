package com.BYL.lotteryTools.backstage.prediction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 预测类型
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月27日 下午3:47:32
 */
@Entity
@Table(name="T_LT_PREDICTION_TYPE")
public class PredictionType extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="PREDICTION_NAME", length=45)
	private String predictionName;//预测类型名称
	
	@Column(name="PREDICTION_CODE", length=45)
	private String predictionCode;//预测类型编码
	
	@Column(name="PREDICTION_TABLE", length=45)
	private String predictionTable;//预测方案表（一个预测类型对应一张预测方案表，例如11选5的预测类型1则对应11选5预测类型1方案表的表名,每个省份的预测方案结果表各自独立）
	
	//多个预测类型可以对应一个彩种
	@ManyToOne  
    @JoinColumn(name = "LOTTERYPLAY_ID", referencedColumnName = "id")
	private LotteryPlay lotteryPlay;
	
	//排序规则---中奖率计算规则表分母
	@Column(name="ORDER_RULE", length=10)
	private Integer orderRule;//规定取多少条数据作为计算中奖率的基础分母
	
	@ManyToOne  
    @JoinColumn(name = "BASE_PREDICTION_TYPE_ID", referencedColumnName = "id")
	private BasePredictionType basePredictionType;
	
	
	@Column(name="LIANGMA_TABLE_NAME", length=10)
	private String liangmaTableName;//两码基础表表名
	
	@Column(name="SANMA_TABLE_NAME", length=10)
	private String sanmaTableName;//三码基础表表名
	
	@Column(name="SIMA_TABLE_NAME", length=10)
	private String simaTableName;//四码基础表表名
	
	@Column(name="LIUMA_TABLE_NAME", length=10)
	private String liumaTableName;//六码基础表表名
	
	
	

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

	public BasePredictionType getBasePredictionType() {
		return basePredictionType;
	}

	public void setBasePredictionType(BasePredictionType basePredictionType) {
		this.basePredictionType = basePredictionType;
	}

	public Integer getOrderRule() {
		return orderRule;
	}

	public void setOrderRule(Integer orderRule) {
		this.orderRule = orderRule;
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

	public LotteryPlay getLotteryPlay() {
		return lotteryPlay;
	}

	public void setLotteryPlay(LotteryPlay lotteryPlay) {
		this.lotteryPlay = lotteryPlay;
	}
	
	
	
	
}
