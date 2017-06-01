package com.BYL.lotteryTools.backstage.lotteryGroup.dto;


public class LotteryGroupNoticeDTO {

	private String id;
	
	
	private String groupId;
	
	
	private String notice;//申请消息（用户填写）
	
	private String notPassMessage;//不通过消息
	
	
	private String status;//审核状态0:不通过1：通过
	
	private String creator;
	
	private String createTime;
	
	

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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	
}
