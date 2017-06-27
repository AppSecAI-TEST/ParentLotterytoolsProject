package com.BYL.lotteryTools.backstage.lotteryStation.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.lotteryStation.dto.LotteryStationDTO;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface LotteryStationService {

	public void save(LotteryStation entity);
	
	public void update(LotteryStation entity);
	
	public LotteryStation getLotteryStationById(String id);
	
	public QueryResult<LotteryStation> getLotteryStationList(Class<LotteryStation> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public LotteryStationDTO toDTO(LotteryStation entity);
	
	public List<LotteryStationDTO> toDTOs(List<LotteryStation> entities);
	public LotteryStation getLotteryStationByStationNumber(String stationNumber);
	
	public List<LotteryStation> getLotteryStationByUserId(String userId);
	
	public List<LotteryStation> findAll();
	
	public LotteryStation getLotteryStationByInviteCode(String inviteCode);
}
