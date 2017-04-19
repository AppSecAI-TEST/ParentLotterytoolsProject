package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.RelaBuyLotteryOfLotterybuyer;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.RelaHistoryPurchasePredictionOfLotterybuyer;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.RelaPurchasePredictionRecordOfLotteryBuyer;
import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 用户（彩民/专家）
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月28日 下午3:03:15
 */
@Entity
@Table(name="T_LT_LOTTERYBUYER_OR_EXPERT")
public class LotterybuyerOrExpert extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	private String id;
	
	@Column(name="CODE")
	private String code;//用户名，昵称
	
	@Column(name="TOU_XIANG",length=45)
	private String touXiang;//头像
	
	@Column(name="NAME")
	private String name;//姓名
	
	@Column(name="CAILIAO_NAME")
	private String cailiaoName;//彩聊名
	
	@Column(name="PASSWORD")
	private String password;//密码
	
	@Column(name="TELEPHONE")
	private String telephone;
	
	@Column(name="IS_PHONE")
	private String isPhone;//是否为手机用户（0：手机用户 1：其他用户）
	
	@Column(name="PROVINCE_CODE")
	private String provinceCode;
	
	@Column(name="CITY_CODE")
	private String cityCode;
	
	@Column(name="REGION_CODE")
	private String regionCode;
	
	@Column(name="ADDRESS")
	private String address;
	
	
	@Column(name="SEX")
	private String sex;//性别
	
	@Column(name="SIGNATURE")
	private String signature;//个性签名
	
	@Column(name="COORDINATE")
	private String coordinate;//坐标
	
	
	@Column(name="RONGYUN_ID")
	private String rongyunId;//融云id,即时通讯使用
	
	@Column(name="IS_EXPERT")
	private String isExpert;//是否为彩民 1：彩民 0：非彩民
	
	@Column(name="IS_STATION_OWNER")
	private String isStationOwner;//是否为站主1：是0:不是
	
	@Column(name="HANDSEL")
	private BigDecimal handSel;//彩金余额
	
	@Column(name="COLOR_COINS")
	private BigDecimal colorCoins;//彩币余额
	
	@Column(name="IS_LOTTERY_BUYER")
	private String isLotteryBuyer;//是否为彩民 1：彩民 0：非彩民
	
	@Column(name="ID_NUMBER",length=45)
	private String idNumber;//身份证号
	
	@Column(name="ID_NUMBER_FRONT_IMG",length=45)
	private String idNumberFrontImg;//身份证正面图片
	
	@Column(name="ID_NUMBER_BACK_IMG",length=45)
	private String idNumberBackImg;//身份证反面图片
	
	@Column(name="TOKEN",length=45)
	private String token;//用户融云token
	
	//一个用户可以进行多次彩金、彩币的充值
	@OneToMany(mappedBy="lotterybuyerOrExpert" , fetch=FetchType.LAZY)
	private List<RechargeForHandselOrColorcoins> rechargeForHandselOrColorcoins;
	
	//一个用户可以对多个彩票站进行关注，也可以绑定彩票站
	@OneToMany(mappedBy="lotterybuyerOrExpert" ,fetch=FetchType.LAZY)
	private List<RelaBindOfLbuyerorexpertAndLStation> relaBindOfLbuyerorexpertAndLStations;
	
	//一个专家可以被多个彩站邀请成为驻店专家
	@OneToMany(mappedBy="lotterybuyerOrExpert" ,fetch=FetchType.LAZY)
	private List<RelaInvitationOfLStationAndLbuyerorexpert> relaInvitationOfLStationAndLbuyerorexperts;
	
	//一个用户可以多次购买彩票
	@OneToMany(mappedBy="lotterybuyerOrExpert" ,fetch=FetchType.LAZY)
	private List<RelaBuyLotteryOfLotterybuyer> relaRecordOfLotterybuyers;
	

	//一个用户可以购买多个预测方案
	@OneToMany(mappedBy="lotterybuyerOrExpert" ,fetch=FetchType.LAZY)
	private List<RelaPurchasePredictionRecordOfLotteryBuyer> relaPurchaseRecordOfLotteryBuyers;
	
	@OneToMany(mappedBy="lotterybuyerOrExpert" ,fetch=FetchType.LAZY)
	private List<RelaHistoryPurchasePredictionOfLotterybuyer> relaHistoryPurchasePredictionOfLotterybuyers;
	
	//一个用户可以成为多个彩票站的站主
	@OneToMany(mappedBy="lotteryBuyerOrExpert",fetch=FetchType.LAZY)
	private List<LotteryStation> lotteryStations;
	
	@OneToMany(mappedBy="lotteryBuyerOrExpert",fetch=FetchType.LAZY)
	private List<LotteryGroup> lotteryGroups;//一个用户可以成为多个群的群主
	
	
	
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCailiaoName() {
		return cailiaoName;
	}

	public void setCailiaoName(String cailiaoName) {
		this.cailiaoName = cailiaoName;
	}

	public String getTouXiang() {
		return touXiang;
	}

	public void setTouXiang(String touXiang) {
		this.touXiang = touXiang;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<LotteryGroup> getLotteryGroups() {
		return lotteryGroups;
	}

	public void setLotteryGroups(List<LotteryGroup> lotteryGroups) {
		this.lotteryGroups = lotteryGroups;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

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

	public String getIsStationOwner() {
		return isStationOwner;
	}

	public void setIsStationOwner(String isStationOwner) {
		this.isStationOwner = isStationOwner;
	}

	public List<LotteryStation> getLotteryStations() {
		return lotteryStations;
	}

	public void setLotteryStations(List<LotteryStation> lotteryStations) {
		this.lotteryStations = lotteryStations;
	}

	public String getRongyunId() {
		return rongyunId;
	}

	public void setRongyunId(String rongyunId) {
		this.rongyunId = rongyunId;
	}

	public List<RelaHistoryPurchasePredictionOfLotterybuyer> getRelaHistoryPurchasePredictionOfLotterybuyers() {
		return relaHistoryPurchasePredictionOfLotterybuyers;
	}

	public void setRelaHistoryPurchasePredictionOfLotterybuyers(
			List<RelaHistoryPurchasePredictionOfLotterybuyer> relaHistoryPurchasePredictionOfLotterybuyers) {
		this.relaHistoryPurchasePredictionOfLotterybuyers = relaHistoryPurchasePredictionOfLotterybuyers;
	}

	public List<RelaPurchasePredictionRecordOfLotteryBuyer> getRelaPurchaseRecordOfLotteryBuyers() {
		return relaPurchaseRecordOfLotteryBuyers;
	}

	public void setRelaPurchaseRecordOfLotteryBuyers(
			List<RelaPurchasePredictionRecordOfLotteryBuyer> relaPurchaseRecordOfLotteryBuyers) {
		this.relaPurchaseRecordOfLotteryBuyers = relaPurchaseRecordOfLotteryBuyers;
	}

	public List<RelaBuyLotteryOfLotterybuyer> getRelaRecordOfLotterybuyers() {
		return relaRecordOfLotterybuyers;
	}

	public void setRelaRecordOfLotterybuyers(
			List<RelaBuyLotteryOfLotterybuyer> relaRecordOfLotterybuyers) {
		this.relaRecordOfLotterybuyers = relaRecordOfLotterybuyers;
	}

	public List<RelaBindOfLbuyerorexpertAndLStation> getRelaBindOfLbuyerorexpertAndLStations() {
		return relaBindOfLbuyerorexpertAndLStations;
	}

	public void setRelaBindOfLbuyerorexpertAndLStations(
			List<RelaBindOfLbuyerorexpertAndLStation> relaBindOfLbuyerorexpertAndLStations) {
		this.relaBindOfLbuyerorexpertAndLStations = relaBindOfLbuyerorexpertAndLStations;
	}

	public List<RelaInvitationOfLStationAndLbuyerorexpert> getRelaInvitationOfLStationAndLbuyerorexperts() {
		return relaInvitationOfLStationAndLbuyerorexperts;
	}

	public void setRelaInvitationOfLStationAndLbuyerorexperts(
			List<RelaInvitationOfLStationAndLbuyerorexpert> relaInvitationOfLStationAndLbuyerorexperts) {
		this.relaInvitationOfLStationAndLbuyerorexperts = relaInvitationOfLStationAndLbuyerorexperts;
	}

	public List<RechargeForHandselOrColorcoins> getRechargeForHandselOrColorcoins() {
		return rechargeForHandselOrColorcoins;
	}

	public void setRechargeForHandselOrColorcoins(
			List<RechargeForHandselOrColorcoins> rechargeForHandselOrColorcoins) {
		this.rechargeForHandselOrColorcoins = rechargeForHandselOrColorcoins;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIsPhone() {
		return isPhone;
	}

	public void setIsPhone(String isPhone) {
		this.isPhone = isPhone;
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

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getIsLotteryBuyer() {
		return isLotteryBuyer;
	}

	public void setIsLotteryBuyer(String isLotteryBuyer) {
		this.isLotteryBuyer = isLotteryBuyer;
	}

	public String getIsExpert() {
		return isExpert;
	}

	public void setIsExpert(String isExpert) {
		this.isExpert = isExpert;
	}

	public BigDecimal getHandSel() {
		return handSel;
	}

	public void setHandSel(BigDecimal handSel) {
		this.handSel = handSel;
	}

	public BigDecimal getColorCoins() {
		return colorCoins;
	}

	public void setColorCoins(BigDecimal colorCoins) {
		this.colorCoins = colorCoins;
	}
	
	
}
