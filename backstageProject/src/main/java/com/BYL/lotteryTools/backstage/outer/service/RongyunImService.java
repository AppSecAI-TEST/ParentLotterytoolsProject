package com.BYL.lotteryTools.backstage.outer.service;


/**
 * 调用融云进行交互的service层
* @Description: TODO(通过融云的用户的相关操作以及群的相关操作) 
* @author banna
* @date 2017年4月14日 上午9:37:25
 */
public interface RongyunImService {

	public String blockUser(String userId,Integer minute);
	
	public String getUserToken(String userId,String username,String imguri);
	
	/**
	 * 将用户加入到群（加群之前判断当前群是否已经人数超过额度，超过则不可以加入了）
	* @Title: joinUserInGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param groupJoinUserId：要加入群的用户数组
	* @param @param groupId：群id
	* @param @param groupName：群名称
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月19日 下午4:47:40 
	* @return String    返回类型 
	* @throws
	 */
	public String joinUserInGroup(String[] groupJoinUserId,String groupId,String groupName);
	
	/**
	 * 用户从群退出
	* @Title: quitUserFronGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param groupQuitUserId：要退出群的用户数组
	* @param @param groupId：要退出群的群id
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月19日 下午4:51:29 
	* @return String    返回类型 
	* @throws
	 */
	public String quitUserFronGroup(String[] groupQuitUserId,String groupId);
	
	/**
	 * 删除群
	* @Title: groupDismiss 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId：操作删除群的用户id
	* @param @param dismissGroupId：要删除的群的群id
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月19日 下午4:56:23 
	* @return String    返回类型 
	* @throws
	 */
	public String groupDismiss(String userId,String dismissGroupId);
	
	public String createGroup(String[] joinUserId,String groupId,String groupName);
	
}
