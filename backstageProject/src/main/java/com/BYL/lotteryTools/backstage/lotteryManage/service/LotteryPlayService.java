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
	 * 获取所有有效的区域彩种数据
	* @Title: getAllLotteryPlays 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月18日 上午10:20:10 
	* @return List<LotteryPlay>    返回类型 
	* @throws
	 */
	public List<LotteryPlay> getAllLotteryPlays();
	
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
	
	/**
	 * 根据省份获取玩法
	* @Title: getLotteryPlayByProvince 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param province
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月26日 下午3:41:36 
	* @return List<LotteryPlay>    返回类型 
	* @throws
	 */
	public List<LotteryPlay> getLotteryPlayByProvince(String province);
	
	/**
	 * 获取当前应该预测的期号
	* @Title: getYuceMaxIssueId 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotteryPlayId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月26日 下午4:25:52 
	* @return String    返回类型 
	* @throws
	 */
	public String getYuceMaxIssueId(String lotteryPlayId);
	
	public LotteryPlay getLotteryPlayByProvinceAndLotteryTypeAndLotteryNumber(String province,String lotteryType,String lotteryNumber);
	
	
}
