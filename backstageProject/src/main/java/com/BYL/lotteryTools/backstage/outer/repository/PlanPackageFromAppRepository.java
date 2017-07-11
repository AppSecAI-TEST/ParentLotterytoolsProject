package com.BYL.lotteryTools.backstage.outer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.outer.entity.PlanPackageFromApp;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface PlanPackageFromAppRepository extends GenericRepository<PlanPackageFromApp,String>{

	@Query("select u from PlanPackageFromApp u where u.isDeleted = 1 and  u.id = ?1")
	public PlanPackageFromApp getPlanPackageFromAppByID(String id);
	
	@Query("select u from PlanPackageFromApp u where u.isDeleted = 1 and   u.userId = ?1")
	public List<PlanPackageFromApp> getPlanPackageFromAppByUserId(String userId);
	
}
