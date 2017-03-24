package com.BYL.lotteryTools.backstage.prediction.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.prediction.dto.PredictionTypeDTO;
import com.BYL.lotteryTools.backstage.prediction.entity.PredictionType;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface PredictionTypeService {

	public void save(PredictionType entity);
	
	public void update(PredictionType entity);
	
	public PredictionType getPredictionTypeById(String id);
	
	public QueryResult<PredictionType> getPredictionTypeList(Class<PredictionType> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public PredictionTypeDTO toDTO(PredictionType entity);
	
	public List<PredictionTypeDTO> toDTOs(List<PredictionType> entities);
}
