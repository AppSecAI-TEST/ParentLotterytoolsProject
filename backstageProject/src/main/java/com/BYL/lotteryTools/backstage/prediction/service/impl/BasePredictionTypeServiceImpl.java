package com.BYL.lotteryTools.backstage.prediction.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.prediction.dto.BasePredictionTypeDTO;
import com.BYL.lotteryTools.backstage.prediction.entity.BasePredictionType;
import com.BYL.lotteryTools.backstage.prediction.repository.BasePredictionTypeRepository;
import com.BYL.lotteryTools.backstage.prediction.service.BasePredictionTypeService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("basePredictionTypeService")
@Transactional(propagation=Propagation.REQUIRED)
public class BasePredictionTypeServiceImpl implements BasePredictionTypeService {

	@Autowired
	private BasePredictionTypeRepository basePredictionTypeRepository;

	public void save(BasePredictionType entity) 
	{
		basePredictionTypeRepository.save(entity);
	}

	public void update(BasePredictionType entity) {
		basePredictionTypeRepository.save(entity);		
	}

	public BasePredictionType getBasePredictionTypeById(String id) {
		return basePredictionTypeRepository.getBasePredictionTypeById(id);
	}

	public QueryResult<BasePredictionType> getBasePredictionTypeList(
			Class<BasePredictionType> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {
		
		QueryResult<BasePredictionType> queryResult = basePredictionTypeRepository.
				getScrollDataByJpql(BasePredictionType.class, whereJpql, queryParams, orderby, pageable);
		
		return queryResult;
	}

	public BasePredictionTypeDTO toDTO(BasePredictionType entity) {
		
		BasePredictionTypeDTO dto = new BasePredictionTypeDTO();
		
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTime(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<BasePredictionTypeDTO> toDTOs(List<BasePredictionType> entities) {
		
		List<BasePredictionTypeDTO> dtos = new ArrayList<BasePredictionTypeDTO>();
		
		for (BasePredictionType entity : entities) 
		{
			BasePredictionTypeDTO dto = this.toDTO(entity);
			dtos.add(dto);
		}
		
		return dtos;
	}
}
