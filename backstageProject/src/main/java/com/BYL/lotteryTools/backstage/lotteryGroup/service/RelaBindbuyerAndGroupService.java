package com.BYL.lotteryTools.backstage.lotteryGroup.service;

import java.util.List;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaBindOfLbuyerorexpertAndGroup;

public interface RelaBindbuyerAndGroupService {

	public void save(RelaBindOfLbuyerorexpertAndGroup entity);
	
	public void update(RelaBindOfLbuyerorexpertAndGroup entity);
	
	public RelaBindOfLbuyerorexpertAndGroup getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(String userId,String groupId);
	
	public List<RelaBindOfLbuyerorexpertAndGroup> getRelaList(String userId);
}
