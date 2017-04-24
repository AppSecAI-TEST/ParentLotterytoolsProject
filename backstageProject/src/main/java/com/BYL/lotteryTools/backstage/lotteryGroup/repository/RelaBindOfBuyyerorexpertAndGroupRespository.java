package com.BYL.lotteryTools.backstage.lotteryGroup.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaBindOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface RelaBindOfBuyyerorexpertAndGroupRespository extends GenericRepository<RelaBindOfLbuyerorexpertAndGroup, String> 
{
	@Query("select u from RelaBindOfLbuyerorexpertAndGroup u where u.isDeleted = '1' and u.lotterybuyerOrExpert.id = ?1 and u.lotteryGroup.id = ?2")
	public RelaBindOfLbuyerorexpertAndGroup getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(String userId,String groupId);
}
