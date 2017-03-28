package com.BYL.lotteryTools.backstage.prediction.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.prediction.dto.PredictionTypeDTO;
import com.BYL.lotteryTools.backstage.prediction.entity.PredictionType;
import com.BYL.lotteryTools.backstage.prediction.repository.PredictionTypeRepository;
import com.BYL.lotteryTools.backstage.prediction.service.PredictionTypeService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("predictionTypeService")
@Transactional(propagation=Propagation.REQUIRED)
public class PredictionTypeServiceImpl implements PredictionTypeService {

	@Autowired
	private PredictionTypeRepository predictionTypeRepository;

	public void save(PredictionType entity) {
		predictionTypeRepository.save(entity);
	}

	public void update(PredictionType entity) {
		predictionTypeRepository.save(entity);		
	}

	public PredictionType getPredictionTypeById(String id) {
		return predictionTypeRepository.getPredictionTypeById(id);
	}

	public QueryResult<PredictionType> getPredictionTypeList(
			Class<PredictionType> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {

		QueryResult<PredictionType> queryResult = predictionTypeRepository.
				getScrollDataByJpql(PredictionType.class, whereJpql, queryParams, orderby, pageable);
		return queryResult;
	}

	public PredictionTypeDTO toDTO(PredictionType entity) {
		
		PredictionTypeDTO dto = new PredictionTypeDTO();
		if(null != entity)
		{
			try 
			{
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTime(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
				}
				
				if(null != entity.getLotteryPlay())
				{
					dto.setLotteryPlayName(entity.getLotteryPlay().getName());
				}
				
				if(null != entity.getBasePredictionType())
				{
					dto.setBasePredictionTypeName(entity.getBasePredictionType().getBasePredictionName());
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<PredictionTypeDTO> toDTOs(List<PredictionType> entities) {
		
		List<PredictionTypeDTO> dtos = new ArrayList<PredictionTypeDTO>();
		
		
		for (PredictionType entity : entities)
		{
			PredictionTypeDTO dto = new PredictionTypeDTO();
			
			dto = this.toDTO(entity);
			
			dtos.add(dto);
		}
		
		return dtos;
	}
}
