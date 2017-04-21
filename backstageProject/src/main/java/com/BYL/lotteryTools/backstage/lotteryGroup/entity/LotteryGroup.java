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
 * 彩票群
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月18日 下午4:41:31
 */
@Entity
@Table(name="T_LOTTERYTOOLS_LOTTERY_GROUP")
public class LotteryGroup extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	private String id;//群id，群融云id
	
	@Column(name="NAME", length=45)
	private String name;//群名称
	
	@Column(name="LOTTERY_TYPE", length=45)
	private String lotteryType;//群分类，1：体彩，2：福彩 3：竞彩
	
	@Column(name="GROUP_LEVEL", length=45)
	private String groupLevel;//群等级，与群等级表关联
	
	@Column(name="GROUP_ROBOT_ID", length=45)
	private String groupRobotID;//机器人id（与用户表关联）,区域彩种高频一个机器人
	
	@Column(name="INTRODUCTION", length=45)
	private String introduction;//群简介
	
	@Column(name="TOU_XIANG", length=45)
	private String touXiang;//头像图片
	
	@ManyToOne
	@JoinColumn(name="GROUP_OWNER_ID",referencedColumnName="id")
	private LotterybuyerOrExpert lotteryBuyerOrExpert;//群主id
	
	

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(String groupLevel) {
		this.groupLevel = groupLevel;
	}

	public String getGroupRobotID() {
		return groupRobotID;
	}

	public void setGroupRobotID(String groupRobotID) {
		this.groupRobotID = groupRobotID;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getTouXiang() {
		return touXiang;
	}

	public void setTouXiang(String touXiang) {
		this.touXiang = touXiang;
	}

	public LotterybuyerOrExpert getLotteryBuyerOrExpert() {
		return lotteryBuyerOrExpert;
	}

	public void setLotteryBuyerOrExpert(LotterybuyerOrExpert lotteryBuyerOrExpert) {
		this.lotteryBuyerOrExpert = lotteryBuyerOrExpert;
	}

	
	
	
	
}
