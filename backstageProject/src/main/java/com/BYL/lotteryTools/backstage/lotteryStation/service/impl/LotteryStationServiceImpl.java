package com.BYL.lotteryTools.backstage.lotteryStation.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryStation.dto.LotteryStationDTO;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.backstage.lotteryStation.repository.LotteryStationRepository;
import com.BYL.lotteryTools.backstage.lotteryStation.service.LotteryStationService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lotteryStationService")
@Transactional(propagation=Propagation.REQUIRED)
public class LotteryStationServiceImpl implements LotteryStationService {

	@Autowired
	private LotteryStationRepository lotteryStationRepository;

	public void save(LotteryStation entity)
	{
		lotteryStationRepository.save(entity);
		
	}

	public void update(LotteryStation entity) 
	{
		lotteryStationRepository.save(entity);		
	}

	public LotteryStation getLotteryStationById(String id) 
	{
		return lotteryStationRepository.getLotteryStationById(id);
	}

	public QueryResult<LotteryStation> getLotteryStationList(
			Class<LotteryStation> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {
		
		QueryResult<LotteryStation> queryResult = lotteryStationRepository.getScrollDataByJpql(LotteryStation.class,
				whereJpql, queryParams, orderby, pageable);
		
		return queryResult;
	}

	public LotteryStationDTO toDTO(LotteryStation entity) 
	{
		LotteryStationDTO dto = new LotteryStationDTO();
		
		if(null != entity)
		{
			try 
			{
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTimeStr(DateUtil.formatDate(entity.getCreateTime(),DateUtil.FULL_DATE_FORMAT));
				}
				
				if(null != entity.getOpenDoorTime())
				{
					dto.setOpenDoorTimeStr(DateUtil.formatDate(entity.getOpenDoorTime(),DateUtil.FULL_DATE_FORMAT));
				}
				
				if(null != entity.getCloseDoorTime())
				{
					dto.setCloseDoorTimeStr(DateUtil.formatDate(entity.getCloseDoorTime(),DateUtil.FULL_DATE_FORMAT));
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			
		}
		
		return dto;
	}

	public List<LotteryStationDTO> toDTOs(List<LotteryStation> entities) 
	{
		List<LotteryStationDTO> dtos = new ArrayList<LotteryStationDTO>();
			
		for (LotteryStation entity : entities) 
		{
			LotteryStationDTO dto = toDTO(entity);
			
			dtos.add(dto);
		}
		
		return dtos;
	}

	public LotteryStation getLotteryStationByStationNumber(String stationNumber)
	{
		return lotteryStationRepository.getLotteryStationByStationNumber(stationNumber);
	}
	
}
