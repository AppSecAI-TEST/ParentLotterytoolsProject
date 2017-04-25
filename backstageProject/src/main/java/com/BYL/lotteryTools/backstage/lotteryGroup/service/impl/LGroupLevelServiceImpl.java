package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LGroupLevel;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.LGroupLevelRepository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LGroupLevelService;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lGroupLevelService")
@Transactional(propagation=Propagation.REQUIRED)
public class LGroupLevelServiceImpl implements LGroupLevelService {

	@Autowired
	private LGroupLevelRepository lGroupLevelRepository;
	
	 public LGroupLevel getLGroupLevelByID(String id)
	 {
		 return lGroupLevelRepository.getLGroupLevelByID(id);
	 }
	 
	 public void save(LGroupLevel entity)
	 {
		 lGroupLevelRepository.save(entity);
	 }
	 
	 public void update(LGroupLevel entity)
	 {
		 lGroupLevelRepository.save(entity);
	 }
	 
	 public QueryResult<LGroupLevel> getLGroupLevelList(
				Class<LGroupLevel> entityClass, String whereJpql,
				Object[] queryParams, LinkedHashMap<String, String> orderby,
				Pageable pageable) {
			
			QueryResult<LGroupLevel> queryResult = lGroupLevelRepository.getScrollDataByJpql
					(LGroupLevel.class, whereJpql, queryParams, orderby, pageable);
			
			return queryResult;
		}
	 
}
