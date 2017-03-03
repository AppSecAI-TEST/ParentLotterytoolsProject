package com.BYL.lotteryTools.backstage.serviceNumber.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 服务号
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月27日 下午3:48:30
 */
@Entity
@Table(name="T_LOTTERYTOOLS_SERVICENUMBER")
public class ServiceNumber extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="SERVICENUMBER_NAME",length=45)
	private String serviceNumberName;//服务号名称
	
	@Column(name="CODE",length=45)
	private String code;//登录名
	
	@Column(name="PASSWORD",length=45)
	private String password;//密码
	
	@Column(name="PROVINCE",length=45)
	private String province;//省
	
	@Column(name="CITY",length=45)
	private String city;//市
	
	@Column(name="COUNTRY",length=45)
	private String country;//区
	
	@Column(name="COORDINATE",length=45)
	private String coordinate;//坐标
	
	@Column(name="ADDDRESS",length=100)
	private String address;//详细地址
	
	@OneToMany(mappedBy="serviceNumber",fetch=FetchType.LAZY)
	private List<ServiceNumberNotice> serviceNumberNotice;
	
	
	

	public String getServiceNumberName() {
		return serviceNumberName;
	}

	public void setServiceNumberName(String serviceNumberName) {
		this.serviceNumberName = serviceNumberName;
	}

	public List<ServiceNumberNotice> getServiceNumberNotice() {
		return serviceNumberNotice;
	}

	public void setServiceNumberNotice(List<ServiceNumberNotice> serviceNumberNotice) {
		this.serviceNumberNotice = serviceNumberNotice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	
	
	
	
}
