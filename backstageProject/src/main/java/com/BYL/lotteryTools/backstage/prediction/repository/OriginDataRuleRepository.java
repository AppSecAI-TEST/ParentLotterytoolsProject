package com.BYL.lotteryTools.backstage.prediction.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.prediction.entity.OriginDataRule;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface OriginDataRuleRepository extends GenericRepository<OriginDataRule, String> 
{
	@Query("select u from OriginDataRule u where u.isDeleted=1 and   u.id =?1")
	public OriginDataRule getOriginDataRuleById(String id);
}
