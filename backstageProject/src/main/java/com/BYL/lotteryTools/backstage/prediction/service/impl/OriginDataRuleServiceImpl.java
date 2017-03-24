package com.BYL.lotteryTools.backstage.prediction.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.prediction.dto.OriginDataRuleDTO;
import com.BYL.lotteryTools.backstage.prediction.entity.OriginDataRule;
import com.BYL.lotteryTools.backstage.prediction.repository.OriginDataRuleRepository;
import com.BYL.lotteryTools.backstage.prediction.service.OriginDataRuleService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("originDataRuleService")
@Transactional(propagation=Propagation.REQUIRED)
public class OriginDataRuleServiceImpl implements OriginDataRuleService {

	@Autowired
	private OriginDataRuleRepository originDataRuleRepository;

	public void save(OriginDataRule entity) 
	{
		originDataRuleRepository.save(entity);		
	}

	public void update(OriginDataRule entity) {

		originDataRuleRepository.save(entity);		
	}

	public OriginDataRule getOriginDataRuleById(String id) {
		return originDataRuleRepository.getOriginDataRuleById(id);
	}

	public QueryResult<OriginDataRule> getOriginDataRuleList(
			Class<OriginDataRule> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {

		QueryResult<OriginDataRule> queryResult = originDataRuleRepository.
				getScrollDataByJpql(OriginDataRule.class, whereJpql, queryParams, orderby, pageable);
		return queryResult;
	}

	public OriginDataRuleDTO toDTO(OriginDataRule entity) {
		OriginDataRuleDTO dto = new OriginDataRuleDTO();
		
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

	public List<OriginDataRuleDTO> toDTOs(List<OriginDataRule> entities) {
		List<OriginDataRuleDTO> dtos = new ArrayList<OriginDataRuleDTO>();
		
		for (OriginDataRule entity : entities) 
		{
			OriginDataRuleDTO dto = this.toDTO(entity);
			dtos.add(dto);
		}
		
		return dtos;
	}
}
