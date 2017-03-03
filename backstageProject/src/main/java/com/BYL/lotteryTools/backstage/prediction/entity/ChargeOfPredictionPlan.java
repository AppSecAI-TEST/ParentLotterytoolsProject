package com.BYL.lotteryTools.backstage.prediction.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 预测方案收费规则表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年3月1日 上午10:15:37
 */
@Entity
@Table(name="T_LT_CHARGE_OF_PREDICTION_PLAN")
public class ChargeOfPredictionPlan extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="NAME")
	private String name;//收费规则名称
	
	@Column(name="ACCURACY_RATE")
	private Float accuracyRate;//预测准确率
	
	@Column(name="PRICE")
	private BigDecimal price;//价格（根据不同的预测准确率确认不同的价格）
	
	@Column(name="PERCENT_OF_COM")
	private Float percentOfCom;//公司抽成比例
	
	@Column(name="PERCENT_OF_EXPERT")
	private Float percentOfExpert;//专家抽成比例

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

	public Float getAccuracyRate() {
		return accuracyRate;
	}

	public void setAccuracyRate(Float accuracyRate) {
		this.accuracyRate = accuracyRate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Float getPercentOfCom() {
		return percentOfCom;
	}

	public void setPercentOfCom(Float percentOfCom) {
		this.percentOfCom = percentOfCom;
	}

	public Float getPercentOfExpert() {
		return percentOfExpert;
	}

	public void setPercentOfExpert(Float percentOfExpert) {
		this.percentOfExpert = percentOfExpert;
	}
	
	
	
	
}
