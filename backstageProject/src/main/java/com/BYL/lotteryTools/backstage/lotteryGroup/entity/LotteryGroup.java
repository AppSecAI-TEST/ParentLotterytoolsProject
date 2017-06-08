package com.BYL.lotteryTools.backstage.lotteryGroup.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 彩票群
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月18日 下午4:41:31
 */
@Entity
@Table(name="T_LT_LOTTERY_GROUP")
public class LotteryGroup extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	private String id;//群id，群融云id
	
	@Column(name="NAME", length=45)
	private String name;//群名称
	
	@Column(name="GROUP_NUMBER", length=45)
	private String groupNumber;//群号
	
	@Column(name="LOTTERY_TYPE", length=45)
	private String lotteryType;//群分类，1：体彩，2：福彩 3：竞彩,4:中心群,5：公司群
	
	/**
	 * Add in 2017/6/6 by banna 
	 */
	@Column(name="DETAIL_LOTTERY_TYPE", length=45)
	private String detailLotteryType;//详细群彩种分类，1：体彩，2：福彩 
	
	@Column(name="GROUP_LEVEL", length=45)
	private String groupLevel;//群等级，与群等级表关联
	
	@Column(name="GROUP_ROBOT_ID", length=45)
	private String groupRobotID;//机器人id（与用户表关联）,区域彩种高频一个机器人
	
	@Column(name="INTRODUCTION", length=45)
	private String introduction;//群简介
	
	@Column(name="TOU_XIANG", length=45)
	private String touXiang;//头像图片
	
	@Column(name="PROVINCE",length=45)
	private String province;//省
	
	@Column(name="CITY",length=45)
	private String city;//市
	
	@Column(name="MEMBER_COUNT")
	private Integer memberCount;//当前群人数
	
	@Column(name="JOIN_TYPE")
	private Integer joinType;//加入方式，1：验证加入 0：自由加入
	
	@Column(name="FABUKJ")
	private Integer fabuKj;//是否发布开奖画面（1：发布0：不发布）
	
	@Column(name="FABUZS")
	private Integer fabuZs;//是否发布走势画面（1：发布0：不发布）
	
	@Column(name="SSYLCX")
	private Integer ssYlChaxun;//实时遗漏查询（1：是0：否）
	
	@Column(name="SSZJCX")
	private Integer ssZjChaxun;//实时专家查询（1：是0：否）
	
	@Column(name="SSKJCX")
	private Integer ssKjChaxun;//是否实时开奖查询（1：是0：否）
	
	@Column(name="NOTICE_REVIEW")
	private Integer noticeReview;//群公告是否审核，0：不审核 1：审核
	
	@Column(name="GROUP_QR_IMG")
	private String groupQRImg;//群二维码图片
	
	@ManyToOne
	@JoinColumn(name="GROUP_OWNER_ID",referencedColumnName="id")
	private LotterybuyerOrExpert lotteryBuyerOrExpert;//群主id
	
	//一个群可以被多个用户加入
	@OneToMany(mappedBy = "lotteryGroup", fetch = FetchType.LAZY)
	private List<RelaBindOfLbuyerorexpertAndGroup> relaBindOfLbuyerorexpertAndGroups ;
	
	//2017.5.9ADD:一个群可以被多个用户申请加入
	@OneToMany(mappedBy = "lotteryGroup", fetch = FetchType.LAZY)
	private List<RelaApplyOfLbuyerorexpertAndGroup> relaApplyOfLbuyerorexpertAndGroups ;
	
	//一个群可以对应多个群升级记录
	@OneToMany(mappedBy = "lotteryGroup", fetch = FetchType.LAZY)
	private List<RelaGroupUpLevelRecord> relaGroupUpLevelRecords ;
	
	//一个群可以对应多个群公告
	@OneToMany(mappedBy = "lotteryGroup", fetch = FetchType.LAZY)
	private List<LotteryGroupNotice> lotteryGroupNotices  ;
	
	//一个信息可以推送给多个群，一个群也可以收到多个信息
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<GroupMsgOfPush> groupMsgOfPushs;
	
	
	

	public List<GroupMsgOfPush> getGroupMsgOfPushs() {
		return groupMsgOfPushs;
	}

	public void setGroupMsgOfPushs(List<GroupMsgOfPush> groupMsgOfPushs) {
		this.groupMsgOfPushs = groupMsgOfPushs;
	}

	public String getDetailLotteryType() {
		return detailLotteryType;
	}

	public void setDetailLotteryType(String detailLotteryType) {
		this.detailLotteryType = detailLotteryType;
	}

	public Integer getNoticeReview() {
		return noticeReview;
	}

	public void setNoticeReview(Integer noticeReview) {
		this.noticeReview = noticeReview;
	}

	public List<LotteryGroupNotice> getLotteryGroupNotices() {
		return lotteryGroupNotices;
	}

	public void setLotteryGroupNotices(List<LotteryGroupNotice> lotteryGroupNotices) {
		this.lotteryGroupNotices = lotteryGroupNotices;
	}

	public String getGroupQRImg() {
		return groupQRImg;
	}

	public void setGroupQRImg(String groupQRImg) {
		this.groupQRImg = groupQRImg;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public Integer getFabuKj() {
		return fabuKj;
	}

	public void setFabuKj(Integer fabuKj) {
		this.fabuKj = fabuKj;
	}

	public Integer getFabuZs() {
		return fabuZs;
	}

	public void setFabuZs(Integer fabuZs) {
		this.fabuZs = fabuZs;
	}

	public Integer getSsYlChaxun() {
		return ssYlChaxun;
	}

	public void setSsYlChaxun(Integer ssYlChaxun) {
		this.ssYlChaxun = ssYlChaxun;
	}

	public Integer getSsZjChaxun() {
		return ssZjChaxun;
	}

	public void setSsZjChaxun(Integer ssZjChaxun) {
		this.ssZjChaxun = ssZjChaxun;
	}

	public Integer getSsKjChaxun() {
		return ssKjChaxun;
	}

	public void setSsKjChaxun(Integer ssKjChaxun) {
		this.ssKjChaxun = ssKjChaxun;
	}

	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroups() {
		return relaApplyOfLbuyerorexpertAndGroups;
	}

	public void setRelaApplyOfLbuyerorexpertAndGroups(
			List<RelaApplyOfLbuyerorexpertAndGroup> relaApplyOfLbuyerorexpertAndGroups) {
		this.relaApplyOfLbuyerorexpertAndGroups = relaApplyOfLbuyerorexpertAndGroups;
	}

	public Integer getJoinType() {
		return joinType;
	}

	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}

	public List<RelaGroupUpLevelRecord> getRelaGroupUpLevelRecords() {
		return relaGroupUpLevelRecords;
	}

	public void setRelaGroupUpLevelRecords(
			List<RelaGroupUpLevelRecord> relaGroupUpLevelRecords) {
		this.relaGroupUpLevelRecords = relaGroupUpLevelRecords;
	}

	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	public List<RelaBindOfLbuyerorexpertAndGroup> getRelaBindOfLbuyerorexpertAndGroups() {
		return relaBindOfLbuyerorexpertAndGroups;
	}

	public void setRelaBindOfLbuyerorexpertAndGroups(
			List<RelaBindOfLbuyerorexpertAndGroup> relaBindOfLbuyerorexpertAndGroups) {
		this.relaBindOfLbuyerorexpertAndGroups = relaBindOfLbuyerorexpertAndGroups;
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

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(String groupLevel) {
		this.groupLevel = groupLevel;
	}

	public String getGroupRobotID() {
		return groupRobotID;
	}

	public void setGroupRobotID(String groupRobotID) {
		this.groupRobotID = groupRobotID;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getTouXiang() {
		return touXiang;
	}

	public void setTouXiang(String touXiang) {
		this.touXiang = touXiang;
	}

	public LotterybuyerOrExpert getLotteryBuyerOrExpert() {
		return lotteryBuyerOrExpert;
	}

	public void setLotteryBuyerOrExpert(LotterybuyerOrExpert lotteryBuyerOrExpert) {
		this.lotteryBuyerOrExpert = lotteryBuyerOrExpert;
	}

	
	
	
	
}
