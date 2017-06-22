package com.BYL.lotteryTools.common.bean;

public class ResultBean
{
	private String message;
	
	private String status;
	
	private boolean isExist;
	
	private boolean flag; 
	
//	private boolean tokenFlag;
	
	private boolean isProxy;
	
	private boolean isFinancialManager;
	
	private boolean useFlag;
	
	private boolean isCityCenterManager;
	
	private boolean isProvinceCenterManager;
	
	private String province;
	
	private String city;
	
	private String provinceName;
	
	private String cityName;
	
	private String lotteryType;
	
	private String code;//操作返回码：200（操作成功） 100（普通错误） 300（token错误） 301（token过期） 302（token没有值）500（服务器未应答）
	
	
	
	
	
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/*public boolean isTokenFlag() {
		return tokenFlag;
	}

	public void setTokenFlag(boolean tokenFlag) {
		this.tokenFlag = tokenFlag;
	}
*/
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
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

	public boolean isProvinceCenterManager() {
		return isProvinceCenterManager;
	}

	public void setProvinceCenterManager(boolean isProvinceCenterManager) {
		this.isProvinceCenterManager = isProvinceCenterManager;
	}

	public boolean isCityCenterManager() {
		return isCityCenterManager;
	}

	public void setCityCenterManager(boolean isCityCenterManager) {
		this.isCityCenterManager = isCityCenterManager;
	}

	public boolean isUseFlag() {
		return useFlag;
	}

	public void setUseFlag(boolean useFlag) {
		this.useFlag = useFlag;
	}

	public boolean isProxy() {
		return isProxy;
	}

	public void setProxy(boolean isProxy) {
		this.isProxy = isProxy;
	}

	public boolean isFinancialManager() {
		return isFinancialManager;
	}

	public void setFinancialManager(boolean isFinancialManager) {
		this.isFinancialManager = isFinancialManager;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
	
}
