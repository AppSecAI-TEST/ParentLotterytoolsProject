package com.BYL.lotteryTools.backstage.outer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * app购彩方案
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年7月11日 上午11:22:08
 */
@Entity
@Table(name="T_LT_PLAN_FROM_APP")
public class PlanFromApp extends BaseEntity
{
	@Id
    @Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
	@Column(name="COST")
	private String cost;
	
	@Column(name="COUNT")
	private String count;
	
	@Column(name="MULTIPLE")
	private String multiple;
	
	@Column(name="NUMBER")
	private String number;
	
	@Column(name="NUMBER_TWO")
	private String numberTwo;
	
	@Column(name="NUMBER_THREE")
	private String numberThree;
	
	@Column(name="PLAY")
	private String play;
	
	@Column(name="PROVINCE_CODE")
	private String provinceCode;
	
	@Column(name="PROVINCE_NAME")
	private String provinceName;
	
	@Column(name="STAGE")
	private String stage;
	
	@Column(name="STATE")
	private String state;
	
	@ManyToOne
	@JoinColumn(name="P_PACKAGE_ID",referencedColumnName="id")
	private PlanPackageFromApp planPackageFromApp;
	
	

	public PlanPackageFromApp getPlanPackageFromApp() {
		return planPackageFromApp;
	}

	public void setPlanPackageFromApp(PlanPackageFromApp planPackageFromApp) {
		this.planPackageFromApp = planPackageFromApp;
	}


	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}


	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumberTwo() {
		return numberTwo;
	}

	public void setNumberTwo(String numberTwo) {
		this.numberTwo = numberTwo;
	}

	public String getNumberThree() {
		return numberThree;
	}

	public void setNumberThree(String numberThree) {
		this.numberThree = numberThree;
	}

	public String getPlay() {
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	


	
	
}
