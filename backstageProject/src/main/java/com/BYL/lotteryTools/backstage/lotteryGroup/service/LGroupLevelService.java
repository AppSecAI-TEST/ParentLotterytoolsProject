package com.BYL.lotteryTools.backstage.lotteryGroup.service;

import java.util.LinkedHashMap;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LGroupLevel;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface LGroupLevelService {

	
	public LGroupLevel getLGroupLevelByID(String id);
	 
	 public void save(LGroupLevel entity);
	 
	 public void update(LGroupLevel entity);
	 
	 public QueryResult<LGroupLevel> getLGroupLevelList(
				Class<LGroupLevel> entityClass, String whereJpql,
				Object[] queryParams, LinkedHashMap<String, String> orderby,
				Pageable pageable);
}
