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

import com.BYL.lotteryTools.backstage.prediction.dto.OriginDataRuleDTO;
import com.BYL.lotteryTools.backstage.prediction.entity.OriginDataRule;
import com.BYL.lotteryTools.backstage.prediction.service.OriginDataRuleService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;
import com.BYL.lotteryTools.common.util.QueryResult;

/**
 * 源码筛选规则controller
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年3月23日 下午5:16:07
 */
@Controller
@RequestMapping("/origin")
public class OriginDataRuleController {

	private Logger logger = LoggerFactory.getLogger(OriginDataRuleController.class);
	
	@Autowired
	private OriginDataRuleService originDataRuleService;
	
	/**
	 * 根据id获取源码规则数据
	* @Title: getDetailOriginDataRule 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月24日 下午3:14:42 
	* @return OriginDataRuleDTO    返回类型 
	* @throws
	 */
	 @RequestMapping(value = "/getDetailOriginDataRule", method = RequestMethod.GET)
		public @ResponseBody OriginDataRuleDTO getDetailOriginDataRule(@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception
		{
			OriginDataRule originDataRule = originDataRuleService.getOriginDataRuleById(id);
			 
			
			OriginDataRuleDTO originDataRuleDTO = originDataRuleService.toDTO(originDataRule);
			return originDataRuleDTO;
		}
	 
	 /**
	  * 获取源码规则数据
	 * @Title: getOriginDataRuleList 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param page
	 * @param @param rows
	 * @param @param ruleName
	 * @param @param model
	 * @param @param httpSession
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @author banna
	 * @date 2017年3月24日 下午3:14:32 
	 * @return Map<String,Object>    返回类型 
	 * @throws
	  */
	 @RequestMapping(value = "/getOriginDataRuleList", method = RequestMethod.GET)
		public @ResponseBody Map<String,Object> getOriginDataRuleList(
				@RequestParam(value="page",required=false) int page,
				@RequestParam(value="rows",required=false) int rows,
				@RequestParam(value="ruleName",required=false) String ruleName,//源码规则名称
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
			if(null != ruleName&&!"".equals(ruleName.trim()))
			{
				params.add("%"+ruleName+"%");
				buffer.append(" and ruleName like ?").append(params.size());
			}
			
		 	
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			orderBy.put("createTime", "desc");
			
			QueryResult<OriginDataRule> lQueryResult = originDataRuleService
					.getOriginDataRuleList(OriginDataRule.class,
					buffer.toString(), params.toArray(),orderBy, pageable);
					
			List<OriginDataRule> list = lQueryResult.getResultList();
			Long totalrow = lQueryResult.getTotalRecord();
			
			//将实体转换为dto
			List<OriginDataRuleDTO> dtos = originDataRuleService.toDTOs(list);
			
			returnData.put("rows", dtos);
			returnData.put("total", totalrow);
		 	
		 	
		 	return returnData;
		}
	 
	 /**
	  * 保存或修改源码规则数据
	 * @Title: saveOrUpdateLotteryPlay 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param originDataRuleDTO
	 * @param @param session
	 * @param @return    设定文件 
	 * @author banna
	 * @date 2017年3月24日 下午3:14:12 
	 * @return ResultBean    返回类型 
	 * @throws
	  */
	 @RequestMapping(value = "/saveOrUpdateLotteryPlay", method = RequestMethod.GET)
		public @ResponseBody ResultBean saveOrUpdateLotteryPlay(OriginDataRuleDTO originDataRuleDTO,
				HttpSession session)
		{
		 	ResultBean resultBean = new ResultBean();
		 	
		 	OriginDataRule originDataRule = originDataRuleService.getOriginDataRuleById(originDataRuleDTO.getId());
		 	
		 	if(null != originDataRule)
		 	{
		 		originDataRule.setType(originDataRuleDTO.getType());
		 		originDataRule.setLocationOrContain(originDataRuleDTO.getLocationOrContain());
		 		originDataRule.setCiLocationNumber(originDataRuleDTO.getCiLocationNumber());
		 		originDataRule.setCiRuleFiled(originDataRuleDTO.getCiRuleFiled());
		 		originDataRule.setLiLocationNumber(originDataRuleDTO.getLiLocationNumber());
		 		originDataRule.setLiRuleFiled(originDataRuleDTO.getLiRuleFiled());
		 		originDataRule.setModify(LoginUtils.getAuthenticatedUserCode(session));
				originDataRule.setModifyTime(new Timestamp(System.currentTimeMillis()));
				
				originDataRuleService.update(originDataRule);
				
				logger.info("修改源码生成规则数据，id="+originDataRule.getId());
		 		
		 	}
		 	else
		 	{
		 		originDataRule = new OriginDataRule();
		 		try 
		 		{
					BeanUtil.copyBeanProperties(originDataRule, originDataRuleDTO);
					originDataRule.setIsDeleted(Constants.IS_NOT_DELETED);
					originDataRule.setCreator(LoginUtils.getAuthenticatedUserCode(session));
					originDataRule.setCreateTime(new Timestamp(System.currentTimeMillis()));
					originDataRule.setModify(LoginUtils.getAuthenticatedUserCode(session));
					originDataRule.setModifyTime(new Timestamp(System.currentTimeMillis()));
					
					originDataRuleService.save(originDataRule);
					
					logger.info("添加源码生成规则数据，ruleName="+originDataRule.getRuleName());
				} 
		 		catch (Exception e)
		 		{
					e.printStackTrace();
				}
		 	}
		 		
		 	
		 	return resultBean;
		}
	 
	 /**
	  * 删除源码生成规则数据
	 * @Title: deleteOriginRules 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param ids
	 * @param @param model
	 * @param @param httpSession
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @author banna
	 * @date 2017年3月24日 下午3:13:58 
	 * @return ResultBean    返回类型 
	 * @throws
	  */
	 @RequestMapping(value = "/deleteOriginRules", method = RequestMethod.POST)
		public @ResponseBody ResultBean  deleteOriginRules(
				@RequestParam(value="ids",required=false) String[] ids,
				ModelMap model,HttpSession httpSession) throws Exception {
		 
		 ResultBean resultBean = new ResultBean();
		 
		 OriginDataRule originDataRule;
		 for (String id : ids) 
			{
			 	originDataRule = originDataRuleService.getOriginDataRuleById(id);
			 	if(null != originDataRule)
			 	{
			 		originDataRule.setIsDeleted(Constants.IS_DELETED);
			 		originDataRule.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			 		originDataRule.setModifyTime(new Timestamp(System.currentTimeMillis()));
			 		
			 		
			 		originDataRuleService.update(originDataRule);
			 		
			 		 //日志输出
					 logger.info("删除源码规则数据--id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
				   
			 	}
			}
		 String returnMsg = "删除成功!";
		 resultBean.setStatus("success");
		 resultBean.setMessage(returnMsg);
		 
		 return resultBean;
				 
		 
	 }
	 
}
