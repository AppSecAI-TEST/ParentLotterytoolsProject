package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.repository.LotterybuyerOrExpertRepository;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lotterybuyerOrExpertService")
@Transactional(propagation= Propagation.REQUIRED)
public class LotterybuyerOrExpertServiceImpl implements
		LotterybuyerOrExpertService {

	@Autowired
	private LotterybuyerOrExpertRepository lotterybuyerOrExpertRepository;

	public void save(LotterybuyerOrExpert entity) 
	{
		lotterybuyerOrExpertRepository.save(entity);		
	}

	public void update(LotterybuyerOrExpert entity) {
		lotterybuyerOrExpertRepository.save(entity);		
		
	}

	public LotterybuyerOrExpertDTO toDTO(LotterybuyerOrExpert entity) {
		
		LotterybuyerOrExpertDTO dto = new LotterybuyerOrExpertDTO();
		
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

	public List<LotterybuyerOrExpertDTO> toDTOs(
			List<LotterybuyerOrExpert> entities) {
		
		List<LotterybuyerOrExpertDTO> dtos = new ArrayList<LotterybuyerOrExpertDTO>();
		
		for (LotterybuyerOrExpert entity : entities) 
		{
			LotterybuyerOrExpertDTO dto = new LotterybuyerOrExpertDTO();
			
			dto = toDTO(entity);
			
			dtos.add(dto);
		}
		
		return dtos;
	}

	public QueryResult<LotterybuyerOrExpert> getLotterybuyerOrExpertList(
			Class<LotterybuyerOrExpert> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {
		
		QueryResult<LotterybuyerOrExpert> queryResult = lotterybuyerOrExpertRepository.getScrollDataByJpql
				(LotterybuyerOrExpert.class, whereJpql, queryParams, orderby, pageable);
		
		return queryResult;
	}

	public LotterybuyerOrExpert getLotterybuyerOrExpertById(String id) {
		return lotterybuyerOrExpertRepository.getLotterybuyerOrExpertById(id);
	}
	
	public LotterybuyerOrExpert getLotterybuyerOrExpertByTelephone(String telephone)
	{
		return lotterybuyerOrExpertRepository.getLotterybuyerOrExpertByTelephone(telephone);
	}
	
	public List<LotterybuyerOrExpert> getLotterybuyerOrExpertByCailiaoName(String cailiaoName)
	{
		return lotterybuyerOrExpertRepository.getLotterybuyerOrExpertByCailiaoName(cailiaoName);
	}
}
