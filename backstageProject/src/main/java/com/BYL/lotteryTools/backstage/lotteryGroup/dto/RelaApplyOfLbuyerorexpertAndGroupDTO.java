package com.BYL.lotteryTools.backstage.lotteryGroup.dto;

import com.BYL.lotteryTools.common.entity.BaseEntity;


/**
 * 用户申请加群dto
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年5月9日 上午9:08:12
 */
public class RelaApplyOfLbuyerorexpertAndGroupDTO extends BaseEntity
{
	private String id;
	
	private String applyUserId;
	
	private String groupId;
	
	private String groupName;
	
	
	private String applyMessage;//申请消息（用户填写）
	
	private String notPassMessage;//群主不通过消息（群主填写）
	
	private String approvalUser;//审核人，群主
	
	private String status;//审核状态0:不通过1：通过
	
	private String creatorName;//申请加群人的昵称
	
	
	

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	
	


	
}
