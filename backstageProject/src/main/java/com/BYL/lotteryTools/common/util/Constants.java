package com.BYL.lotteryTools.common.util;

public class Constants {
	
	/** 
	  * @Fields IS_DELETED : 常量类标识删除
	  */ 
	public static final String IS_DELETED = "0";
	
	public static final String IS_NOT_DELETED = "1";
	
	public static final String ORIGIN_AUTH_ID = "1";//权限表的虚拟根节点值
	
	public static final String CITY_ALL = "all";//市级的“全部”选项对应的值
	
	public static final String CITY_ALL_NAME = "全部";//市级的“全部”选项对应的显示名称
	
	public static final String DISTRICT_ALL = "all";//区级的“全部”选项对应的值
	
	public static final String DISTRICT_ALL_NAME = "全部";//区级的“全部”选项对应的显示名称
	
	public static final String REGION_ALL = "all";//市级的“全部”选项对应的值
	
	public static final String REGION_ALL_NAME = "全部";//市级的“全部”选项对应的显示名称
	
	public static final String PROVINCE_ALL = "all";//省级的“全部”选项对应的值
	
	public static final String PROVINCE_ALL_NAME = "请选择省份";//省级的“全部”选项对应的显示名称
	
	public static final String PRODUCT_XL_ALL = "all";//产品小类别的“全部”选项对应的值
	
	public static final String PRODUCT_XL_ALL_NAME = "全部";//产品小类别的“全部”选项对应的显示名称
	
	public static final String PRODUCT_ZL_ALL = "all";//产品中类别的“全部”选项对应的值
	
	public static final String PRODUCT_ZL_ALL_NAME = "全部";//产品中类别的“全部”选项对应的显示名称
	
	public static final String GOODS_SHELVES = "1";//商品上架
	
	public static final String GOODS_OFF_SHELVES = "2";//商品下架
	
	public static final String GOODS_STAY_ON_SHELVES = "0";//商品待上架
	
	public static final String PAY_MODE_ONE = "0";//支付方式：现金支付
	
	public static final String PAY_MODE_TWO = "1";//支付方式：转账支付
	
	public static final String ROLE_FINANCIAL_MANAGER_ID = "1";//“财务管理员”角色的id
	
	public static final String ROLE_PROXY_ID = "2";//“代理”角色的id
	
	public static final String ROLE_CITY_CENTER_ID = "4";//“市中心”角色id
	
	public static final String ROLE_PROVINCE_CENTER_ID = "3";//“省中心”角色id
	
	public static final String LOTTERY_TYPE_TC = "1";//彩种：体彩
	
	public static final String LOTTERY_TYPE_FC = "2";//彩种：福彩
	
	public static final String LOTTERY_TYPE_SJ = "0";//彩种：双机
	
	//代理模块内容start
	//固化市场专员角色编码，目地是通过编码查找对应的市场人员
	public static final String ROLE_SCZY_CODE = "SC_ZY";
	
	public static final String ROLE_SCDL_CODE = "3";
	//代理模块内容end
	
	
	public static final String MESSAGE_STR="message";
	
	public static final String FLAG_STR="flag";
	
//	public static final String TOKEN_FLAG_STR="tokenFlag";
	
	public static final String ERROR_STR="error:";
	
	public static final String CENTER_CITY_GROUP_ID="4";//中心群id
	public static final String COMPANY_GROUP_ID="5";//公司群id
	
	public static final int SERIAL_NUM_LEN = 6;//流水号中自动生成的数字位数
	
	//操作码：
	public static final String CODE_STR="resultCode";
	
	public static final String SUCCESS_CODE="200";//操作成功
	
	public static final String TOKEN_IS_PASS_CODE="301";//token过期
	public static final String TOKEN_IS_NOT_EXIST_CODE="302";//token不存在
	
	public static final String FAIL_CODE="400";//业务获取错误,业务处理错误的捕捉返回
	public static final String FAIL_CODE_OF_NO_NEW_APPVERSION="401";//应用没有最新版本
	public static final String FAIL_CODE_OF_NO_APP="402";//没有应用名为所传值的应用
	public static final String FAIL_CODE_OF_STAION_IS_REGISTED="403";//彩站已注册
	public static final String FAIL_CODE_OF_DELETE_GROUP="404";//群删除失败
	public static final String FAIL_CODE_OF_JOIN_GROUP_MEMBER="405";//加群失败，超出群可以加入的群成员数
	public static final String FAIL_CODE_OF_CREATE_GROUP="406";//群创建失败
	public static final String FAIL_CODE_OF_PWD_ERROR="407";//登录密码失败
	public static final String FAIL_CODE_OF_TEL_IS_REGITED="408";//手机号已被注册
	public static final String FAIL_CODE_OF_TEL_IS_NOT_EXIST="409";//用户名不存在
	public static final String FAIL_CODE_OF_ORIGIN_PWD_ERROR="410";//原密码输入错误
	public static final String FAIL_CODE_OF_CAILIAO_IS_NOT_ONLY="411";//彩聊号不唯一
	public static final String FAIL_CODE_OF_GROUP_IS_NOT_FIND="412";//未找到群
	
	public static final String SERVER_FAIL_CODE="500";//服务器异常，服务器异常抛出
	
	public static final String YZM_INPUT_ERROR_CODE="601";//验证码输入错误
	public static final String YZM_GET_ERROR_CODE="602";//验证码过期

}
