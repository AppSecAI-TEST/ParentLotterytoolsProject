package com.BYL.lotteryTools.backstage.lotteryGroup.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;


public class LotteryGroupDTO {

	private String id;//群id，群融云id
	
	private String name;//群名称
	
	private String groupNumber;//群号（8位流水号）
	
	private String groupLevel;//群等级，与群等级表关联
	
	private String groupRobotID;//机器人id（与用户表关联）,区域彩种高频一个机器人
	
	private String introduction;//群简介
	
	private String touXiang;//头像图片
	
	private String touXiangImgUrl;//头像图片路径
	
	private MultipartFile touXiangImg;//头像图片
	
	private String ownerId;//群主id
	
	private String lotteryType;//群分类，1：体彩，2：福彩 3：竞彩 4.中心群 5：公司群
	
	private String detailLotteryType;//详细群彩种分类，1：体彩，2：福彩 
	
	private String province;//省
	
	private String city;//市
	
	private String upLevel;//升级
	
	private Integer joinType;//加入方式，1：验证加入 0：自由加入
	
	private Integer fabuKj;//是否发布开奖画面（1：发布0：不发布）
	
	private Integer fabuZs;//是否发布走势画面（1：发布0：不发布）
	
	private Integer ssYlChaxun;//实时遗漏查询（1：是0：否）
	
	private Integer ssZjChaxun;//实时专家查询（1：是0：否）
	
	private Integer ssKjChaxun;//是否实时开奖查询（1：是0：否）
	
	private Integer noticeReview;//群公告是否审核，0：不审核 1：审核
	
	private String createTimeStr;//创建时间字符串
	
	private String provinceName;
	
	private String cityName;
	
	private String ownerName;//群主昵称
	
	private String haveStation;//当前群主是否有彩站，1：有认证彩站 0：没有
	
	private String groupQRImg;//群二维码图片
	
	private String isJoinOfUser;//指定用户是否已加入此群 1：已加入 0:未加入
	
	private String isOwner;//指定用户是否为此群群主
	
	private String alreadyApplyOfUser;//当前用户已经申请加入（1：已申请（正在审核） 0：未申请）
	
	private String isTop;//是否置顶（0：未置顶 1：置顶）
	
	private String userToken;//用户token
	
	private String[] lotteryNumber;//高频彩种开奖号码个数
	
	private String[] gaoPinLotteryPlay;//高频彩种玩法名称
	private String[] gaoPinLotteryPlayId;//高频彩种玩法id数组
	
	private List<LotterybuyerOrExpertDTO> userList;
	
	
	
	

	public String[] getGaoPinLotteryPlayId() {
		return gaoPinLotteryPlayId;
	}

	public void setGaoPinLotteryPlayId(String[] gaoPinLotteryPlayId) {
		this.gaoPinLotteryPlayId = gaoPinLotteryPlayId;
	}

	public List<LotterybuyerOrExpertDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<LotterybuyerOrExpertDTO> userList) {
		this.userList = userList;
	}

	public String[] getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String[] lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}


	public String[] getGaoPinLotteryPlay() {
		return gaoPinLotteryPlay;
	}

	public void setGaoPinLotteryPlay(String[] gaoPinLotteryPlay) {
		this.gaoPinLotteryPlay = gaoPinLotteryPlay;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
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

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getAlreadyApplyOfUser() {
		return alreadyApplyOfUser;
	}

	public void setAlreadyApplyOfUser(String alreadyApplyOfUser) {
		this.alreadyApplyOfUser = alreadyApplyOfUser;
	}

	public String getIsOwner() {
		return isOwner;
	}

	public void setIsOwner(String isOwner) {
		this.isOwner = isOwner;
	}

	public String getIsJoinOfUser() {
		return isJoinOfUser;
	}

	public void setIsJoinOfUser(String isJoinOfUser) {
		this.isJoinOfUser = isJoinOfUser;
	}

	public String getGroupQRImg() {
		return groupQRImg;
	}

	public void setGroupQRImg(String groupQRImg) {
		this.groupQRImg = groupQRImg;
	}

	public String getHaveStation() {
		return haveStation;
	}

	public void setHaveStation(String haveStation) {
		this.haveStation = haveStation;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getTouXiangImgUrl() {
		return touXiangImgUrl;
	}

	public void setTouXiangImgUrl(String touXiangImgUrl) {
		this.touXiangImgUrl = touXiangImgUrl;
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

	public Integer getJoinType() {
		return joinType;
	}

	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}

	public String getUpLevel() {
		return upLevel;
	}

	public void setUpLevel(String upLevel) {
		this.upLevel = upLevel;
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

	public MultipartFile getTouXiangImg() {
		return touXiangImg;
	}

	public void setTouXiangImg(MultipartFile touXiangImg) {
		this.touXiangImg = touXiangImg;
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

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	
}
