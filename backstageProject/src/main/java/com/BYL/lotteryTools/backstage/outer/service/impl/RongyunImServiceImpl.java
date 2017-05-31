package com.BYL.lotteryTools.backstage.outer.service.impl;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.outer.repository.TxtMessage;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.ImgMessage;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.RongCloud;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.CodeSuccessResult;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.SMSSendCodeResult;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.SMSVerifyCodeResult;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.TokenResult;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.util.GsonUtil;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.util.HostType;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.util.HttpUtil;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;

@Service("/rongyunService")
@Transactional(propagation=Propagation.REQUIRED)
public class RongyunImServiceImpl implements RongyunImService 
{
	private Logger logger = LoggerFactory.getLogger(RongyunImServiceImpl.class);
	String appKey = "82hegw5u83v8x";//appkey
	String appSecret = "z2VxpghbOvscFu";//上面key的secret
	
	
	//封禁用户
	public CodeSuccessResult blockUser(String userId,Integer minute)
	{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		CodeSuccessResult userBlockResult = null;
		try
		{
			userBlockResult = rongCloud.user.block(userId, minute);//minute:封禁多少分钟
		}
		catch(Exception e)
		{
			logger.error("error:", e);
		}
		
		return userBlockResult;
	}
	
	public CodeSuccessResult refreshUser(String userId,String username,String imguri)
	{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		CodeSuccessResult result = null;
		try {
			result = rongCloud.user.refresh(userId, username, imguri);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	//新建用户，获取用户token
	public String getUserToken(String userId,String username,String imguri)
	{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		TokenResult userGetTokenResult = null;
		try
		{
			userGetTokenResult = rongCloud.user.getToken(userId, username, imguri);//"http://www.rongcloud.cn/images/logo.png"
			System.out.println("getToken:  " + userGetTokenResult.toString());
			
			/*// 检查用户在线状态 方法 
			CheckOnlineResult userCheckOnlineResult = rongCloud.user.checkOnline("userId1");
			System.out.println("checkOnline:  " + userCheckOnlineResult.toString());
			
			// 封禁用户方法（每秒钟限 100 次） 
			CodeSuccessResult userBlockResult = rongCloud.user.block("userId4", 10);
			System.out.println("block:  " + userBlockResult.toString());
			
			// 解除用户封禁方法（每秒钟限 100 次） 
			CodeSuccessResult userUnBlockResult = rongCloud.user.unBlock("userId2");
			System.out.println("unBlock:  " + userUnBlockResult.toString());
			
			// 获取被封禁用户方法（每秒钟限 100 次） 
			QueryBlockUserResult userQueryBlockResult = rongCloud.user.queryBlock();
			System.out.println("queryBlock:  " + userQueryBlockResult.toString());
			
			// 添加用户到黑名单方法（每秒钟限 100 次） 
			CodeSuccessResult userAddBlacklistResult = rongCloud.user.addBlacklist("userId1", "userId2");
			System.out.println("addBlacklist:  " + userAddBlacklistResult.toString());
			
			// 获取某用户的黑名单列表方法（每秒钟限 100 次） 
			QueryBlacklistUserResult userQueryBlacklistResult = rongCloud.user.queryBlacklist("userId1");
			System.out.println("queryBlacklist:  " + userQueryBlacklistResult.toString());
			
			// 从黑名单中移除用户方法（每秒钟限 100 次） 
			CodeSuccessResult userRemoveBlacklistResult = rongCloud.user.removeBlacklist("userId1", "userId2");
			System.out.println("removeBlacklist:  " + userRemoveBlacklistResult.toString());*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null!= userGetTokenResult.getToken()?userGetTokenResult.getToken():"";
	}
	
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
	public CodeSuccessResult joinUserInGroup(String[] groupJoinUserId,String groupId,String groupName)
	{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);//这个时候可以初始化各个对象的appkey和appSecreat
		CodeSuccessResult groupJoinResult = null;
		try {
			groupJoinResult = rongCloud.group.join(groupJoinUserId, groupId, groupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupJoinResult;
	}
	
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
	public CodeSuccessResult quitUserFronGroup(String[] groupQuitUserId,String groupId)
	{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);//这个时候可以初始化各个对象的appkey和appSecreat
		CodeSuccessResult groupQuitResult = null;
		try {
			groupQuitResult = rongCloud.group.quit(groupQuitUserId,groupId);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return groupQuitResult;
	}
	
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
	public CodeSuccessResult groupDismiss(String userId,String dismissGroupId)
	{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);//这个时候可以初始化各个对象的appkey和appSecreat
		CodeSuccessResult groupDismissResult = null;
		try {
			groupDismissResult = rongCloud.group.dismiss(userId, dismissGroupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupDismissResult;
	}
	
	public CodeSuccessResult refreshGroup(String groupId,String groupName)
	{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);//这个时候可以初始化各个对象的appkey和appSecreat
		CodeSuccessResult groupDismissResult = null;
		try {
			groupDismissResult = rongCloud.group.refresh(groupId,groupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupDismissResult;
	}
	
	public CodeSuccessResult createGroup(String[] joinUserId,String groupId,String groupName)
	{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);//这个时候可以初始化各个对象的appkey和appSecreat
		CodeSuccessResult groupCreateResult = null;
		System.out.println("************************Group********************");
		// 创建群组方法（创建群组，并将用户加入该群组，用户将可以收到该群的消息，同一用户最多可加入 500 个群，每个群最大至 3000 人，App 内的群组数量没有限制.注：其实本方法是加入群组方法 /group/join 的别名。） 
		try
		{
//			String[] groupCreateUserId = {"userId1","userid2","userId3"};
			groupCreateResult = rongCloud.group.create(joinUserId, groupId, groupName);
			System.out.println("create:  " + groupCreateResult.toString());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		/*// 同步用户所属群组方法（当第一次连接融云服务器时，需要向融云服务器提交 userId 对应的用户当前所加入的所有群组，此接口主要为防止应用中用户群信息同融云已知的用户所属群信息不同步。） 
		GroupInfo[] groupSyncGroupInfo = {new GroupInfo("groupId1","groupName1" ), new GroupInfo("groupId2","groupName2" ), new GroupInfo("groupId3","groupName3" )};
		CodeSuccessResult groupSyncResult = rongCloud.group.sync("userId1", groupSyncGroupInfo);
		System.out.println("sync:  " + groupSyncResult.toString());
		
		// 刷新群组信息方法 
		CodeSuccessResult groupRefreshResult = rongCloud.group.refresh("groupId1", "newGroupName");
		System.out.println("refresh:  " + groupRefreshResult.toString());
		
		// 将用户加入指定群组，用户将可以收到该群的消息，同一用户最多可加入 500 个群，每个群最大至 3000 人。 
		String[] groupJoinUserId = {"userId2","userid3","userId4"};
		CodeSuccessResult groupJoinResult = rongCloud.group.join(groupJoinUserId, "groupId1", "TestGroup");
		System.out.println("join:  " + groupJoinResult.toString());
		
		// 查询群成员方法 
		GroupUserQueryResult groupQueryUserResult = rongCloud.group.queryUser("groupId1");
		System.out.println("queryUser:  " + groupQueryUserResult.toString());
		
		// 退出群组方法（将用户从群中移除，不再接收该群组的消息.） 
		String[] groupQuitUserId = {"userId2","userid3","userId4"};
		CodeSuccessResult groupQuitResult = rongCloud.group.quit(groupQuitUserId, "TestGroup");
		System.out.println("quit:  " + groupQuitResult.toString());
		
		// 添加禁言群成员方法（在 App 中如果不想让某一用户在群中发言时，可将此用户在群组中禁言，被禁言用户可以接收查看群组中用户聊天信息，但不能发送消息。） 
		CodeSuccessResult groupAddGagUserResult = rongCloud.group.addGagUser("userId1", "groupId1", "1");
		System.out.println("addGagUser:  " + groupAddGagUserResult.toString());
		
		// 查询被禁言群成员方法 
		ListGagGroupUserResult groupLisGagUserResult = rongCloud.group.lisGagUser("groupId1");
		System.out.println("lisGagUser:  " + groupLisGagUserResult.toString());
		
		// 移除禁言群成员方法 
		String[] groupRollBackGagUserUserId = {"userId2","userid3","userId4"};
		CodeSuccessResult groupRollBackGagUserResult = rongCloud.group.rollBackGagUser(groupRollBackGagUserUserId, "groupId1");
		System.out.println("rollBackGagUser:  " + groupRollBackGagUserResult.toString());
		
		// 解散群组方法。（将该群解散，所有用户都无法再接收该群的消息。） 
		CodeSuccessResult groupDismissResult = rongCloud.group.dismiss("userId1", "groupId1");
		System.out.println("dismiss:  " + groupDismissResult.toString());*/
		
		return groupCreateResult;
	}
	
	
	/**
	 * 发送短信验证码方法。 
	 * 
	 * @param  mobile:接收短信验证码的目标手机号，每分钟同一手机号只能发送一次短信验证码，同一手机号 1 小时内最多发送 3 次。（必传）
	 * @param  templateId:短信模板 Id，在开发者后台->短信服务->服务设置->短信模版中获取。（必传）
	 * @param  region:手机号码所属国家区号，目前只支持中图区号 86）
	 * @param  verifyId:图片验证标识 Id ，开启图片验证功能后此参数必传，否则可以不传。在获取图片验证码方法返回值中获取。
	 * @param  verifyCode:图片验证码，开启图片验证功能后此参数必传，否则可以不传。
	 *
	 * @return SMSSendCodeResult
	 **/
	public SMSSendCodeResult sendCode(String mobile, String templateId, String region, String verifyId, String verifyCode) throws Exception {
		if (mobile == null) {
			throw new IllegalArgumentException("Paramer 'mobile' is required");
		}
		
		if (templateId == null) {
			throw new IllegalArgumentException("Paramer 'templateId' is required");
		}
		
		if (region == null) {
			throw new IllegalArgumentException("Paramer 'region' is required");
		}
		
	    StringBuilder sb = new StringBuilder();
	    sb.append("&mobile=").append(URLEncoder.encode(mobile.toString(), "UTF-8"));
	    sb.append("&templateId=").append(URLEncoder.encode(templateId.toString(), "UTF-8"));
	    sb.append("&region=").append(URLEncoder.encode(region.toString(), "UTF-8"));
	    
	    if (verifyId != null) {
	    	sb.append("&verifyId=").append(URLEncoder.encode(verifyId.toString(), "UTF-8"));
	    }
	    
	    if (verifyCode != null) {
	    	sb.append("&verifyCode=").append(URLEncoder.encode(verifyCode.toString(), "UTF-8"));
	    }
		String body = sb.toString();
	   	if (body.indexOf("&") == 0) {
	   		body = body.substring(1, body.length());
	   	}
	   	
		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.SMS, appKey, appSecret, "/sendCode.json", "application/x-www-form-urlencoded");
		HttpUtil.setBodyParameter(body, conn);
	    
	    return (SMSSendCodeResult) GsonUtil.fromJson(HttpUtil.returnResult(conn), SMSSendCodeResult.class);
	}
	
	/**
	 * 验证码验证方法 
	 * 
	 * @param  sessionId:短信验证码唯一标识，在发送短信验证码方法，返回值中获取。（必传）在发送验证码返回后将sessionid放在本地session中，用手机号存储
	 * @param  code:短信验证码内容。（必传）
	 *
	 * @return SMSVerifyCodeResult
	 **/
	public SMSVerifyCodeResult verifyCode(String sessionId, String code) throws Exception {
		if (sessionId == null) {
			throw new IllegalArgumentException("Paramer 'sessionId' is required");
		}
		
		if (code == null) {
			throw new IllegalArgumentException("Paramer 'code' is required");
		}
		
	    StringBuilder sb = new StringBuilder();
	    sb.append("&sessionId=").append(URLEncoder.encode(sessionId.toString(), "UTF-8"));
	    sb.append("&code=").append(URLEncoder.encode(code.toString(), "UTF-8"));
		String body = sb.toString();
	   	if (body.indexOf("&") == 0) {
	   		body = body.substring(1, body.length());
	   	}
	   	
		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.SMS, appKey, appSecret, "/verifyCode.json", "application/x-www-form-urlencoded");
		HttpUtil.setBodyParameter(body, conn);
	    
	    return (SMSVerifyCodeResult) GsonUtil.fromJson(HttpUtil.returnResult(conn), SMSVerifyCodeResult.class);
	}
	
	/**
	 * 向群发送消息
	* @Title: sendMessgeToGroups 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param fromUseId
	* @param @param messagePublishGroupToGroupId
	* @param @param messagePublishGroupTxtMessage
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月27日 上午9:53:06 
	* @return CodeSuccessResult    返回类型 
	* @throws
	 */
	public CodeSuccessResult sendMessgeToGroups(String fromUseId,String[] messagePublishGroupToGroupId,String message)
	{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);//这个时候可以初始化各个对象的appkey和appSecreat
		CodeSuccessResult messagePublishGroupResult = null;
		System.out.println("************************Group********************"+message);
		// 创建群组方法（创建群组，并将用户加入该群组，用户将可以收到该群的消息，同一用户最多可加入 500 个群，每个群最大至 3000 人，App 内的群组数量没有限制.注：其实本方法是加入群组方法 /group/join 的别名。） 
		try
		{
			TxtMessage messagePublishGroupTxtMessage = new TxtMessage(message, "group");
			messagePublishGroupResult = rongCloud.message.
					publishGroup(fromUseId, messagePublishGroupToGroupId, messagePublishGroupTxtMessage
							, "thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return messagePublishGroupResult;
	}
	
	public CodeSuccessResult sendImgMessgeToGroups(String fromUseId,String[] messagePublishGroupToGroupId,String message,String imageUri)
	{
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);//这个时候可以初始化各个对象的appkey和appSecreat
		CodeSuccessResult messagePublishGroupResult = null;
		System.out.println("************************Group********************"+message);
		// 创建群组方法（创建群组，并将用户加入该群组，用户将可以收到该群的消息，同一用户最多可加入 500 个群，每个群最大至 3000 人，App 内的群组数量没有限制.注：其实本方法是加入群组方法 /group/join 的别名。） 
		try
		{
			ImgMessage messagePublishGroupTxtMessage = new ImgMessage(message, "group", imageUri);
			messagePublishGroupResult = rongCloud.message.
					publishGroup(fromUseId, messagePublishGroupToGroupId, messagePublishGroupTxtMessage
							, "thisisapush", "{\"pushData\":\"hello\"}", 1, 1, 0);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return messagePublishGroupResult;
	}

}
