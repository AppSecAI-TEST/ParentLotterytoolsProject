package com.BYL.lotteryTools.backstage.prediction.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;
import com.BYL.lotteryTools.backstage.prediction.dto.PredictionTypeDTO;
import com.BYL.lotteryTools.backstage.prediction.entity.PredictionType;
import com.BYL.lotteryTools.backstage.prediction.entity.QiansanDanmaYuce;
import com.BYL.lotteryTools.backstage.prediction.repository.PredictionTypeRepository;
import com.BYL.lotteryTools.backstage.prediction.repository.QiansanDanmaRepository;
import com.BYL.lotteryTools.backstage.prediction.service.PredictionTypeService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("predictionTypeService")
@Transactional(propagation=Propagation.REQUIRED)
public class PredictionTypeServiceImpl implements PredictionTypeService {

	@Autowired
	private PredictionTypeRepository predictionTypeRepository;
	
	@Autowired
	private QiansanDanmaRepository qiansanDanmaRepository;

	public void save(PredictionType entity) {
		predictionTypeRepository.save(entity);
	}

	public void update(PredictionType entity) {
		predictionTypeRepository.save(entity);		
	}

	public PredictionType getPredictionTypeById(String id) {
		return predictionTypeRepository.getPredictionTypeById(id);
	}

	public QueryResult<PredictionType> getPredictionTypeList(
			Class<PredictionType> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {

		QueryResult<PredictionType> queryResult = predictionTypeRepository.
				getScrollDataByJpql(PredictionType.class, whereJpql, queryParams, orderby, pageable);
		return queryResult;
	}
	
	public List<PredictionType> getPredictionTypeOfProAndLplay(String lotteryPlayId,String province,String baseInittypeId) {
		List<PredictionType> list = new ArrayList<PredictionType>();
		
		StringBuffer sql = new StringBuffer();
		
		Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
		List<Object> params = new ArrayList<Object>();
		
		sql.append("SELECT pre.* FROM T_LT_BASE_PREDICTION_TYPE base,T_LT_PREDICTION_TYPE pre "+
				 " WHERE pre.BASE_PREDICTION_TYPE_ID=base.ID "+
				" AND base.YUCE_FENLEI='"+baseInittypeId+"' AND pre.LOTTERYPLAY_ID='"+lotteryPlayId+"'");
		
		QueryResult<PredictionType> queryResult = predictionTypeRepository.
				getScrollDataBySql(PredictionType.class, sql.toString(), params.toArray(), pageable);
		
		list = queryResult.getResultList();
		return list;
	}
	//TODO：按条件获取专家的预测结果
	public List<?> getPredictionPlanOfExperts(String maxIssueId,String isFree,String count,String baseTypeId,String predictionTbname)
	{
		List<?> list = new ArrayList<Object>();
		
		Integer baseType = Integer.parseInt(baseTypeId);
		
		switch(baseType)
		{
			case 1: list = this.getQiansanDanmaYuceList(maxIssueId, isFree, count, predictionTbname); break;
			case 2: break;
			case 3: break;
			case 4: break;
			case 5: break;
			case 6: break;
		}
		
		return list;
	}
	
	/**获取前三胆杀的专家预测结果**/	
	private List<QiansanDanmaYuce> getQiansanDanmaYuceList(String maxIssueId,String isFree,String count,String predictionTbname)
	{
		
		StringBuffer execSql = new StringBuffer("SELECT u.* FROM "+predictionTbname +" u  where u.ISSUE_NUMBER ="+maxIssueId );
		
		if(null != isFree && !"".equals(isFree))
		{
			execSql.append(" and  IS_CHARGE = '"+isFree+"' ");
		}
		
		
		execSql.append("order by EXPERT_LEVEL asc LIMIT "+count);
		
		Object[] queryParams = new Object[]{
		};
		
		List<QiansanDanmaYuce> yucelist = qiansanDanmaRepository.
				getEntityListBySql(QiansanDanmaYuce.class, execSql.toString(), queryParams);
		
		return yucelist;
	}

	public PredictionTypeDTO toDTO(PredictionType entity) {
		
		PredictionTypeDTO dto = new PredictionTypeDTO();
		if(null != entity)
		{
			try 
			{
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTime(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
				}
				
				if(null != entity.getLotteryPlay())
				{
					dto.setLotteryPlayName(entity.getLotteryPlay().getName());
				}
				
				if(null != entity.getBasePredictionType())
				{
					dto.setBasePredictionTypeName(entity.getBasePredictionType().getBasePredictionName());
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<PredictionTypeDTO> toDTOs(List<PredictionType> entities) {
		
		List<PredictionTypeDTO> dtos = new ArrayList<PredictionTypeDTO>();
		
		
		for (PredictionType entity : entities)
		{
			PredictionTypeDTO dto = new PredictionTypeDTO();
			
			dto = this.toDTO(entity);
			
			dtos.add(dto);
		}
		
		return dtos;
	}
}
