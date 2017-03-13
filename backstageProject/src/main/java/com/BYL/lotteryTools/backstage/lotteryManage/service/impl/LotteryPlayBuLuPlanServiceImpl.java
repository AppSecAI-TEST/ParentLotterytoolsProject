package com.BYL.lotteryTools.backstage.lotteryManage.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryPlayBuluPlanDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlayBulufangan;
import com.BYL.lotteryTools.backstage.lotteryManage.repository.LotteryPlayPlanRepository;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryPlayBuLuPlanService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;


@Service("lotteryPlayBuLuPlanService")
@Transactional(propagation=Propagation.REQUIRED)
public class LotteryPlayBuLuPlanServiceImpl implements LotteryPlayBuLuPlanService 
{
	@Autowired
	private LotteryPlayPlanRepository lotteryPlayPlanRepository;//补录号码方案

	public void save(LotteryPlayBulufangan entity) {
		lotteryPlayPlanRepository.save(entity);
	}

	public void update(LotteryPlayBulufangan entity) {
		lotteryPlayPlanRepository.save(entity);
		
	}

	public QueryResult<LotteryPlayBulufangan> getLotteryPlayBulufanganList(
			Class<LotteryPlayBulufangan> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {
		QueryResult<LotteryPlayBulufangan> qResult = lotteryPlayPlanRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,
				orderby, pageable);
		
		return qResult;
	}

	public List<LotteryPlayBuluPlanDTO> toRDTOS(
			List<LotteryPlayBulufangan> entities) {
		List<LotteryPlayBuluPlanDTO> dtos = new ArrayList<LotteryPlayBuluPlanDTO>();
		LotteryPlayBuluPlanDTO dto;
		for (LotteryPlayBulufangan entity : entities) 
		{
			dto = toDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	public LotteryPlayBuluPlanDTO toDTO(LotteryPlayBulufangan entity) {
		LotteryPlayBuluPlanDTO dto = new LotteryPlayBuluPlanDTO();
		try {
			BeanUtil.copyBeanProperties(dto, entity);
			
			//处理实体中的特殊转换值
			if(null != entity.getCreateTime())//创建时间
			{
				dto.setCreateTime(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public LotteryPlayBulufangan getLotteryPlayBulufanganById(String id) {
		return lotteryPlayPlanRepository.getLotteryPlayBulufanganById(id);
	}
	
	
	
}
