package com.BYL.lotteryTools.backstage.lotteryGroup.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaBindOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface RelaBindbuyerAndGroupService {

	public void save(RelaBindOfLbuyerorexpertAndGroup entity);
	
	public void delete(RelaBindOfLbuyerorexpertAndGroup entity);
	
	public void update(RelaBindOfLbuyerorexpertAndGroup entity);
	
	public RelaBindOfLbuyerorexpertAndGroup getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(String userId,String groupId);
	
	public List<RelaBindOfLbuyerorexpertAndGroup> getRelaList(String userId);
	
	//获取加入当前群的群成员
	public QueryResult<RelaBindOfLbuyerorexpertAndGroup> getMemberOfJoinGroup(Pageable pageable,String groupId) ;
}
