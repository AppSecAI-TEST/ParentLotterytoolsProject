package com.BYL.lotteryTools.backstage.lotteryStation.entity;

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

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.RelaBindOfLbuyerorexpertAndLStation;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.RelaInvitationOfLStationAndLbuyerorexpert;
import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 彩票站
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月27日 下午3:48:20
 */
@Entity
@Table(name="T_LT_LOTTERYSTATION")
public class LotteryStation extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@Column(name="STATION_OWNER",length=45)
	private String stationOwner;//站主
	
	@Column(name="STATION_NAME")
	private String stationName;//彩票站名称
	
	
	@Column(name="TELEPHONE",length=45)
	private String telephone;//站主手机
	
	@Column(name="STATION_NUMBER",length=45)
	private String stationNumber;//站点号
	
	@Column(name="CODE",length=45)
	private String code;//站主的真实姓名
	
	@Column(name="LOTTERYTYPE",length=45)
	private String lotteryType;//彩种类型，1：体彩2：福彩
	
	@Column(name="IS_BYL_STATION",length=45)
	private String isBylStation;//是否为佰艺霖用户1：是0：不是
	
	@Column(name="STATION_CODE",length=45)
	private String bylStationCode;//佰艺霖通行证号
	
	
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
	
	@Column(name="DAIXIAO_IMG",length=45)
	private String daixiaoImg;//代销证图片
	
	@Column(name="ID_NUMBER_FRONT_IMG",length=45)
	private String idNumberFrontImg;//身份证正面图片
	
	@Column(name="ID_NUMBER_BACK_IMG",length=45)
	private String idNumberBackImg;//身份证反面图片
	
	
	@Column(name="ADDDRESS",length=100)
	private String address;//详细地址
	
	@Column(name="STATION_INTERVIEW",length=100)
	private String stationInterview;//彩站介绍
	
	@Column(name="APPROVAL_STATUS",length=45)
	private String approvalStatus;//审批状态（1：审批完成0：审批中）
	
	@Column(name="STATUS",length=45)
	private String status;//彩票站信息审批状态（0：未通过1:通过）
	
	@Column(name="INVITE_CODE",length=45)
	private String inviteCode;//邀请码
	
	@Column(name="FROM_APP",length=45)
	private String fromApp;//app数据录入1:app 0:非app
	
	@Column(name="NOT_ALLOW_REASON")
	private String notAllowReason;//未批准原因
	
	@Column(name="OPENDOOR_TIME",length=100)
	private Timestamp openDoorTime;//开始营业时间
	
	@Column(name="CLOSEDOOR_TIME",length=100)
	private Timestamp closeDoorTime;//停止营业时间
	
	@ManyToOne
	@JoinColumn(name="managerId",referencedColumnName="id")
	private LotterybuyerOrExpert lotteryBuyerOrExpert;
	
	//一个彩票站可以发布多个彩票站公告
	@OneToMany(mappedBy = "lotteryStation", fetch = FetchType.LAZY) 
	private List<LotteryStationNotice> lotteryStationNotices;
	
	//一个彩票站可以发售多种彩种
	@OneToMany(mappedBy = "lotteryStation", fetch = FetchType.LAZY)
	private List<LotteryTypeOfLotteryStationSale> lotteryTypeOfLotteryStationSales;
	
	//一个彩票站可以被多个彩票站关注或绑定
	@OneToMany(mappedBy = "lotteryStation", fetch = FetchType.LAZY)
	private List<RelaBindOfLbuyerorexpertAndLStation> relaBindOfLbuyerorexpertAndLStations;
	
	//一个彩票站可以邀请多个专家成为驻店专家
	@OneToMany(mappedBy = "lotteryStation", fetch = FetchType.LAZY)
	private List<RelaInvitationOfLStationAndLbuyerorexpert> relaInvitationOfLStationAndLbuyerorexperts;

	@OneToMany(mappedBy = "lotteryStation", fetch = FetchType.LAZY)
	private List<RelaBuyLotteryOfLotterybuyer> relaRecordOfLotterybuyers;//可以多个用户在一个彩票站进行彩票的购买
	
	
	

	public String getIdNumberFrontImg() {
		return idNumberFrontImg;
	}

	public void setIdNumberFrontImg(String idNumberFrontImg) {
		this.idNumberFrontImg = idNumberFrontImg;
	}

	public String getIdNumberBackImg() {
		return idNumberBackImg;
	}

	public void setIdNumberBackImg(String idNumberBackImg) {
		this.idNumberBackImg = idNumberBackImg;
	}

	public String getFromApp() {
		return fromApp;
	}

	public void setFromApp(String fromApp) {
		this.fromApp = fromApp;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getNotAllowReason() {
		return notAllowReason;
	}

	public void setNotAllowReason(String notAllowReason) {
		this.notAllowReason = notAllowReason;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getDaixiaoImg() {
		return daixiaoImg;
	}

	public void setDaixiaoImg(String daixiaoImg) {
		this.daixiaoImg = daixiaoImg;
	}

	public String getIsBylStation() {
		return isBylStation;
	}

	public void setIsBylStation(String isBylStation) {
		this.isBylStation = isBylStation;
	}

	public String getBylStationCode() {
		return bylStationCode;
	}

	public void setBylStationCode(String bylStationCode) {
		this.bylStationCode = bylStationCode;
	}

	public LotterybuyerOrExpert getLotteryBuyerOrExpert() {
		return lotteryBuyerOrExpert;
	}

	public void setLotteryBuyerOrExpert(LotterybuyerOrExpert lotteryBuyerOrExpert) {
		this.lotteryBuyerOrExpert = lotteryBuyerOrExpert;
	}

	public List<RelaBuyLotteryOfLotterybuyer> getRelaRecordOfLotterybuyers() {
		return relaRecordOfLotterybuyers;
	}

	public void setRelaRecordOfLotterybuyers(
			List<RelaBuyLotteryOfLotterybuyer> relaRecordOfLotterybuyers) {
		this.relaRecordOfLotterybuyers = relaRecordOfLotterybuyers;
	}

	public List<RelaInvitationOfLStationAndLbuyerorexpert> getRelaInvitationOfLStationAndLbuyerorexperts() {
		return relaInvitationOfLStationAndLbuyerorexperts;
	}

	public void setRelaInvitationOfLStationAndLbuyerorexperts(
			List<RelaInvitationOfLStationAndLbuyerorexpert> relaInvitationOfLStationAndLbuyerorexperts) {
		this.relaInvitationOfLStationAndLbuyerorexperts = relaInvitationOfLStationAndLbuyerorexperts;
	}

	public List<RelaBindOfLbuyerorexpertAndLStation> getRelaBindOfLbuyerorexpertAndLStations() {
		return relaBindOfLbuyerorexpertAndLStations;
	}

	public void setRelaBindOfLbuyerorexpertAndLStations(
			List<RelaBindOfLbuyerorexpertAndLStation> relaBindOfLbuyerorexpertAndLStations) {
		this.relaBindOfLbuyerorexpertAndLStations = relaBindOfLbuyerorexpertAndLStations;
	}

	public List<LotteryTypeOfLotteryStationSale> getLotteryTypeOfLotteryStationSales() {
		return lotteryTypeOfLotteryStationSales;
	}

	public void setLotteryTypeOfLotteryStationSales(
			List<LotteryTypeOfLotteryStationSale> lotteryTypeOfLotteryStationSales) {
		this.lotteryTypeOfLotteryStationSales = lotteryTypeOfLotteryStationSales;
	}

	public List<LotteryStationNotice> getLotteryStationNotices() {
		return lotteryStationNotices;
	}

	public void setLotteryStationNotices(
			List<LotteryStationNotice> lotteryStationNotices) {
		this.lotteryStationNotices = lotteryStationNotices;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStationOwner() {
		return stationOwner;
	}

	public void setStationOwner(String stationOwner) {
		this.stationOwner = stationOwner;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
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

	public String getStationInterview() {
		return stationInterview;
	}

	public void setStationInterview(String stationInterview) {
		this.stationInterview = stationInterview;
	}

	public Timestamp getOpenDoorTime() {
		return openDoorTime;
	}

	public void setOpenDoorTime(Timestamp openDoorTime) {
		this.openDoorTime = openDoorTime;
	}

	public Timestamp getCloseDoorTime() {
		return closeDoorTime;
	}

	public void setCloseDoorTime(Timestamp closeDoorTime) {
		this.closeDoorTime = closeDoorTime;
	}
	
	
	
	
	
}
