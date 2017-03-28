package com.BYL.lotteryTools.backstage.prediction.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryPlayService;
import com.BYL.lotteryTools.backstage.prediction.dto.BasePredictionTypeDTO;
import com.BYL.lotteryTools.backstage.prediction.dto.PredictionTypeDTO;
import com.BYL.lotteryTools.backstage.prediction.entity.BasePredictionType;
import com.BYL.lotteryTools.backstage.prediction.entity.OriginDataRule;
import com.BYL.lotteryTools.backstage.prediction.entity.PredictionType;
import com.BYL.lotteryTools.backstage.prediction.service.BasePredictionTypeService;
import com.BYL.lotteryTools.backstage.prediction.service.OriginDataRuleService;
import com.BYL.lotteryTools.backstage.prediction.service.PredictionTypeService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;
import com.BYL.lotteryTools.common.util.QueryResult;

/**
 * 基础预测类型和预测类型的controller
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年3月23日 下午5:15:52
 */
@Controller
@RequestMapping("/ptype")
public class PredictionTypeController {

	private Logger logger = LoggerFactory.getLogger(PredictionTypeController.class);
	
	@Autowired
	private BasePredictionTypeService basePredictionTypeService;
	
	@Autowired
	private PredictionTypeService predictionTypeService;
	
	@Autowired
	private LotteryPlayService lotteryPlayService;
	
	@Autowired
	private OriginDataRuleService originDataRuleService;
	
	
	/**1、基础预测类型方法**/
	
	/**
	 * 获取基础预测类型数据
	* @Title: getBasePredictionType 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月24日 下午3:20:57 
	* @return BasePredictionTypeDTO    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getBasePredictionType", method = RequestMethod.GET)
	public @ResponseBody BasePredictionTypeDTO getBasePredictionType(@RequestParam(value="id",required=false) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		BasePredictionType basePredictionType = basePredictionTypeService.getBasePredictionTypeById(id);
		 
		
		BasePredictionTypeDTO basePredictionTypeDTO = basePredictionTypeService.toDTO(basePredictionType);
		return basePredictionTypeDTO;
	}
 
	/**
	 * 
	* @Title: getBasePredictionTypeList 
	* @Description: 获取基础预测类型列表
	* @param @param page
	* @param @param rows
	* @param @param basePredictionName
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月24日 下午3:22:13 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	 @RequestMapping(value = "/getBasePredictionTypeList", method = RequestMethod.GET)
		public @ResponseBody Map<String,Object> getBasePredictionTypeList(
				@RequestParam(value="page",required=false) int page,
				@RequestParam(value="rows",required=false) int rows,
				@RequestParam(value="basePredictionName",required=false) String basePredictionName,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	Map<String,Object> returnData = new HashMap<String, Object>();
		 	
		 	/**补录的表名处理：之后实在没办法就直接写语句执行sql吧，插入补录数据**/
			
		 	//放置分页参数
			Pageable pageable = new PageRequest(page-1,rows);
			
			//参数
			StringBuffer buffer = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			
			//只查询未删除数据
			params.add("1");//只查询有效的数据
			buffer.append(" isDeleted = ?").append(params.size());
			
			//连接查询条件
			if(null != basePredictionName&&!"".equals(basePredictionName.trim()))
			{
				params.add("%"+basePredictionName+"%");
				buffer.append(" and basePredictionName like ?").append(params.size());
			}
			
		 	
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			orderBy.put("createTime", "desc");
			
			QueryResult<BasePredictionType> lQueryResult = basePredictionTypeService
					.getBasePredictionTypeList(BasePredictionType.class,
					buffer.toString(), params.toArray(),orderBy, pageable);
					
			List<BasePredictionType> list = lQueryResult.getResultList();
			Long totalrow = lQueryResult.getTotalRecord();
			
			//将实体转换为dto
			List<BasePredictionTypeDTO> dtos = basePredictionTypeService.toDTOs(list);
			
