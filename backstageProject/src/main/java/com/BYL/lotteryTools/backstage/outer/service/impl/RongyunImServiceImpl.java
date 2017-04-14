package com.BYL.lotteryTools.backstage.outer.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.RongCloud;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.CodeSuccessResult;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.TokenResult;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;

@Service("/rongyunService")
@Transactional(propagation=Propagation.REQUIRED)
public class RongyunImServiceImpl implements RongyunImService 
{
	String appKey = "82hegw5u83v8x";//appkey
	String appSecret = "z2VxpghbOvscFu";//上面key的secret
	
	
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
		
		return userGetTokenResult.toString();
	}
	
	public String createGroup(String[] joinUserId,String groupId,String groupName)
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
		
		return groupCreateResult.toString();
	}
	
}
