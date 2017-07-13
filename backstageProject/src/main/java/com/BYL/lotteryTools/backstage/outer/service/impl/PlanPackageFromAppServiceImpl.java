package com.BYL.lotteryTools.backstage.outer.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.outer.dto.PlanFromAppDTO;
import com.BYL.lotteryTools.backstage.outer.dto.PlanPackageFromAppDTO;
import com.BYL.lotteryTools.backstage.outer.entity.PlanFromApp;
import com.BYL.lotteryTools.backstage.outer.entity.PlanPackageFromApp;
import com.BYL.lotteryTools.backstage.outer.repository.PlanFromAppRepository;
import com.BYL.lotteryTools.backstage.outer.repository.PlanPackageFromAppRepository;
import com.BYL.lotteryTools.backstage.outer.service.PlanPackageFromAppService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("/planPackageFromAppService")
@Transactional(propagation=Propagation.REQUIRED)
public class PlanPackageFromAppServiceImpl implements PlanPackageFromAppService {

	@Autowired
	private PlanPackageFromAppRepository planPackageFromAppRepository;
	
	@Autowired
	private PlanFromAppRepository planFromAppRepository;
	
	public void savePackage(PlanPackageFromApp entity)
	{
		planPackageFromAppRepository.save(entity);
	}
	
	public void updatePackage(PlanPackageFromApp entity)
	{
		planPackageFromAppRepository.save(entity);
	}
	
	public void savePlan(PlanFromApp entity)
	{
		planFromAppRepository.save(entity);
	}
	
	public void updatePlan(PlanFromApp entity)
	{
		planFromAppRepository.save(entity);
	}

	public PlanPackageFromApp getPlanPackageFromAppByID(String id) {
		return planPackageFromAppRepository.getPlanPackageFromAppByID(id);
	}

	public PlanFromApp getPlanFromAppByID(String id) {
		return planFromAppRepository.getPlanFromAppByID(id);
	}

	public List<PlanPackageFromApp> getPlanPackageFromAppList(int page,
			int rows, PlanPackageFromAppDTO dto) 
	{
		Pageable pageable = new PageRequest(page-1,rows);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("createTime", "desc");
		
		if(null != dto.getUserId())
		{
			params.add(dto.getUserId());//只查询有效的数据
			buffer.append(" and userId = ?").append(params.size());
		}
		
		if(null != dto.getProvinceCode())
		{
			params.add(dto.getProvinceCode());//只查询有效的数据
			buffer.append(" and provinceCode = ?").append(params.size());
		}
		
		QueryResult<PlanPackageFromApp> lQueryResult = planPackageFromAppRepository
				.getScrollDataByJpql(PlanPackageFromApp.class,
				buffer.toString(), params.toArray(),orderBy, pageable);
		List<PlanPackageFromApp> entities = lQueryResult.getResultList();
		
		return entities;
	}

	public List<PlanFromAppDTO> toDTOsOfPlanFromApp(List<PlanFromApp> entities) {
		List<PlanFromAppDTO> dtos = new ArrayList<PlanFromAppDTO>();
		if(null != entities)
		{
			for (PlanFromApp entity : entities) {
				PlanFromAppDTO dto = new PlanFromAppDTO();
				dto = this.toDTOOfPlanFromApp(entity);
				dtos.add(dto);
			}
		}
		
		return dtos;
	}

	public PlanFromAppDTO toDTOOfPlanFromApp(PlanFromApp entity) {
		PlanFromAppDTO dto = new PlanFromAppDTO();
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<PlanPackageFromAppDTO> toDTOsOfPlanPackage(
			List<PlanPackageFromApp> entities) {
		List<PlanPackageFromAppDTO> dtos = new ArrayList<PlanPackageFromAppDTO>();
		if(null != entities)
		{
			for (PlanPackageFromApp entity : entities) {
				PlanPackageFromAppDTO dto = new PlanPackageFromAppDTO();
				dto = this.toDTOOfPlanPackage(entity);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public PlanPackageFromAppDTO toDTOOfPlanPackage(PlanPackageFromApp entity) {
		PlanPackageFromAppDTO dto  = new PlanPackageFromAppDTO();
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTimeStr(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<PlanPackageFromApp> getPlanPackageFromAppByUserId(String userId) {
		return planPackageFromAppRepository.getPlanPackageFromAppByUserId(userId);
	}
	
	
}
