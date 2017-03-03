package com.BYL.lotteryTools.backstage.lotteryStation.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 彩民购彩记录表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月28日 下午4:41:28
 */
@Entity
@Table(name="RELA_BUY_LOTTERY_OF_LOTTERYBUYER")
public class RelaBuyLotteryOfLotterybuyer extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@ManyToOne
	@JoinColumn(name="LBOE_ID",referencedColumnName="id")
	private LotterybuyerOrExpert lotterybuyerOrExpert;
	
	
	@ManyToOne
	@JoinColumn(name="LS_ID",referencedColumnName="id")
	private LotteryStation lotteryStation;//出票彩站id
	
	//用户使用哪种预测方案出票（可选也可不选择预测方案直接出票或使用预测工具进行出票，然后选择彩票站进行出票）
	@Column(name="PREDICTION_ID")
	private String predictionId;//预测方案id
	
	@Column(name="SALE_LOTTERY_CONTENT")
	private String saleLotteryContent;//出票内容
	
	@Column(name="SALE_LOTTERY_STATUS")
	private String saleLotteryStatus;//出票状态（0：未出票1：已出票）
	
	@Column(name="SALE_LOTTERY_MONEY")
	private BigDecimal saleLotteryMoney;//出票金额（用彩币计算）彩币可以通过彩金和现金购买，彩金可以体现，彩币不可以体现

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LotterybuyerOrExpert getLotterybuyerOrExpert() {
		return lotterybuyerOrExpert;
	}

	public void setLotterybuyerOrExpert(LotterybuyerOrExpert lotterybuyerOrExpert) {
		this.lotterybuyerOrExpert = lotterybuyerOrExpert;
	}

	public LotteryStation getLotteryStation() {
		return lotteryStation;
	}

	public void setLotteryStation(LotteryStation lotteryStation) {
		this.lotteryStation = lotteryStation;
	}

	public String getPredictionId() {
		return predictionId;
	}

	public void setPredictionId(String predictionId) {
		this.predictionId = predictionId;
	}

	public String getSaleLotteryContent() {
		return saleLotteryContent;
	}

	public void setSaleLotteryContent(String saleLotteryContent) {
		this.saleLotteryContent = saleLotteryContent;
	}

	public String getSaleLotteryStatus() {
		return saleLotteryStatus;
	}

	public void setSaleLotteryStatus(String saleLotteryStatus) {
		this.saleLotteryStatus = saleLotteryStatus;
	}

	public BigDecimal getSaleLotteryMoney() {
		return saleLotteryMoney;
	}

	public void setSaleLotteryMoney(BigDecimal saleLotteryMoney) {
		this.saleLotteryMoney = saleLotteryMoney;
	}
	
	
	
	
	
	
	
}
