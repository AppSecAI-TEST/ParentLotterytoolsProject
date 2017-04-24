package com.BYL.lotteryTools.backstage.lotteryGroup.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.LotteryGroupDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface LotteryGroupService {

	public void save(LotteryGroup entity);
	
	public void update(LotteryGroup entity);
	
	public QueryResult<LotteryGroup> getLotteryGroupList(
			Class<LotteryGroup> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable);
	
	public LotteryGroupDTO toDTO(LotteryGroup entity);
	
	public List<LotteryGroupDTO> toDTOs(
			List<LotteryGroup> entities);
	
	public List<LotteryGroup> getLotteryGroupByGroupRobotID(String groupRobotID);
	
	public LotteryGroup getLotteryGroupById(String id);
}
