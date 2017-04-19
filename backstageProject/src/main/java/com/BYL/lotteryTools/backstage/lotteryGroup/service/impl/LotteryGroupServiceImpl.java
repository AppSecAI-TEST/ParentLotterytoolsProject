package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.lotteryGroupDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.LotteryGroupRespository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lotteryGroupService")
@Transactional(propagation=Propagation.REQUIRED)
public class LotteryGroupServiceImpl implements LotteryGroupService 
{
	@Autowired
	private LotteryGroupRespository lotteryGroupRespository;
	
	public void save(LotteryGroup entity)
	{
		lotteryGroupRespository.save(entity);
	}
	
	public void update(LotteryGroup entity)
	{
		lotteryGroupRespository.save(entity);
	}
	
	public QueryResult<LotteryGroup> getLotteryGroupList(
			Class<LotteryGroup> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {
		
		QueryResult<LotteryGroup> queryResult = lotteryGroupRespository.getScrollDataByJpql
				(LotteryGroup.class, whereJpql, queryParams, orderby, pageable);
		
		return queryResult;
	}
	
	public lotteryGroupDTO toDTO(LotteryGroup entity) {
		
		lotteryGroupDTO dto = new lotteryGroupDTO();
		
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<lotteryGroupDTO> toDTOs(
			List<LotteryGroup> entities) {
		
		List<lotteryGroupDTO> dtos = new ArrayList<lotteryGroupDTO>();
		
		for (LotteryGroup entity : entities) 
		{
			lotteryGroupDTO dto = new lotteryGroupDTO();
			
			dto = toDTO(entity);
			
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	
}
