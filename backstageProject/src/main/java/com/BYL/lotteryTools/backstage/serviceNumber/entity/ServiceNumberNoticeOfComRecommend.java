package com.BYL.lotteryTools.backstage.serviceNumber.entity;

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
 * 公司推荐的服务号公告
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月28日 下午3:13:33
 */
@Entity
@Table(name="T_LT_SERVICENUMBER_NOTICE_OF_RECOMMEND")
public class ServiceNumberNoticeOfComRecommend extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	//一个服务号公告可以由公司发布到多个省市
	@ManyToOne
	@JoinColumn(name="SERVICENUMBER_NOTICE_ID",referencedColumnName="id")
	private ServiceNumberNotice serviceNumberNotice;
	
	@Column(name="PROVINCE_CODE")
	private String provinceCode;
	
	@Column(name="CITY_CODE")
	private String cityCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ServiceNumberNotice getServiceNumberNotice() {
		return serviceNumberNotice;
	}

	public void setServiceNumberNotice(ServiceNumberNotice serviceNumberNotice) {
		this.serviceNumberNotice = serviceNumberNotice;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	
	
}
