package com.BYL.lotteryTools.backstage.lotteryManage.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryPlayDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface LotteryPlayService 
{
	public void save(LotteryPlay entity);
	
	
	public void update(LotteryPlay entity);
	
	
	public QueryResult<LotteryPlay> getLotteryPlayList(Class<LotteryPlay> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	
	public List<LotteryPlayDTO> toRDTOS(List<LotteryPlay> entities);
	
	
	public LotteryPlayDTO toDTO(LotteryPlay entity);
	
	public LotteryPlay getLotteryPlayById(String id);
	
	/**
	 * 获取当前可以进行补录的省份数据
	 * @param entityClass
	 * @param whereJpql
	 * @param queryParams
	 * @param orderby
	 * @param pageable
	 * @return
	 */
	public QueryResult<LotteryPlay> getProvinceOfLotteryPlayList(Class<LotteryPlay> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	/**
	 * 根据省份和彩种类型信息获取补录信息数据列表
	 * @param city
	 * @param lotteryType
	 * @return
	 */
	public List<LotteryPlay> getLotteryPlayByProvinceAndLotteryType(String city,String lotteryType);
	
	
	
	
}
