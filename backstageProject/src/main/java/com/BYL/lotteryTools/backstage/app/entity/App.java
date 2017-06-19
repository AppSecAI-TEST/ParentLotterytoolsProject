package com.BYL.lotteryTools.backstage.app.entity;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 
* @ClassName: App
* @Description: 应用表实体
* @author banna
* @date 2016年1月25日 下午3:27:31
*
 */
@Entity
@Table(name="T_LT_APPLICATION")
public class App extends BaseEntity{

	
	@Id
	@Column(name="ID", nullable=false, length=45)
//	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
//	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="APP_CODE", length=45)
	private String appCode;//应用编码
	
	
	@Column(name="APP_NAME", length=45)
	private String appName;//应用名称
	
	@Column(name="APP_STATUS", length=45)
	private String appStatus;//应用状态(0:待上架1:上架2:下架3:更新)
	
	
	@Column(name="APP_DEVELOPER", length=45)
	private String appDeveloper;//应用开发商
	
	//省
	@Column(name="PROVINCE", length=45)
	private String province;
	//市
	@Column(name="CITY", length=45)
	private String city;
	//区
	@Column(name="COUNTRY", length=45)
	private String country;
	
	//应用默认单价
	@Column(name="APP_MONEY", length=45)
	private String appMoney;
	
	@Column(name="LOTTERY_TYPE", length=45)
	private String lotteryType;//彩种
	
	//与“应用版本表”关联
	@OneToMany(mappedBy = "app", fetch = FetchType.LAZY) 
	private List<Appversion> appVersions;
	
	
	
	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}


	public String getAppMoney() {
		return appMoney;
	}

	public void setAppMoney(String appMoney) {
		this.appMoney = appMoney;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}




	public List<Appversion> getAppVersions() {
		return appVersions;
	}


	public void setAppVersions(List<Appversion> appVersions) {
		this.appVersions = appVersions;
	}


	public String getAppDeveloper() {
		return appDeveloper;
	}


	public void setAppDeveloper(String appDeveloper) {
		this.appDeveloper = appDeveloper;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAppCode() {
		return appCode;
	}


	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}


	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}


	public String getAppStatus() {
		return appStatus;
	}


	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}




	
	
	
}
