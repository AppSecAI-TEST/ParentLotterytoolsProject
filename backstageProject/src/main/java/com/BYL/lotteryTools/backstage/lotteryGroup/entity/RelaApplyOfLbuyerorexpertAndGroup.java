package com.BYL.lotteryTools.backstage.lotteryGroup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.common.entity.BaseEntity;


/**
 * 用户申请加群关联表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年5月9日 上午9:08:12
 */
@Entity
@Table(name="RELA_APPLY_OF_BUYER_AND_LOTTERYGROUP")
public class RelaApplyOfLbuyerorexpertAndGroup extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	private String id;
	
	//一个用户可以申请加入多个彩票站
	@ManyToOne
	@JoinColumn(name="LBOE_ID",referencedColumnName="id")
	private LotterybuyerOrExpert lotterybuyerOrExpert;
	
	//一个彩票站可以被多个用户申请加入
	@ManyToOne
	@JoinColumn(name="GROUP_ID",referencedColumnName="id")
	private LotteryGroup lotteryGroup;
	
	
	@Column(name="APPLY_MESSAGE")
	private String applyMessage;//申请消息（用户填写）
	
	@Column(name="NOT_PASS_MESSAGE")
	private String notPassMessage;//群主不通过消息（群主填写）
	
	@Column(name="APPOVAL_USER")
	private String approvalUser;//审核人，群主
	
	@Column(name="STATUS")
	private String status;//审核状态0:不通过1：通过
	
	

	public String getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public LotteryGroup getLotteryGroup() {
		return lotteryGroup;
	}

	public void setLotteryGroup(LotteryGroup lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
	}

	public String getApplyMessage() {
		return applyMessage;
	}

	public void setApplyMessage(String applyMessage) {
		this.applyMessage = applyMessage;
	}

	public String getNotPassMessage() {
		return notPassMessage;
	}

	public void setNotPassMessage(String notPassMessage) {
		this.notPassMessage = notPassMessage;
	}

	

	
}
