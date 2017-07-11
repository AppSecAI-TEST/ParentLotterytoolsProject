package com.BYL.lotteryTools.backstage.outer.service;

import java.util.List;

import com.BYL.lotteryTools.backstage.outer.dto.PlanFromAppDTO;
import com.BYL.lotteryTools.backstage.outer.dto.PlanPackageFromAppDTO;
import com.BYL.lotteryTools.backstage.outer.entity.PlanFromApp;
import com.BYL.lotteryTools.backstage.outer.entity.PlanPackageFromApp;

public interface PlanPackageFromAppService {

	public void savePackage(PlanPackageFromApp entity);
	
	public void updatePackage(PlanPackageFromApp entity);
	
	public void savePlan(PlanFromApp entity);
	
	public void updatePlan(PlanFromApp entity);
	
	public PlanPackageFromApp getPlanPackageFromAppByID(String id);
	
	public PlanFromApp getPlanFromAppByID(String id);
	
	public List<PlanPackageFromApp> getPlanPackageFromAppList(int page,int rows,PlanPackageFromAppDTO dto);
	
	public List<PlanFromAppDTO> toDTOsOfPlanFromApp(List<PlanFromApp> entities);
	
	public PlanFromAppDTO toDTOOfPlanFromApp(PlanFromApp entity);
	
	public List<PlanPackageFromAppDTO> toDTOsOfPlanPackage(List<PlanPackageFromApp> entities);
	
	public PlanPackageFromAppDTO toDTOOfPlanPackage(PlanPackageFromApp entity);
}
