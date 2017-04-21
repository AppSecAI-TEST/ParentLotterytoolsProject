package com.BYL.lotteryTools.common.bean;

/**
 * 
  * @ClassName: resultBean 
  * @Description: TODO(杩斿洖鏁版嵁bean) 
  * @author bann@sdfcp.com
  * @date 2015骞�0鏈�鏃�涓嬪崍4:46:57 
  *
 */
public class ResultBean
{
	private String message;//杩斿洖鎻愮ず淇℃伅
	
	private String status;//杩斿洖鐘舵�锛坰uccess of fail锛�
	
	private boolean isExist;//褰撳墠鍊兼槸鍚﹀瓨鍦�
	
	private boolean flag; 
	
	private boolean isProxy;//鏄惁鎷ユ湁浠ｇ悊瑙掕壊
	
	private boolean isFinancialManager;//鏄惁鎷ユ湁璐㈡斂绠＄悊鍛樿鑹�
	
	private boolean useFlag;//鏄惁鍙互浣跨敤
	
	private boolean isCityCenterManager;//鏄惁鎷ュ競涓績瑙掕壊
	
	private boolean isProvinceCenterManager;//鏄惁鎷ョ渷涓績瑙掕壊
	
	private String province;
	
	private String city;
	
	private String provinceName;
	
	private String cityName;
	
	private String lotteryType;
	
	
	
	
	
	
	

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
