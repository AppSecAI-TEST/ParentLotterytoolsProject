package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.BYL.lotteryTools.common.entity.BaseEntity;


/**
 * 彩聊卡维护表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年5月25日 下午1:51:37
 */
@Entity
@Table(name="T_LT_CARD")
public class LotteryChatCard extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	private String id;
	
	
	@Column(name="NAME")
	private String name;//卡名
	
	@Column(name="PRICE")
	private Double price;//售价
	
	@Column(name="INTRODUCE")
	private String introduce;//介绍描述
	
	@Column(name="DISCOUNT")
	private Double discount;
	
	@OneToMany(mappedBy="lotteryChatCard" , fetch=FetchType.LAZY)
	private List<RelaLBEUserAndLtcard> relaLBEUserAndLtcards;
	
	

	public List<RelaLBEUserAndLtcard> getRelaLBEUserAndLtcards() {
		return relaLBEUserAndLtcards;
	}

	public void setRelaLBEUserAndLtcards(
			List<RelaLBEUserAndLtcard> relaLBEUserAndLtcards) {
		this.relaLBEUserAndLtcards = relaLBEUserAndLtcards;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	
	
	
}
