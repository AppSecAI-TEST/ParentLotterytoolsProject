package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 彩金/彩币充值记录表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月28日 下午3:04:20
 */
@Entity
@Table(name="T_LT_RECHARGE_FOR_HANDSEL_OR_COLORCOINS")
public class RechargeForHandselOrColorcoins 
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	
	@Column(name="RECHARGE_MODE",length=10)
	private String rechargeMode;//充值方式
	
	@Column(name="RECHARGE_USER",length=10)
	private String rechargeUser;//充值操作人
	
	@Column(name="RECHARGE_OBJECT",length=10)
	private String rechargeObject;//充值对象（0：彩币 1：彩金）
	
	@ManyToOne
	@JoinColumn(name = "LBOE_ID",referencedColumnName="id")
	private LotterybuyerOrExpert lotterybuyerOrExpert;
	
	@Column(name="STATUS",length=10)
	private String status;//充值状态
	
	@Column(name="STATUS_TIME")
	private Timestamp statustime;//充值状态更改时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRechargeMode() {
		return rechargeMode;
	}

	public void setRechargeMode(String rechargeMode) {
		this.rechargeMode = rechargeMode;
	}

	public String getRechargeUser() {
		return rechargeUser;
	}

	public void setRechargeUser(String rechargeUser) {
		this.rechargeUser = rechargeUser;
	}

	public String getRechargeObject() {
		return rechargeObject;
	}

	public void setRechargeObject(String rechargeObject) {
		this.rechargeObject = rechargeObject;
	}

	public LotterybuyerOrExpert getLotterybuyerOrExpert() {
		return lotterybuyerOrExpert;
	}

	public void setLotterybuyerOrExpert(LotterybuyerOrExpert lotterybuyerOrExpert) {
		this.lotterybuyerOrExpert = lotterybuyerOrExpert;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getStatustime() {
		return statustime;
	}

	public void setStatustime(Timestamp statustime) {
		this.statustime = statustime;
	}
	
	
}
