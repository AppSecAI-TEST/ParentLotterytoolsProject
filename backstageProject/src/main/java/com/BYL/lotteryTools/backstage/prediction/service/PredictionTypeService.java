package com.BYL.lotteryTools.backstage.prediction.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.prediction.dto.PredictionTypeDTO;
import com.BYL.lotteryTools.backstage.prediction.entity.PredictionType;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface PredictionTypeService {

	public void save(PredictionType entity);
	
	public void update(PredictionType entity);
	
	public PredictionType getPredictionTypeById(String id);
	
	public QueryResult<PredictionType> getPredictionTypeList(Class<PredictionType> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public PredictionTypeDTO toDTO(PredictionType entity);
	
	public List<PredictionTypeDTO> toDTOs(List<PredictionType> entities);
	
	public List<PredictionType> getPredictionTypeOfProAndLplay(String lotteryPlayId,String province,String baseInittypeId) ;
	
	/**
	 * 获取当前预测期的预测数据
	* @Title: getPredictionPlanOfExperts 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param maxIssueId
	* @param @param isFree
	* @param @param count
	* @param @param baseTypeId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月26日 下午4:22:18 
	* @return List<?>    返回类型 
	* @throws
	 */
	public List<?> getPredictionPlanOfExperts(String maxIssueId,String isFree,String count,String baseTypeId,String predictionTbname);
}
