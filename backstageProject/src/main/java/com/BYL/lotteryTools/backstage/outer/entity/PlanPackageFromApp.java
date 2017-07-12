package com.BYL.lotteryTools.backstage.outer.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * app购彩方案包
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年7月11日 上午11:22:08
 */
@Entity
@Table(name="T_LT_PLAN_PACKAGE_FROM_APP")
public class PlanPackageFromApp extends BaseEntity
{
	@Id
    @Column(name="ID")
    private String id;
	
	@Column(name="SERIAL_NUM")
	private String serialNum;
    
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="PROVINCE_CODE")
	private String provinceCode;
	
	@Column(name="LOTTERY_TYPE")
	private String lotteryType;
	
	@Column(name="LOTTERY_NUMBER")
	private String lotteryNumber;//开奖号码个数
	
	@Column(name="STAGE")
	private String stage;//方案包期号
	
	@Column(name="KJ_NUMBER")
	private String kjNumber;//开奖号
	
	@Column(name="COST")
	private String cost;
	
	@Column(name="COUNT")
	private String count;
	
	@OneToMany(mappedBy = "planPackageFromApp", fetch = FetchType.LAZY)
	private List<PlanFromApp> planFromApps;
	
	
	

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public List<PlanFromApp> getPlanFromApps() {
		return planFromApps;
	}

	public void setPlanFromApps(List<PlanFromApp> planFromApps) {
		this.planFromApps = planFromApps;
	}

	

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public String getKjNumber() {
		return kjNumber;
	}

	public void setKjNumber(String kjNumber) {
		this.kjNumber = kjNumber;
	}

	
	
	
	


	
	
}
