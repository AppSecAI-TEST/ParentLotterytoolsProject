package com.BYL.lotteryTools.backstage.lotteryStation.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;

/**
 * 用户购买预测方案记录表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月28日 下午4:56:07
 */
@Entity
@Table(name="RELA_PURCHASE_PREDICTION_RECORD_OF_LOTTERYBUYER")
public class RelaPurchasePredictionRecordOfLotteryBuyer 
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@ManyToOne
	@JoinColumn(name="LBOE_ID",referencedColumnName="id")
	private LotterybuyerOrExpert lotterybuyerOrExpert;//一个用户可以购买多个预测方案
	
	@Column(name="PREDICTION_ID")
	private String predictionId;//预测方案id
	
	@Column(name="PURCHASE_MODE")
	private String purchaseMode;//购买方式
	
	@Column(name="STATUS",length=10)
	private String status;//0:正在处理1：付费成功2:申请退费3：退费成功
	
	@Column(name="PAY_TIME")
	private Timestamp payTime;//付费时间
	
	@Column(name="DRAWEE")
	private String drawee;//付款人
	
	@Column(name="MODIFY_TIME")
	private Timestamp modifyTime;//更改时间
	
	@Column(name="MODIFIER")
	private String modifier;//更改人

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

	public String getPredictionId() {
		return predictionId;
	}

	public void setPredictionId(String predictionId) {
		this.predictionId = predictionId;
	}

	public String getPurchaseMode() {
		return purchaseMode;
	}

	public void setPurchaseMode(String purchaseMode) {
		this.purchaseMode = purchaseMode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public String getDrawee() {
		return drawee;
	}

	public void setDrawee(String drawee) {
		this.drawee = drawee;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	
	
}
