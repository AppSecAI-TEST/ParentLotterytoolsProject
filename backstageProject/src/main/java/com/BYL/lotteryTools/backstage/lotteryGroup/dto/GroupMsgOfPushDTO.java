package com.BYL.lotteryTools.backstage.lotteryGroup.dto;


public class GroupMsgOfPushDTO {

	private String id;
	
	private String type;//0:文字1：图片2：图文

	private String message;//文字内容
	
	
	private String imgFileUuid;//图片id
	
	private String creator;
	
	private String createTimeStr;
	
	

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImgFileUuid() {
		return imgFileUuid;
	}

	public void setImgFileUuid(String imgFileUuid) {
		this.imgFileUuid = imgFileUuid;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	
}
