package com.BYL.lotteryTools.backstage.lotteryGroup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.common.entity.BaseEntity;


/**
 * 推送的系统消息表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年6月5日 上午11:05:50
 */
@Entity
@Table(name="T_LT_SYS_MESSAGE")
public class SysMessage extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;//群id，群融云id
	
	@Column(name="TARGET", length=45)
	private String target;//推送目标
	
	@Column(name="MESSAGE")
	private String message;//推送内容

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