			returnData.put("rows", dtos);
			returnData.put("total", totalrow);
		 	
		 	
		 	return returnData;
		}
	 /**
	  * 保存或修改基础预测类型数据
	 * @Title: saveOrUpdateBasePType 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param basePredictionTypeDTO
	 * @param @param session
	 * @param @return    设定文件 
	 * @author banna
	 * @date 2017年3月24日 下午3:24:13 
	 * @return ResultBean    返回类型 
	 * @throws
	  */
	 @RequestMapping(value = "/saveOrUpdateBasePType", method = RequestMethod.GET)
		public @ResponseBody ResultBean saveOrUpdateBasePType(BasePredictionTypeDTO basePredictionTypeDTO,
				HttpSession session)
		{
		 	ResultBean resultBean = new ResultBean();
		 	
		 	BasePredictionType basePredictionType = basePredictionTypeService.
		 			getBasePredictionTypeById(basePredictionTypeDTO.getId());
		 	
		 	if(null != basePredictionType)
		 	{
		 		basePredictionType.setOriginDataSize(basePredictionTypeDTO.getOriginDataSize());
		 		basePredictionType.setnPlan(basePredictionType.getnPlan());
		 		basePredictionType.setBasePredictionName(basePredictionTypeDTO.getBasePredictionName());
		 		basePredictionType.setMethodName(basePredictionType.getMethodName());
		 		basePredictionType.setModify(LoginUtils.getAuthenticatedUserCode(session));
		 		basePredictionType.setModifyTime(new Timestamp(System.currentTimeMillis()));
		 		basePredictionType.setFlowDataSize((Integer.parseInt(basePredictionTypeDTO.getOriginDataSize())*
		 				Integer.parseInt(basePredictionTypeDTO.getnPlan()))+"");
		 		
		 		OriginDataRule originDataRule = originDataRuleService.getOriginDataRuleById(basePredictionTypeDTO.getOriginDataRuleId());
				basePredictionType.setOriginDataRule(originDataRule);
				
				
				basePredictionTypeService.update(basePredictionType);
				
				resultBean.setMessage("修改成功");
				
				logger.info("修改基础预测类型数据，id="+basePredictionType.getId());
		 		
		 	}
		 	else
		 	{
		 		basePredictionType = new BasePredictionType();
		 		try 
		 		{
					BeanUtil.copyBeanProperties(basePredictionType, basePredictionTypeDTO);
					basePredictionType.setIsDeleted(Constants.IS_NOT_DELETED);
					basePredictionType.setCreator(LoginUtils.getAuthenticatedUserCode(session));
					basePredictionType.setCreateTime(new Timestamp(System.currentTimeMillis()));
					basePredictionType.setModify(LoginUtils.getAuthenticatedUserCode(session));
					basePredictionType.setModifyTime(new Timestamp(System.currentTimeMillis()));
					
					OriginDataRule originDataRule = originDataRuleService.getOriginDataRuleById(basePredictionTypeDTO.getOriginDataRuleId());
					basePredictionType.setOriginDataRule(originDataRule);
					
					basePredictionTypeService.save(basePredictionType);
					
					logger.info("添加基础数据类型数据，ruleName="+basePredictionType.getBasePredictionName());
					
					resultBean.setMessage("添加成功");
				} 
		 		catch (Exception e)
		 		{
					e.printStackTrace();
				}
		 	}
		 		
		 	
		 	return resultBean;
		}
	 
	/**
	 * 删除基础预测类型数据
	* @Title: deleteBasePredictionType 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param ids
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月24日 下午3:58:44 
	* @return ResultBean    返回类型 
	* @throws
	 */
	 @RequestMapping(value = "/deleteBasePredictionType", method = RequestMethod.POST)
		public @ResponseBody ResultBean  deleteBasePredictionType(
				@RequestParam(value="ids",required=false) String[] ids,
				ModelMap model,HttpSession httpSession) throws Exception {
		 
		 ResultBean resultBean = new ResultBean();
		 
		BasePredictionType basePredictionType;
		 for (String id : ids) 
			{
			 	basePredictionType = basePredictionTypeService.getBasePredictionTypeById(id);
			 	if(null != basePredictionType)
			 	{
			 		basePredictionType.setIsDeleted(Constants.IS_DELETED);
			 		basePredictionType.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			 		basePredictionType.setModifyTime(new Timestamp(System.currentTimeMillis()));
			 		basePredictionType.setOriginDataRule(null);
			 		
			 		basePredictionTypeService.update(basePredictionType);
			 		
			 		 //日志输出
					 logger.info("删除基础预测类型数据--id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
				   
			 	}
			}
		 String returnMsg = "删除成功!";
		 resultBean.setStatus("success");
		 resultBean.setMessage(returnMsg);
		 
		 return resultBean;
				 
		 
	 }
	 
	 /**
	  * 获取基本预测类型对应的源码规则id
	 * @Title: getOrderRuleId 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param id
	 * @param @param model
	 * @param @param httpSession
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @author banna
	 * @date 2017年3月28日 上午10:30:46 
	 * @return Map<String,Object>    返回类型 
	 * @throws
	  */
	 @RequestMapping(value = "/getOrderRuleId", method = RequestMethod.GET)
		public @ResponseBody Map<String,Object> getOrderRuleId(@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	Map<String,Object> result = new HashMap<String, Object>();
			BasePredictionType basePredictionType = basePredictionTypeService.getBasePredictionTypeById(id);
			
			if(null != basePredictionType && null != basePredictionType.getOriginDataRule())
			{
				result.put("lpBuluId", basePredictionType.getOriginDataRule().getId());
			}
			else
			{
				result.put("lpBuluId", "");
			}
			
			return result;
		}
	 
	 
	 /**2、预测类型方法**/
		
		/**
		 * 获取预测类型数据
		* @Title: getBasePredictionType 
		* @Description: TODO(这里用一句话描述这个方法的作用) 
		* @param @param id
		* @param @param model
		* @param @param httpSession
		* @param @return
		* @param @throws Exception    设定文件 
		* @author banna
		* @date 2017年3月24日 下午3:20:57 
		* @return BasePredictionTypeDTO    返回类型 
		* @throws
		 */
		@RequestMapping(value = "/getPredictionType", method = RequestMethod.GET)
		public @ResponseBody PredictionTypeDTO getPredictionType(@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception
		{
			PredictionType predictionType = predictionTypeService.getPredictionTypeById(id);
			
			PredictionTypeDTO predictionTypeDTO = predictionTypeService.toDTO(predictionType);
			
			return predictionTypeDTO;
		}
	 
		/**
		 * 获取预测类型列表
		* @Title: getPredictionTypeList 
		* @Description: TODO(这里用一句话描述这个方法的作用) 
		* @param @param page
		* @param @param rows
		* @param @param predictionName
		* @param @param model
		* @param @param httpSession
		* @param @return
		* @param @throws Exception    设定文件 
		* @author banna
		* @date 2017年3月24日 下午4:11:53 
		* @return Map<String,Object>    返回类型 
		* @throws
		 */
		 @RequestMapping(value = "/getPredictionTypeList", method = RequestMethod.GET)
			public @ResponseBody Map<String,Object> getPredictionTypeList(
					@RequestParam(value="page",required=false) int page,
					@RequestParam(value="rows",required=false) int rows,
					@RequestParam(value="predictionName",required=false) String predictionName,
					ModelMap model,HttpSession httpSession) throws Exception
			{
			 	Map<String,Object> returnData = new HashMap<String, Object>();
			 	
			 	/**补录的表名处理：之后实在没办法就直接写语句执行sql吧，插入补录数据**/
				
			 	//放置分页参数
				Pageable pageable = new PageRequest(page-1,rows);
				
				//参数
				StringBuffer buffer = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				
				//只查询未删除数据
				params.add("1");//只查询有效的数据
				buffer.append(" isDeleted = ?").append(params.size());
				
				//连接查询条件
				if(null != predictionName&&!"".equals(predictionName.trim()))
				{
					params.add("%"+predictionName+"%");
					buffer.append(" and predictionName like ?").append(params.size());
				}
				
			 	
				//排序
				LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
				orderBy.put("createTime", "desc");
				
				QueryResult<PredictionType> lQueryResult = predictionTypeService
						.getPredictionTypeList(PredictionType.class,
						buffer.toString(), params.toArray(),orderBy, pageable);
						
				List<PredictionType> list = lQueryResult.getResultList();
				Long totalrow = lQueryResult.getTotalRecord();
				
				//将实体转换为dto
				List<PredictionTypeDTO> dtos = predictionTypeService.toDTOs(list);
				
				returnData.put("rows", dtos);
				returnData.put("total", totalrow);
			 	
			 	
			 	return returnData;
			}
		 
		 
		 @RequestMapping(value = "/saveOrUpdatePredictionType", method = RequestMethod.GET)
			public @ResponseBody ResultBean saveOrUpdatePredictionType(PredictionTypeDTO predictionTypeDTO,
					HttpSession session)
			{
			 	ResultBean resultBean = new ResultBean();
			 	
			 	PredictionType predictionType = predictionTypeService.getPredictionTypeById(predictionTypeDTO.getId());
			 	
			 	if(null != predictionType)
			 	{
			 		predictionType.setPredictionName(predictionTypeDTO.getPredictionName());
			 		predictionType.setPredictionCode(predictionTypeDTO.getPredictionCode());
			 		predictionType.setPredictionTable(predictionTypeDTO.getPredictionTable());
			 		predictionType.setLiangmaTableName(predictionTypeDTO.getLiangmaTableName());
			 		predictionType.setSanmaTableName(predictionTypeDTO.getSanmaTableName());
			 		predictionType.setSimaTableName(predictionTypeDTO.getSanmaTableName());
			 		predictionType.setLiumaTableName(predictionTypeDTO.getLiumaTableName());
			 		
			 		LotteryPlay lotteryPlay = lotteryPlayService.getLotteryPlayById(predictionTypeDTO.getLotteryPlayId());
			 		predictionType.setLotteryPlay(lotteryPlay);
			 		
			 		BasePredictionType basePredictionType = basePredictionTypeService.
			 				getBasePredictionTypeById(predictionTypeDTO.getBasePredictionTypeId());
			 		predictionType.setBasePredictionType(basePredictionType);
			 		
			 		
			 		predictionType.setModify(LoginUtils.getAuthenticatedUserCode(session));
			 		predictionType.setModifyTime(new Timestamp(System.currentTimeMillis()));
					
					predictionTypeService.update(predictionType);
					
					logger.info("修改预测类型数据，id="+predictionType.getId());
					
					resultBean.setMessage("修改成功");
			 		
			 	}
			 	else
			 	{
			 		predictionType = new PredictionType();
			 		try 
			 		{
						BeanUtil.copyBeanProperties(predictionType, predictionTypeDTO);
						predictionType.setIsDeleted(Constants.IS_NOT_DELETED);
						predictionType.setCreator(LoginUtils.getAuthenticatedUserCode(session));
						predictionType.setCreateTime(new Timestamp(System.currentTimeMillis()));
						predictionType.setModify(LoginUtils.getAuthenticatedUserCode(session));
						predictionType.setModifyTime(new Timestamp(System.currentTimeMillis()));
						
						LotteryPlay lotteryPlay = lotteryPlayService.getLotteryPlayById(predictionTypeDTO.getLotteryPlayId());
				 		predictionType.setLotteryPlay(lotteryPlay);
				 		
				 		BasePredictionType basePredictionType = basePredictionTypeService.
				 				getBasePredictionTypeById(predictionTypeDTO.getBasePredictionTypeId());
				 		predictionType.setBasePredictionType(basePredictionType);
				 		
						
						predictionTypeService.toDTO(predictionType);
						
						logger.info("添加数据类型数据，predictionName="+predictionType.getPredictionName());
						
						resultBean.setMessage("添加成功");
					} 
			 		catch (Exception e)
			 		{
						e.printStackTrace();
					}
			 	}
			 		
			 	
			 	return resultBean;
			}
		 
		/**
		 * 删除基础预测类型数据
		* @Title: deleteBasePredictionType 
		* @Description: TODO(这里用一句话描述这个方法的作用) 
		* @param @param ids
		* @param @param model
		* @param @param httpSession
		* @param @return
		* @param @throws Exception    设定文件 
		* @author banna
		* @date 2017年3月24日 下午3:58:44 
		* @return ResultBean    返回类型 
		* @throws
		 */
		 @RequestMapping(value = "/deletePredictionType", method = RequestMethod.POST)
			public @ResponseBody ResultBean  deletePredictionType(
					@RequestParam(value="ids",required=false) String[] ids,
					ModelMap model,HttpSession httpSession) throws Exception {
			 
			 ResultBean resultBean = new ResultBean();
			 
			PredictionType predictionType ;
			 for (String id : ids) 
				{
				 	predictionType = predictionTypeService.getPredictionTypeById(id);
				 	if(null != predictionType)
				 	{
				 		predictionType.setIsDeleted(Constants.IS_DELETED);
				 		predictionType.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
				 		predictionType.setModifyTime(new Timestamp(System.currentTimeMillis()));
				 		
				 		
				 		predictionTypeService.update(predictionType);
				 		
				 		 //日志输出
						 logger.info("删除预测类型数据--id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
					   
				 	}
				}
			 String returnMsg = "删除成功!";
			 resultBean.setStatus("success");
			 resultBean.setMessage(returnMsg);
			 
			 return resultBean;
					 
			 
		 }
	 
		
}
