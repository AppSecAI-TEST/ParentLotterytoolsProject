package com.BYL.lotteryTools.backstage.lotteryGroup.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="T_LT_GROUP_LEVEL")
public class LGroupLevel 
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="GROUP_LEVEL_NAME")
	private String groupLevelName;//群等级名称
	
	@Column(name="GROUP_LEVEL")
	private String groupLevel;//群等级
	
	@Column(name="MEMBER_COUNT")
	private Integer memberCount;//当前群等级对应人数
	
	@Column(name="MONEY")
	private BigDecimal money;//升级当前等级花费多少钱
	
	@OneToMany(mappedBy = "beforeLevel", fetch = FetchType.LAZY)
	private List<RelaGroupUpLevelRecord> relaGroupUpLevelRecords ;
	
	@OneToMany(mappedBy = "afterLevel", fetch = FetchType.LAZY)
	private List<RelaGroupUpLevelRecord> relaGroupUpLevelRecordsAfter ;
	
	

	public List<RelaGroupUpLevelRecord> getRelaGroupUpLevelRecords() {
		return relaGroupUpLevelRecords;
	}

	public void setRelaGroupUpLevelRecords(
			List<RelaGroupUpLevelRecord> relaGroupUpLevelRecords) {
		this.relaGroupUpLevelRecords = relaGroupUpLevelRecords;
	}

	public List<RelaGroupUpLevelRecord> getRelaGroupUpLevelRecordsAfter() {
		return relaGroupUpLevelRecordsAfter;
	}

	public void setRelaGroupUpLevelRecordsAfter(
			List<RelaGroupUpLevelRecord> relaGroupUpLevelRecordsAfter) {
		this.relaGroupUpLevelRecordsAfter = relaGroupUpLevelRecordsAfter;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupLevelName() {
		return groupLevelName;
	}

	public void setGroupLevelName(String groupLevelName) {
		this.groupLevelName = groupLevelName;
	}

	public String getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(String groupLevel) {
		this.groupLevel = groupLevel;
	}

	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	
}
