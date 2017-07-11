package com.BYL.lotteryTools.backstage.outer.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.outer.entity.PlanFromApp;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface PlanFromAppRepository extends GenericRepository<PlanFromApp,String>{

	@Query("select u from PlanFromApp u where  u.id = ?1")
	public PlanFromApp getPlanFromAppByID(String id);
}
