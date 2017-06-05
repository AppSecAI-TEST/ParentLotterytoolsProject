package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.common.entity.BaseEntity;


/**
 * 用户和彩聊卡的关联表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年5月25日 下午1:52:06
 */
@Entity
@Table(name="RELA_BIND_OF_BUYER_AND_LOTTERY_CARD")
public class RelaLBEUserAndLtcard extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	//一个用户可以拥有多种彩聊卡
	@ManyToOne
	@JoinColumn(name="LBOE_ID",referencedColumnName="id")
	private LotterybuyerOrExpert lotterybuyerOrExpert;
	
	//一个彩票站可以被多个彩票站关注
	@ManyToOne
	@JoinColumn(name="LCARD_ID",referencedColumnName="id")
	private LotteryChatCard lotteryChatCard;
	
	@Column(name="USE_COUNT")
	private Integer useCount;//已使用卡片数
	
	@Column(name="NOT_USE_COUNT")
	private Integer notUseCount;//剩余卡片数

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

	public LotteryChatCard getLotteryChatCard() {
		return lotteryChatCard;
	}

	public void setLotteryChatCard(LotteryChatCard lotteryChatCard) {
		this.lotteryChatCard = lotteryChatCard;
	}

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	public Integer getNotUseCount() {
		return notUseCount;
	}

	public void setNotUseCount(Integer notUseCount) {
		this.notUseCount = notUseCount;
	}

	
	
	
	
}
