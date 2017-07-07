package com.BYL.lotteryTools.backstage.lotteryGroup.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.common.entity.BaseEntity;


/**
 * 群通知消息（后台操作给群以机器人身份发送图片或文字通知）
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年6月8日 下午4:50:00
 */
@Entity
@Table(name="T_LT_GROUP_MSG_OF_PUSH")
public class GroupMsgOfPush extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	//一个信息可以推送给多个群，一个群也可以收到多个信息
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "RELA_LT_GROUPMSG_AND_LTGROUP", 
            joinColumns = {  @JoinColumn(name = "GROUP_MSG_ID", referencedColumnName = "id")  }, 
            inverseJoinColumns = {@JoinColumn(name = "GROUP_ID", referencedColumnName = "id") })
	private List<LotteryGroup> lotteryGroupList;
	
	
	@Column(name="TYPE")
	private String type;//0:文字1：图片2：图文
	
	@Column(name="MESSAGE")
	private String message;//文字内容
	
	
	@Column(name="IMG_FILE_UUID")
	private String imgFileUuid;//图片id
	

	@Column(name="STATUS")
	private String status;//群推送通知状态，0：保存，1：发送


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}




	public List<LotteryGroup> getLotteryGroupList() {
		return lotteryGroupList;
	}


	public void setLotteryGroupList(List<LotteryGroup> lotteryGroupList) {
		this.lotteryGroupList = lotteryGroupList;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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
	
	


	
	

	
}
