package com.BYL.lotteryTools.backstage.outer.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.outer.entity.PlanPackageFromApp;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface PlanPackageFromAppRepository extends GenericRepository<PlanPackageFromApp,String>{

	@Query("select u from PlanPackageFromApp u where  u.id = ?1")
	public PlanPackageFromApp getPlanPackageFromAppByID(String id);
}
