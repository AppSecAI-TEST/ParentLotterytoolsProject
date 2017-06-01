package com.BYL.lotteryTools.backstage.lotteryGroup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.BYL.lotteryTools.common.entity.BaseEntity;



@Entity
@Table(name="T_LT_GROUP_NOTICE")
public class LotteryGroupNotice extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	private String id;
	
	
	//一个群公告对应一个群，一个群可以对应多个群公告
	@ManyToOne
	@JoinColumn(name="GROUP_ID",referencedColumnName="id")
	private LotteryGroup lotteryGroup;
	
	
	@Column(name="NOTICE")
	private String notice;//申请消息（用户填写）
	
	@Column(name="NOT_PASS_MESSAGE")
	private String notPassMessage;//不通过消息
	
	
	@Column(name="STATUS")
	private String status;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public LotteryGroup getLotteryGroup() {
		return lotteryGroup;
	}


	public void setLotteryGroup(LotteryGroup lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
	}


	


	public String getNotice() {
		return notice;
	}


	public void setNotice(String notice) {
		this.notice = notice;
	}


	public String getNotPassMessage() {
		return notPassMessage;
	}


	public void setNotPassMessage(String notPassMessage) {
		this.notPassMessage = notPassMessage;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	

	

	
}
