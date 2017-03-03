package com.BYL.lotteryTools.backstage.serviceNumber.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 服务号公告
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月28日 上午10:24:07
 */
@Entity
@Table(name="T_LT_SERVICENUMBER_NOTICE")
public class ServiceNumberNotice extends BaseEntity 
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="SN_NOTICE_NAME")
	private String snNoticeName;
	
	@Column(name="SN_NOTICE_CONTENT",length=65535)
	private String snNoticeContent;
	
	@Column(name="START_TIME")
	private Timestamp startTime;
	
	@Column(name="END_TIME")
	private Timestamp endTime;
	
	@ManyToOne
	@JoinColumn(name="SERVICENUMBER_ID", referencedColumnName = "id")
	private ServiceNumber serviceNumber;
	
	@Column(name="STATUS",length=10)
	private String status;
	
	//一个服务号公告可以被发布到多个省市
	@OneToMany(mappedBy="serviceNumberNotice",fetch=FetchType.LAZY)
	private List<ServiceNumberNoticeOfComRecommend> serviceNumberNoticeOfComRecommends;
	

	public List<ServiceNumberNoticeOfComRecommend> getServiceNumberNoticeOfComRecommends() {
		return serviceNumberNoticeOfComRecommends;
	}

	public void setServiceNumberNoticeOfComRecommends(
			List<ServiceNumberNoticeOfComRecommend> serviceNumberNoticeOfComRecommends) {
		this.serviceNumberNoticeOfComRecommends = serviceNumberNoticeOfComRecommends;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSnNoticeName() {
		return snNoticeName;
	}

	public void setSnNoticeName(String snNoticeName) {
		this.snNoticeName = snNoticeName;
	}

	public String getSnNoticeContent() {
		return snNoticeContent;
	}

	public void setSnNoticeContent(String snNoticeContent) {
		this.snNoticeContent = snNoticeContent;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public ServiceNumber getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(ServiceNumber serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
