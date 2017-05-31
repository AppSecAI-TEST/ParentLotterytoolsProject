package com.BYL.lotteryTools.backstage.outer.service;

import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.CodeSuccessResult;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.SMSSendCodeResult;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.SMSVerifyCodeResult;


/**
 * 调用融云进行交互的service层
* @Description: TODO(通过融云的用户的相关操作以及群的相关操作) 
* @author banna
* @date 2017年4月14日 上午9:37:25
 */
public interface RongyunImService {

	/**
	 * 封锁用户
	* @Title: blockUser 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @param minute
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 下午2:21:07 
	* @return String    返回类型 
	* @throws
	 */
	public CodeSuccessResult blockUser(String userId,Integer minute);
	
	/**
	 * 创建用户（获取用户token）
	* @Title: getUserToken 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @param username
	* @param @param imguri
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 下午2:21:14 
	* @return String    返回类型 
	* @throws
	 */
	public String getUserToken(String userId,String username,String imguri);
	
	/**
	 * 刷新用户信息
	* @Title: refreshUser 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @param username
	* @param @param imguri
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月26日 下午2:30:58 
	* @return CodeSuccessResult    返回类型 
	* @throws
	 */
	public CodeSuccessResult refreshUser(String userId,String username,String imguri);
	
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
	public CodeSuccessResult joinUserInGroup(String[] groupJoinUserId,String groupId,String groupName);
	
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
	public CodeSuccessResult quitUserFronGroup(String[] groupQuitUserId,String groupId);
	
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
	public CodeSuccessResult groupDismiss(String userId,String dismissGroupId);
	
	/**
	 * 创建群
	* @Title: createGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param joinUserId
	* @param @param groupId
	* @param @param groupName
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 下午2:20:59 
	* @return CodeSuccessResult    返回类型 
	* @throws
	 */
	public CodeSuccessResult createGroup(String[] joinUserId,String groupId,String groupName);
	
	/**
	 * 发送验证码
	* @Title: sendCode 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param mobile
	* @param @param templateId
	* @param @param region
	* @param @param verifyId
	* @param @param verifyCode
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年4月24日 下午2:20:52 
	* @return SMSSendCodeResult    返回类型 
	* @throws
	 */
	public SMSSendCodeResult sendCode(String mobile, String templateId, String region, String verifyId, String verifyCode) throws Exception;
	
	/**
	 * 校验验证码
	* @Title: verifyCode 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param sessionId
	* @param @param code
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年4月24日 下午2:20:44 
	* @return SMSVerifyCodeResult    返回类型 
	* @throws
	 */
	public SMSVerifyCodeResult verifyCode(String sessionId, String code) throws Exception;
	
	/**
	 * 刷新群信息
	* @Title: refreshGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param groupId
	* @param @param groupName
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 下午2:20:36 
	* @return CodeSuccessResult    返回类型 
	* @throws
	 */
	public CodeSuccessResult refreshGroup(String groupId,String groupName);
	
	/**
	 * 发送文本消息到群
	* @Title: sendMessgeToGroups 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param fromUseId
	* @param @param messagePublishGroupToGroupId
	* @param @param message
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月27日 上午10:53:33 
	* @return CodeSuccessResult    返回类型 
	* @throws
	 */
	public CodeSuccessResult sendMessgeToGroups(String fromUseId,String[] messagePublishGroupToGroupId,String message);
	
	/**
	 * 发送图片消息到群
	* @Title: sendImgMessgeToGroups 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param fromUseId
	* @param @param messagePublishGroupToGroupId
	* @param @param message
	* @param @param imageUri
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月27日 下午12:11:58 
	* @return CodeSuccessResult    返回类型 
	* @throws
	 */
	public CodeSuccessResult sendImgMessgeToGroups(String fromUseId,String[] messagePublishGroupToGroupId,String message,String imageUri);
	
}
