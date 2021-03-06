package com.BYL.lotteryTools.backstage.app.entity;


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
 * 
* @ClassName: Appversion
* @Description: 应用版本关联表
* @author banna
* @date 2016年1月25日 下午3:35:00
*
 */
@Entity
@Table(name="T_LT_APPLICATION_VERSION")
public class Appversion extends BaseEntity{

	
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="APP_VERSION_CODE", length=45)
	private String appVersionCode;//应用版本编码
	
	
	@Column(name="APP_VERSION_NAME", length=45)
	private String appVersionName;//应用版本名称
	
	@Column(name="VERSION_CODE", length=45)
	private String versionCode;//版本号
	
	
	@Column(name="VERSION_FLOW_ID", length=45)
	private Integer versionFlowId;//版本流水号


	@Column(name="APP_VERSION_URL", length=100)
	private String appVersionUrl;//应用版本安装包位置
	
	@Column(name="APP_VERSION_STATUS", length=45)
	private String appVersionStatus;//应用版本状态标记(0:待上架1:上架2:下架3:更新)
	
//	@Column(name="APP_ID", length=45)
//	private String appId;//应用id
	
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
	
	//版本描述
	@Column(name="VERSION_DESCRIPTION", length=600)
	private String versionDescription;//版本描述 addDate：2016-4-26
	
	@ManyToOne  
    @JoinColumn(name = "APP_ID", referencedColumnName = "id")
	private App app;
	
	

	public String getVersionDescription() {
		return versionDescription;
	}

	public void setVersionDescription(String versionDescription) {
		this.versionDescription = versionDescription;
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


	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppVersionCode() {
		return appVersionCode;
	}

	public void setAppVersionCode(String appVersionCode) {
		this.appVersionCode = appVersionCode;
	}

	public String getAppVersionName() {
		return appVersionName;
	}

	public void setAppVersionName(String appVersionName) {
		this.appVersionName = appVersionName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}



	public Integer getVersionFlowId() {
		return versionFlowId;
	}

	public void setVersionFlowId(Integer versionFlowId) {
		this.versionFlowId = versionFlowId;
	}

	public String getAppVersionUrl() {
		return appVersionUrl;
	}

	public void setAppVersionUrl(String appVersionUrl) {
		this.appVersionUrl = appVersionUrl;
	}

	public String getAppVersionStatus() {
		return appVersionStatus;
	}

	public void setAppVersionStatus(String appVersionStatus) {
		this.appVersionStatus = appVersionStatus;
	}


	public String getAppDeveloper() {
		return appDeveloper;
	}

	public void setAppDeveloper(String appDeveloper) {
		this.appDeveloper = appDeveloper;
	}
	
	
	
}
