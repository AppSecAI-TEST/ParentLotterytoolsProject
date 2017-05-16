package com.BYL.lotteryTools.backstage.lotteryGroup.entity;

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
 * 用户加入群关联表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月24日 下午2:14:09
 */
@Entity
@Table(name="RELA_BIND_OF_BUYER_AND_LOTTERYGROUP")
public class RelaBindOfLbuyerorexpertAndGroup extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	//一个用户可以关注多个彩票站
	@ManyToOne
	@JoinColumn(name="LBOE_ID",referencedColumnName="id")
	private LotterybuyerOrExpert lotterybuyerOrExpert;
	
	//一个彩票站可以被多个彩票站关注
	@ManyToOne
	@JoinColumn(name="GROUP_ID",referencedColumnName="id")
	private LotteryGroup lotteryGroup;
	
	
	@Column(name="IS_TOP",length=10)
	private String isTop;//是否置顶（0：未置顶 1：置顶）
	
	@Column(name="IS_RECEIVER",length=10)
	private String isReceive;//是否接收消息（0：不接收消息 1：接收消息）
	
	@Column(name="IS_GROUP_OWNER",length=10)
	private String isGroupOwner;//是否为群主
	
	


	public String getIsGroupOwner() {
		return isGroupOwner;
	}

	public void setIsGroupOwner(String isGroupOwner) {
		this.isGroupOwner = isGroupOwner;
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

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}

	
	
}
