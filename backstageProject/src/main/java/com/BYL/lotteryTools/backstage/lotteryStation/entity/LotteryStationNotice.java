package com.BYL.lotteryTools.backstage.lotteryStation.entity;

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
 * 彩票站公告
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月27日 下午4:31:50
 */
@Entity
@Table(name="T_LT_LOTTERYSTATION_NOTTICE")
public class LotteryStationNotice extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="NOTICE_NAME", length=45)
	private String noticeName;//公告名称
	
	@Column(name="NOTICE_CONTENT",length=16777216)//对应mysql的longtext
	private String noticeContent;//公告内容
	
	//多个彩票站公告对应一个彩票站
	@ManyToOne  
    @JoinColumn(name = "LS_ID", referencedColumnName = "id")
	private LotteryStation lotteryStation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public LotteryStation getLotteryStation() {
		return lotteryStation;
	}

	public void setLotteryStation(LotteryStation lotteryStation) {
		this.lotteryStation = lotteryStation;
	}
	
	
	
}
