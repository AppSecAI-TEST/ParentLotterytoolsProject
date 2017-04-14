package com.BYL.lotteryTools.backstage.outer.service;

/**
 * 调用融云进行交互的service层
* @Description: TODO(通过融云的用户的相关操作以及群的相关操作) 
* @author banna
* @date 2017年4月14日 上午9:37:25
 */
public interface RongyunImService {

	public String getUserToken(String userId,String username,String imguri);
	
	public String createGroup(String[] joinUserId,String groupId,String groupName);
}
