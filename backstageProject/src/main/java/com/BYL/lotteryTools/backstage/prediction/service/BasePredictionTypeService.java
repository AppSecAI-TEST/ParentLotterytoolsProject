package com.BYL.lotteryTools.backstage.prediction.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.prediction.dto.BasePredictionTypeDTO;
import com.BYL.lotteryTools.backstage.prediction.entity.BasePredictionType;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface BasePredictionTypeService {

	public void save(BasePredictionType entity);
	
	public void update(BasePredictionType entity);
	
	public BasePredictionType getBasePredictionTypeById(String id);
	
	public QueryResult<BasePredictionType> getBasePredictionTypeList(Class<BasePredictionType> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public BasePredictionTypeDTO toDTO(BasePredictionType entity);
	
	public List<BasePredictionTypeDTO> toDTOs(List<BasePredictionType> entities);
}
