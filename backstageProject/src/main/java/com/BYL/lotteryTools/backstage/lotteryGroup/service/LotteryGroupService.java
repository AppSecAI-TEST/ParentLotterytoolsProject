package com.BYL.lotteryTools.backstage.lotteryGroup.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.LotteryGroupDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.common.bean.ResultBean;
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
	
	public List<LotteryGroup> findAll();
	
	public LotteryGroup getLotteryGroupByGroupNumber(String groupNumber);
	
	public  String generateGroupNumber();
	
	public List<LotteryGroup> getLotteryGroupByProvinceAndLotteryType(String province,String lotteryType);
	
	public List<LotteryGroup> getLotteryGroupByProvinceAndLotteryTypeAndCityAndDetailLotteryType
	(String province,String lotteryType,String city,String detailLotteryType);
	
	public boolean joinInCityCenterGroup(String province,String city,String lotteryType,String detailLotteryType,String userId);
	
	/**
	 * 根据群类型获取群列表
	* @Title: getLotteryGroupByLotteryType 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotteryType
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月7日 下午2:45:18 
	* @return List<LotteryGroup>    返回类型 
	* @throws
	 */
	public List<LotteryGroup> getLotteryGroupByLotteryType(String lotteryType);
	
	public  ResultBean joinUserInGroup(
			String[] joinUsers,
			String groupId);
}
