package com.BYL.lotteryTools.backstage.lotteryStation.dto;

import org.springframework.web.multipart.MultipartFile;




public class LotteryStationDTO 
{
	private String id;
	
	private String userId;//站主id
	
	private String stationOwner;//站主
	
	private String stationName;//彩票站名称
	
	private String isBylStation;//是否为佰艺霖用户1：是0：不是
	
	private String bylStationCode;//佰艺霖通行证号
	
	private String lotteryType;//彩种类型，1：体彩2：福彩
	
	private String telephone;//站主手机
	
	private String yanzhengma;//验证码
	
	private String stationNumber;//站点号
	
	private String code;//站主的真实姓名
	
	private String password;//密码
	
	private String province;//省
	
	private String provinceName;//省
	
	private String city;//市
	
	private String cityName;//市
	
	private String country;//区
	
	private String coordinate;//坐标
	
	private String address;//详细地址
	
	private String stationInterview;//彩站介绍
	
	private String openDoorTimeStr;//开始营业时间
	
	private String closeDoorTimeStr;//停止营业时间
	
	private String createTimeStr;
	
	private String approvalStatus;//审批状态（1：审批完成0：审批中）
	
	private String approvalStatusName;//审批状态名称
	
	private String status;//彩票站信息审批状态（0：未通过1:通过）
	
	private String statusName;//彩票站信息审批状态名称
	
	private String notAllowReason;//未批准原因
	
	private String idNumber;//站主身份证号
	
	private String daixiaoImg;//代销证图片
	
	private String idNumberFrontImg;//身份证正面图片
	
	private String idNumberBackImg;//身份证背面图片
	
	private MultipartFile daixiaoImgFile;//代销证图片
	
	private MultipartFile idNumberFrontImgFile;//身份证正面图片
	
	private MultipartFile idNumberBackImgFile;//身份证背面图片
	
	private String inviteCode;//邀请码
	
	private String fromApp;//app数据录入1:app 0:非app
	
	private String idNumberFrontImgUrl;
	
	private String idNumberBackImgUrl;
	
	
	private String daixiaoImgUrl;
	
	private String userToken;//用户token
	
	private Integer groupJoinType;//加入方式，1：验证加入 0：自由加入
	
	private String groupIntroduction;//群简介
	
	private MultipartFile groupTouXiangImg;//群头像图片
	
	
	
	
	public Integer getGroupJoinType() {
		return groupJoinType;
	}

	public void setGroupJoinType(Integer groupJoinType) {
		this.groupJoinType = groupJoinType;
	}

	public String getGroupIntroduction() {
		return groupIntroduction;
	}

	public void setGroupIntroduction(String groupIntroduction) {
		this.groupIntroduction = groupIntroduction;
	}

	public MultipartFile getGroupTouXiangImg() {
		return groupTouXiangImg;
	}

	public void setGroupTouXiangImg(MultipartFile groupTouXiangImg) {
		this.groupTouXiangImg = groupTouXiangImg;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getYanzhengma() {
		return yanzhengma;
	}

	public void setYanzhengma(String yanzhengma) {
		this.yanzhengma = yanzhengma;
	}

	public MultipartFile getDaixiaoImgFile() {
		return daixiaoImgFile;
	}

	public void setDaixiaoImgFile(MultipartFile daixiaoImgFile) {
		this.daixiaoImgFile = daixiaoImgFile;
	}

	public MultipartFile getIdNumberFrontImgFile() {
		return idNumberFrontImgFile;
	}

	public void setIdNumberFrontImgFile(MultipartFile idNumberFrontImgFile) {
		this.idNumberFrontImgFile = idNumberFrontImgFile;
	}

	public MultipartFile getIdNumberBackImgFile() {
		return idNumberBackImgFile;
	}

	public void setIdNumberBackImgFile(MultipartFile idNumberBackImgFile) {
		this.idNumberBackImgFile = idNumberBackImgFile;
	}

	public String getIdNumberFrontImgUrl() {
		return idNumberFrontImgUrl;
	}

	public void setIdNumberFrontImgUrl(String idNumberFrontImgUrl) {
		this.idNumberFrontImgUrl = idNumberFrontImgUrl;
	}

	public String getIdNumberBackImgUrl() {
		return idNumberBackImgUrl;
	}

	public void setIdNumberBackImgUrl(String idNumberBackImgUrl) {
		this.idNumberBackImgUrl = idNumberBackImgUrl;
	}

	public String getDaixiaoImgUrl() {
		return daixiaoImgUrl;
	}

	public void setDaixiaoImgUrl(String daixiaoImgUrl) {
		this.daixiaoImgUrl = daixiaoImgUrl;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDaixiaoImg() {
		return daixiaoImg;
	}

	public void setDaixiaoImg(String daixiaoImg) {
		this.daixiaoImg = daixiaoImg;
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getApprovalStatusName() {
		return approvalStatusName;
	}

	public void setApprovalStatusName(String approvalStatusName) {
		this.approvalStatusName = approvalStatusName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getOpenDoorTimeStr() {
		return openDoorTimeStr;
	}

	public void setOpenDoorTimeStr(String openDoorTimeStr) {
		this.openDoorTimeStr = openDoorTimeStr;
	}

	public String getCloseDoorTimeStr() {
		return closeDoorTimeStr;
	}

	public void setCloseDoorTimeStr(String closeDoorTimeStr) {
		this.closeDoorTimeStr = closeDoorTimeStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	
}
