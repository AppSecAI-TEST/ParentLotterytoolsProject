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
	private String predictionTable;//预测方案表（一个预测类型对应一张预测方案表，例如11选5的预测类型1则对应11选5预测类型1方案表的表名）
	
	//多个预测类型可以对应一个彩种
	@ManyToOne  
    @JoinColumn(name = "PREDICTION_TYPE_ID", referencedColumnName = "id")
	private LotteryPlay lotteryPlay;

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
