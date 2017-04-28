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
import com.BYL.lotteryTools.backstage.prediction.entity.FushiYuce;
import com.BYL.lotteryTools.backstage.prediction.entity.PredictionType;
import com.BYL.lotteryTools.backstage.prediction.entity.QiansanDanmaYuce;
import com.BYL.lotteryTools.backstage.prediction.entity.RenDanmaYuce;
import com.BYL.lotteryTools.backstage.prediction.entity.SixGroupYuce;
import com.BYL.lotteryTools.backstage.prediction.repository.FushiYuceRepository;
import com.BYL.lotteryTools.backstage.prediction.repository.PredictionTypeRepository;
import com.BYL.lotteryTools.backstage.prediction.repository.QiansanDanmaRepository;
import com.BYL.lotteryTools.backstage.prediction.repository.RenDanmaRepository;
import com.BYL.lotteryTools.backstage.prediction.repository.SixGroupYuceRepository;
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
	
	@Autowired
	private RenDanmaRepository renDanmaRepository;
	
	@Autowired
	private FushiYuceRepository fushiYuceRepository;
	
	@Autowired
	private SixGroupYuceRepository sixGroupYuceRepository;

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
	
	public List<PredictionType> getPredictionTypeOfProAndLplay(String lotteryPlayId,String baseInittypeId) {
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
	
	//获取当前专家的当前区域预测类型的历史预测结果
	public List<?> getHisPredictionOfExpert(String count,String baseTypeId,String predictionTbname,String expertId)
	{
		List<?> list = new ArrayList<Object>();
		
		Integer baseType = Integer.parseInt(baseTypeId);
		
		switch(baseType)
		{
			case 1: list = this.getQiansanDanmaYuceList(null, null, count, predictionTbname,expertId); break;//前三胆杀
			case 2: list = this.getRenDanmaYuceList(null, null, count, predictionTbname,expertId);break;//:任胆杀  
			case 3: list = this.getFushiYuceList(null, null, count, predictionTbname,expertId);break;//:前三六码复式
			case 4: list = this.getFushiYuceList(null, null, count, predictionTbname,expertId);break;//:乐选4期计划 
			case 5: list = this.getFushiYuceList(null, null, count, predictionTbname,expertId);break;//:两码三期计划   
			case 6: list = this.getSixGroupYuceList(null, null, count, predictionTbname,expertId); break;//:获取任三精选六组预测
		}
		
		return list;
	}
	
	//TODO：按条件获取专家的预测结果
	public List<?> getPredictionPlanOfExperts(String maxIssueId,String isFree,String count,String baseTypeId,String predictionTbname)
	{
		List<?> list = new ArrayList<Object>();
		
		Integer baseType = Integer.parseInt(baseTypeId);
		
		switch(baseType)
		{
			case 1: list = this.getQiansanDanmaYuceList(maxIssueId, isFree, count, predictionTbname,null); break;//前三胆杀
			case 2: list = this.getRenDanmaYuceList(maxIssueId, isFree, count, predictionTbname,null);break;//任胆杀
			case 3: list = this.getFushiYuceList(maxIssueId, isFree, count, predictionTbname,null);break;//前三六码复式
			case 4: list = this.getFushiYuceList(maxIssueId, isFree, count, predictionTbname,null);break;//乐选4期计划
			case 5: list = this.getFushiYuceList(maxIssueId, isFree, count, predictionTbname,null);break;//两码三期计划
			case 6: list = this.getSixGroupYuceList(maxIssueId, isFree, count, predictionTbname,null); break;//获取任三精选六组预测
		}
		
		
		return list;
	}
	
	/**获取前三胆杀的专家预测结果**/	
	private List<QiansanDanmaYuce> getQiansanDanmaYuceList(String maxIssueId,String isFree,String count,String predictionTbname,String expertId)
	{
		
		StringBuffer execSql = new StringBuffer("SELECT ID,PREDICTION_TYPE,EXPERT_ID,ISSUE_NUMBER"
				+ ",DANMA_ONE,DANMA_TWO,SHAMA_ONE,IS_CHARGE,MONEY,WIN_RATE_DUDAN,WIN_RATE_SHUANGDAN,WIN_RATE_DANMA,WIN_RATE_SHUANGDAN,WIN_RATE_DANMA,"
				+ " DROWN_NUMBER,DUDAN_STATUS,SHUANGDAN_STATUS,DANMA_STATUS,SHAMAER_STATUS,SHAMASAN_STATUS,WIN_RATE_SHAER,WIN_RATE_SHASAN,EXPERT_LEVEL  FROM "+predictionTbname +" u  where " );
		
		if(null != maxIssueId && !"".equals(maxIssueId))
		{
			execSql.append(" u.ISSUE_NUMBER ="+maxIssueId);
			
			if(null != isFree && !"".equals(isFree))
			{
				execSql.append(" and  IS_CHARGE = '"+isFree+"' ");
				
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append(" and EXPERT_ID = '"+expertId+"' ");
				}
			}
			else
				if(null != expertId && !"".equals(expertId))
				{
				execSql.append(" and EXPERT_ID = '"+expertId+"' ");
				}
		}
		else
		{
			if(null != isFree && !"".equals(isFree))
			{
				execSql.append("  IS_CHARGE = '"+isFree+"' ");
				
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append(" and  EXPERT_ID = '"+expertId+"' ");
				}
			}
			else
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append("  EXPERT_ID = '"+expertId+"' ");
				}
		}
		
		
		
		
		execSql.append(" order by EXPERT_LEVEL desc LIMIT "+count);
		
		Object[] queryParams = new Object[]{
		};
		
		List<QiansanDanmaYuce> yucelist = qiansanDanmaRepository.
				getEntityListBySql(QiansanDanmaYuce.class, execSql.toString(), queryParams);
		
		return yucelist;
	}
	
	/**获取任胆杀的专家预测结果**/	
	private List<RenDanmaYuce> getRenDanmaYuceList(String maxIssueId,String isFree,String count,String predictionTbname,String expertId)
	{
		
		StringBuffer execSql = new StringBuffer("SELECT ID,PREDICTION_TYPE,EXPERT_ID,ISSUE_NUMBER,DANMA_ONE,SHAMA_ONE,IS_CHARGE,MONEY"
				+ ",WIN_RATE_DUDAN,DROWN_NUMBER,DUDAN_STATUS,SHAMAYI_STATUS,WIN_RATE_SHAYI,EXPERT_LEVEL FROM "+predictionTbname +" u  where u.ISSUE_NUMBER ="+maxIssueId );
		
		if(null != maxIssueId && !"".equals(maxIssueId))
		{
			execSql.append(" u.ISSUE_NUMBER ="+maxIssueId);
			
			if(null != isFree && !"".equals(isFree))
			{
				execSql.append(" and  IS_CHARGE = '"+isFree+"' ");
				
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append(" and EXPERT_ID = '"+expertId+"' ");
				}
			}
			else
				if(null != expertId && !"".equals(expertId))
				{
				execSql.append(" and EXPERT_ID = '"+expertId+"' ");
				}
		}
		else
		{
			if(null != isFree && !"".equals(isFree))
			{
				execSql.append("  IS_CHARGE = '"+isFree+"' ");
				
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append(" and  EXPERT_ID = '"+expertId+"' ");
				}
			}
			else
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append("  EXPERT_ID = '"+expertId+"' ");
				}
		}
		
		
		execSql.append(" order by EXPERT_LEVEL asc LIMIT "+count);
		
		Object[] queryParams = new Object[]{
		};
		
		List<RenDanmaYuce> yucelist = renDanmaRepository.
				getEntityListBySql(RenDanmaYuce.class, execSql.toString(), queryParams);
		
		return yucelist;
	}
	
	/**获取复式预测的的专家预测结果（包括：两码三期计划，乐选四期，前三六码复式）**/	
	private List<FushiYuce> getFushiYuceList(String maxIssueId,String isFree,String count,String predictionTbname,String expertId)
	{
		
		StringBuffer execSql = new StringBuffer("SELECT ID,PREDICTION_TYPE,EXPERT_ID,ISSUE_NUMBER,IS_CHARGE,MONEY,DROWN_NUMBER,EXPERT_LEVEL,WIN_RATE,"
				+ "FUSHI,STATUS,YUCE_ISSUE_START,YUCE_ISSUE_STOP,CYCLE,ZJLEVEL FROM "+predictionTbname +" u  where u.ISSUE_NUMBER ="+maxIssueId );
		
		if(null != maxIssueId && !"".equals(maxIssueId))
		{
			execSql.append(" u.ISSUE_NUMBER ="+maxIssueId);
			
			if(null != isFree && !"".equals(isFree))
			{
				execSql.append(" and  IS_CHARGE = '"+isFree+"' ");
				
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append(" and EXPERT_ID = '"+expertId+"' ");
				}
			}
			else
				if(null != expertId && !"".equals(expertId))
				{
				execSql.append(" and EXPERT_ID = '"+expertId+"' ");
				}
		}
		else
		{
			if(null != isFree && !"".equals(isFree))
			{
				execSql.append("  IS_CHARGE = '"+isFree+"' ");
				
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append(" and  EXPERT_ID = '"+expertId+"' ");
				}
			}
			else
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append("  EXPERT_ID = '"+expertId+"' ");
				}
		}
		
		
		
		execSql.append(" order by EXPERT_LEVEL asc LIMIT "+count);
		
		Object[] queryParams = new Object[]{
		};
		
		List<FushiYuce> yucelist = fushiYuceRepository.
				getEntityListBySql(FushiYuce.class, execSql.toString(), queryParams);
		
		return yucelist;
	}
	
	/**获取任三精选六组预测的的专家预测结果（包括：两码三期计划，乐选四期，前三六码复式）**/	
	private List<SixGroupYuce> getSixGroupYuceList(String maxIssueId,String isFree,String count,String predictionTbname,String expertId)
	{
		
		StringBuffer execSql = new StringBuffer("SELECT ID,PREDICTION_TYPE,EXPERT_ID,ISSUE_NUMBER,IS_CHARGE,MONEY,DROWN_NUMBER,EXPERT_LEVEL,WIN_RATE,ZJGROUPS,STATUS,GROUP1,"
				+ "GROUP2,GROUP3,GROUP4,GROUP5,GROUP6 FROM "+predictionTbname +" u  where u.ISSUE_NUMBER ="+maxIssueId );
		
		if(null != maxIssueId && !"".equals(maxIssueId))
		{
			execSql.append(" u.ISSUE_NUMBER ="+maxIssueId);
			
			if(null != isFree && !"".equals(isFree))
			{
				execSql.append(" and  IS_CHARGE = '"+isFree+"' ");
				
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append(" and EXPERT_ID = '"+expertId+"' ");
				}
			}
			else
				if(null != expertId && !"".equals(expertId))
				{
				execSql.append(" and EXPERT_ID = '"+expertId+"' ");
				}
		}
		else
		{
			if(null != isFree && !"".equals(isFree))
			{
				execSql.append("  IS_CHARGE = '"+isFree+"' ");
				
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append(" and  EXPERT_ID = '"+expertId+"' ");
				}
			}
			else
				if(null != expertId && !"".equals(expertId))
				{
					execSql.append("  EXPERT_ID = '"+expertId+"' ");
				}
		}
		
		
		
		execSql.append(" order by EXPERT_LEVEL asc LIMIT "+count);
		
		Object[] queryParams = new Object[]{
		};
		
		List<SixGroupYuce> yucelist = sixGroupYuceRepository.
				getEntityListBySql(SixGroupYuce.class, execSql.toString(), queryParams);
		
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
