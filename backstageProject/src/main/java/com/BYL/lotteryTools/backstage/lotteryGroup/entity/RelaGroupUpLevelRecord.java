package com.BYL.lotteryTools.backstage.lotteryGroup.entity;

import java.sql.Timestamp;

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
 * 群升级记录表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月25日 上午8:50:50
 */
@Entity
@Table(name="RELA_LOTTERYGROUP_UPLEVEL_RECORD")
public class RelaGroupUpLevelRecord extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="OPERATIOR")
	private String operator;//升级操作人
	
	//一个群可以多次升级
	@ManyToOne
	@JoinColumn(name="GROUP_ID",referencedColumnName="id")
	private LotteryGroup lotteryGroup;
	
	//一个群可以对应多个等级
	@ManyToOne
	@JoinColumn(name="BEFORE_LEVEL",referencedColumnName="id")
	private LGroupLevel beforeLevel;
	
	@ManyToOne
	@JoinColumn(name="AFTER_LEVEL",referencedColumnName="id")
	private LGroupLevel afterLevel;
		
	
	/*@Column(name="CREATE_TIME")
	private Timestamp createTime;//创建时间
*/	
	@Column(name="VALID_TIME")
	private Timestamp validTime;//有效时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public LotteryGroup getLotteryGroup() {
		return lotteryGroup;
	}

	public void setLotteryGroup(LotteryGroup lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
	}

	public LGroupLevel getBeforeLevel() {
		return beforeLevel;
	}

	public void setBeforeLevel(LGroupLevel beforeLevel) {
		this.beforeLevel = beforeLevel;
	}

	public LGroupLevel getAfterLevel() {
		return afterLevel;
	}

	public void setAfterLevel(LGroupLevel afterLevel) {
		this.afterLevel = afterLevel;
	}


	public Timestamp getValidTime() {
		return validTime;
	}

	public void setValidTime(Timestamp validTime) {
		this.validTime = validTime;
	}

	
}
