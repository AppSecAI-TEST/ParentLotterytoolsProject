package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaBindOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.RelaBindOfBuyyerorexpertAndGroupRespository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaBindbuyerAndGroupService;

@Service("relaBindbuyerAndGroupService")
@Transactional(propagation=Propagation.REQUIRED)
public class RelaBindbuyerAndGroupServiceImpl implements
		RelaBindbuyerAndGroupService {

	@Autowired
	private RelaBindOfBuyyerorexpertAndGroupRespository relaBindOfBuyyerorexpertAndGroupRespository;
	
	public void save(RelaBindOfLbuyerorexpertAndGroup entity)
	{
		relaBindOfBuyyerorexpertAndGroupRespository.save(entity);
	}
	
	public void update(RelaBindOfLbuyerorexpertAndGroup entity)
	{
		relaBindOfBuyyerorexpertAndGroupRespository.save(entity);
	}

	public RelaBindOfLbuyerorexpertAndGroup getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(
			String userId, String groupId) {
		return relaBindOfBuyyerorexpertAndGroupRespository.getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(userId, groupId);
	}
}
