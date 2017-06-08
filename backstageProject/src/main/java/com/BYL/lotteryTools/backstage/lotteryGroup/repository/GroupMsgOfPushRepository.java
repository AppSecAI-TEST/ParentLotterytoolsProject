package com.BYL.lotteryTools.backstage.lotteryGroup.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.GroupMsgOfPush;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface GroupMsgOfPushRepository extends GenericRepository<GroupMsgOfPush, String> 
{
	@Query("select u from GroupMsgOfPush u where  u.id = ?1")
	public GroupMsgOfPush getGroupMsgOfPushByID(String id);
}
