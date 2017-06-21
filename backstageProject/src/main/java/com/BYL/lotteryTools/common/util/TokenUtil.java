package com.BYL.lotteryTools.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * token工具类
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年6月21日 下午4:10:13
 */
public class TokenUtil {
	
	//token map
	public static Map<String,String> TOKEN_MAP = new HashMap<String, String>();
		

	
	
	/**
	 * 生成token
	* @Title: generateToken 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param username
	* @param @param pwd
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月21日 下午12:07:16 
	* @return String    返回类型 
	* @throws
	 */
	public static String generateToken(String username,String pwd)
	{
		String str = UUID.randomUUID().toString().replace("-", "")+username;//生成32位uuid作为token
		TOKEN_MAP.put(username, str);//放置token
		return str;
	}
	
	/**
	 * 获取token
	* @Title: getToken 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param username
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月21日 下午3:08:01 
	* @return String    返回类型 
	* @throws
	 */
	public static String getToken(String username)
	{
		return TOKEN_MAP.get(username);
	}
	
	/**
	 *校验token
	* @Title: checkToken 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param targetToken:传入的token
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月21日 下午12:04:49 
	* @return String    返回类型 
	* @throws
	 */
	public static boolean  checkToken(String targetToken)
	{ 
		boolean flag = true;
		String tel = TokenUtil.getTelephoneByToken(targetToken);
		String lastToken  = TOKEN_MAP.get(tel);//获取已经缓存的token
		
		if(null != lastToken)
		{
			flag = lastToken.equals(targetToken);
		}
		else
		{//从TOKEN_MAP中无法获取token,可能是因为server重启造成丢失，则将传入的token写入
			TOKEN_MAP.put(tel, targetToken);//放置token
		}
		
		return flag;
	}
	
	//根据token解析出手机号
	public static String getTelephoneByToken(String token)
	{
		String tel = "";
		if(null != token && !"".equals(token) && token.length()>32)
		{
			tel = token.substring(32);//token组成是32位uuid+telephone
		}
		
		return tel;
	}
}
