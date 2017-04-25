package com.BYL.lotteryTools.backstage.lotteryGroup.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LGroupLevel;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface LGroupLevelRepository extends GenericRepository<LGroupLevel, String> 
{
	@Query("select u from LGroupLevel u where  u.id = ?1")
	public LGroupLevel getLGroupLevelByID(String id);
}
