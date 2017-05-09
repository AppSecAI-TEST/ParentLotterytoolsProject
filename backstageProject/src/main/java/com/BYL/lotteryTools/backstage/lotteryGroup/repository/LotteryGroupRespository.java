package com.BYL.lotteryTools.backstage.lotteryGroup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface LotteryGroupRespository extends GenericRepository<LotteryGroup, String> 
{
	@Query("select u from LotteryGroup u where u.isDeleted = 1 and u.id = ?1")
	public LotteryGroup getLotteryGroupById(String id);
	
	@Query("select u from LotteryGroup u where u.isDeleted = 1 and u.groupRobotID = ?1")
	public List<LotteryGroup> getLotteryGroupByGroupRobotID(String groupRobotID);
	
	@Query("select u from LotteryGroup u where u.isDeleted = 1 and u.groupNumber = ?1 ")
	public LotteryGroup getLotteryGroupByGroupNumber(String groupNumber);
}
