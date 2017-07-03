package com.BYL.lotteryTools.backstage.lotteryManage.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.common.entity.BaseEntity;


/**
 * 低频彩种维护
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年6月26日 下午12:07:36
 */
@Entity
@Table(name="T_LT_LOTTERY_DIPIN_PLAY")
public class LotteryDiPinPlay extends BaseEntity implements Serializable 
{
	
	
	/** 
	  * @Fields serialVersionUID : 使用逆向工程时使用
	  */ 
	private static final long serialVersionUID = 9029294793418739543L;

	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="PLAN_NAME", length=45)
	private String planName;//方案名称
	
	@Column(name="PLAN_CODE", length=45)
	private String planCode;//方案编码
	
	@Column(name="START_NUMBER", length=45)
	private String startNumber;//号码选择范围的开始号码是几，11选5是1开始，时时彩是0开始，用来确认前台的补录方案号码以几开始
	
	@Column(name="END_NUMBER", length=45)
	private String endNumber;//号码选择范围的结束号码是几，11选5是11结束，时时彩是9结束，用来确认前台的补录方案号码以几结束
	
	@Column(name="NUM_OR_CHAR", length=45)
	private String numOrChar;//彩种玩法是数字还是汉字，因为有的彩种玩法是汉字的，用此字段来区分，（0：数字，1：汉字或其他）
	
	@Column(name="REPEAT_NUM", length=45)
	private String repeatNum;//开奖号码是否可以重复，0：不重复 1：重复
	
	@Column(name="OTHER_PLAN", length=255)
	private String otherPlan;//若为其他玩法类型，存储其方案
	
	@Column(name="OTHER_NUM", length=255)
	private String otherNum;//其他需要计算的字段和方法
	
	@Column(name="MORE_PART_KJ", length=45)
	private String morePartKj;//多部分开奖，分红蓝号码
	
	
	@Column(name="MORE_START_NUMBER", length=45)
	private String moreStartNumber;//第二部分范围开始号码
	
	@Column(name="MORE_END_NUMBER", length=45)
	private String moreEndNumber;//第二部分范围结束号码
	
	@Column(name="LOTTERY_TYPE", length=45)
	private String lotteryType;//彩种，1：体彩，2：福彩
	
	@Column(name="ISSUENUM_NUMBER_LEN", length=45)
	private String issueNumLen;//期号长度
	
	@Column(name="LOTTERY_NUMBER", length=45)
	private String lotteryNumber;//开奖号码个数
	
	@Column(name="BLUE_LOTTERY_NUMBER", length=45)
	private String blueLotteryNumber;//蓝号开奖号码个数
	
	@Column(name="CORRESPONDINGTABLE", length=45)
	private String correspondingTable;//彩种对应的补录表
	
	
	
	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getBlueLotteryNumber() {
		return blueLotteryNumber;
	}

	public void setBlueLotteryNumber(String blueLotteryNumber) {
		this.blueLotteryNumber = blueLotteryNumber;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getIssueNumLen() {
		return issueNumLen;
	}

	public void setIssueNumLen(String issueNumLen) {
		this.issueNumLen = issueNumLen;
	}

	public String getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public String getCorrespondingTable() {
		return correspondingTable;
	}

	public void setCorrespondingTable(String correspondingTable) {
		this.correspondingTable = correspondingTable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRepeatNum() {
		return repeatNum;
	}

	public void setRepeatNum(String repeatNum) {
		this.repeatNum = repeatNum;
	}

	

	
	
	public String getMorePartKj() {
		return morePartKj;
	}

	public void setMorePartKj(String morePartKj) {
		this.morePartKj = morePartKj;
	}

	public String getMoreStartNumber() {
		return moreStartNumber;
	}

	public void setMoreStartNumber(String moreStartNumber) {
		this.moreStartNumber = moreStartNumber;
	}

	public String getMoreEndNumber() {
		return moreEndNumber;
	}

	public void setMoreEndNumber(String moreEndNumber) {
		this.moreEndNumber = moreEndNumber;
	}


	public String getPlanName() {
		return planName;
	}

	public String getOtherNum() {
		return otherNum;
	}

	public void setOtherNum(String otherNum) {
		this.otherNum = otherNum;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(String startNumber) {
		this.startNumber = startNumber;
	}

	public String getEndNumber() {
		return endNumber;
	}

	public void setEndNumber(String endNumber) {
		this.endNumber = endNumber;
	}

	public String getNumOrChar() {
		return numOrChar;
	}

	public void setNumOrChar(String numOrChar) {
		this.numOrChar = numOrChar;
	}

	public String getOtherPlan() {
		return otherPlan;
	}

	public void setOtherPlan(String otherPlan) {
		this.otherPlan = otherPlan;
	}

	
	
	
	
}
