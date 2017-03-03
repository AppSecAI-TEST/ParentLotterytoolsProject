package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.common.entity.BaseEntity;


/**
 * 用户关注或绑定彩票站关联表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月28日 下午4:23:01
 */
@Entity
@Table(name="RELA_BIND_OF_LOTTERYBUYEROREXPERT_AND_LOTTERYSTATION")
public class RelaBindOfLbuyerorexpertAndLStation extends BaseEntity
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
	@JoinColumn(name="LS_ID",referencedColumnName="id")
	private LotteryStation lotteryStation;
	
	@Column(name="IS_FOLLOW",length=10)
	private String isFollow;//是否关注（0：未关注 1：关注）
	
	@Column(name="IS_BIND",length=10)
	private String isBind;//是否绑定（0：未绑定 1：绑定）
	
	@Column(name="IS_TOP",length=10)
	private String isTop;//是否置顶（0：未置顶 1：置顶）
	
	@Column(name="IS_RECEIVER",length=10)
	private String isReceive;//是否接收消息（0：不接收消息 1：接收消息）

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

	public LotteryStation getLotteryStation() {
		return lotteryStation;
	}

	public void setLotteryStation(LotteryStation lotteryStation) {
		this.lotteryStation = lotteryStation;
	}

	public String getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(String isFollow) {
		this.isFollow = isFollow;
	}

	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
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
