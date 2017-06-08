package com.BYL.lotteryTools.backstage.lotteryGroup.service;

import java.util.List;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.GroupMsgOfPushDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.GroupMsgOfPush;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface GroupMsgOfPushService {

	public void save(GroupMsgOfPush entity);
	
	public void update(GroupMsgOfPush entity);
	
	public GroupMsgOfPushDTO toDTO(GroupMsgOfPush entity);
	
	public List<GroupMsgOfPushDTO> toDTO(List<GroupMsgOfPush> entities);
	
	public QueryResult<GroupMsgOfPush> getGroupMsgOfPushList(GroupMsgOfPushDTO dto,int page,int rows);
}
