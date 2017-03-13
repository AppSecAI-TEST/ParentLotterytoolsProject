package com.BYL.lotteryTools.backstage.lotteryManage.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryPlayBuluPlanDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlayBulufangan;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface LotteryPlayBuLuPlanService 
{
	public void save(LotteryPlayBulufangan entity);
	
	
	public void update(LotteryPlayBulufangan entity);
	
	
	public QueryResult<LotteryPlayBulufangan> getLotteryPlayBulufanganList(Class<LotteryPlayBulufangan> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	
	public List<LotteryPlayBuluPlanDTO> toRDTOS(List<LotteryPlayBulufangan> entities);
	
	
	public LotteryPlayBuluPlanDTO toDTO(LotteryPlayBulufangan entity);
	
	public LotteryPlayBulufangan getLotteryPlayBulufanganById(String id);
}
