package com.BYL.lotteryTools.backstage.lotteryStation.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 彩票站销售的彩种
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月28日 上午9:37:13
 */
@Entity
@Table(name="T_LOTTERYTYPE_OF_LOTTERYSTATION_SALE")
public class LotteryTypeOfLotteryStationSale extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@ManyToOne
	@JoinColumn(name="LOTTERYSTATION_ID" , referencedColumnName = "id")
	private LotteryStation lotteryStation;
	
	@ManyToOne
	@JoinColumn(name = "LOTTERYPLAY_ID" , referencedColumnName = "id")
	private LotteryPlay lotteryPlay;
	
	@Column(name="LOTTERY_SALES_VOLUME")
	private Integer lotterySalesVolume;//销量(每次销售后要进行累计计算)
	
	@Column(name="LOTTERY_SALES_AMOUNT")
	private BigDecimal lotterySalesAmount;//销售金额(每次销售后要进行累计计算)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LotteryStation getLotteryStation() {
		return lotteryStation;
	}

	public void setLotteryStation(LotteryStation lotteryStation) {
		this.lotteryStation = lotteryStation;
	}

	public LotteryPlay getLotteryPlay() {
		return lotteryPlay;
	}

	public void setLotteryPlay(LotteryPlay lotteryPlay) {
		this.lotteryPlay = lotteryPlay;
	}

	public Integer getLotterySalesVolume() {
		return lotterySalesVolume;
	}

	public void setLotterySalesVolume(Integer lotterySalesVolume) {
		this.lotterySalesVolume = lotterySalesVolume;
	}

	public BigDecimal getLotterySalesAmount() {
		return lotterySalesAmount;
	}

	public void setLotterySalesAmount(BigDecimal lotterySalesAmount) {
		this.lotterySalesAmount = lotterySalesAmount;
	}
	
	
	
	
}
