package com.BYL.lotteryTools.backstage.prediction.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.prediction.dto.OriginDataRuleDTO;
import com.BYL.lotteryTools.backstage.prediction.entity.OriginDataRule;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface OriginDataRuleService {

	public void save(OriginDataRule entity);
	
	public void update(OriginDataRule entity);
	
	public OriginDataRule getOriginDataRuleById(String id);
	
	public QueryResult<OriginDataRule> getOriginDataRuleList(Class<OriginDataRule> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public OriginDataRuleDTO toDTO(OriginDataRule entity);
	
	public List<OriginDataRuleDTO> toDTOs(List<OriginDataRule> entities);
}
