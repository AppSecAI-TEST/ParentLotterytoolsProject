package com.BYL.lotteryTools.backstage.lotteryManage.controller;

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

import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryPlayBuluPlanDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryPlayDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlayBulufangan;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryPlayBuLuPlanService;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryPlayService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;
import com.BYL.lotteryTools.common.util.QueryResult;

/**
 * 彩种维护controller
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年3月3日 下午1:59:35
 */
@Controller
@RequestMapping("/lotteryManage")
public class LotteryManageController 
{
	private static final Logger LOG = LoggerFactory.getLogger(LotteryManageController.class);
			
	
	@Autowired
	private LotteryPlayService lotteryPlayService;
	
	@Autowired
	private LotteryPlayBuLuPlanService lotteryPlayBuLuPlanService;
	
	public void save()
	{
		
	}
	
	 /******2.补录信息管理模块******/
	 
	 /**
	  * 根据id获取补录信息数据
	  * @param id
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getDetailLotteryPlay", method = RequestMethod.GET)
		public @ResponseBody LotteryPlayDTO getDetailLotteryPlay(@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception
		{
			LotteryPlay lotteryPlay = lotteryPlayService.getLotteryPlayById(id);
			 
			
			LotteryPlayDTO lotteryPlayDTO = lotteryPlayService.toDTO(lotteryPlay);
			return lotteryPlayDTO;
		}
	 
	 /**
	  * 获取当前补录信息对于的补录方案id
	  * @param id
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getLotteryPlayPlanId", method = RequestMethod.GET)
		public @ResponseBody Map<String,Object> getLotteryPlayPlanId(@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	Map<String,Object> result = new HashMap<String, Object>();
			LotteryPlay lotteryPlay = lotteryPlayService.getLotteryPlayById(id);
			
			if(null !=lotteryPlay && null != lotteryPlay.getLotteryPlayBulufangan())
			{
				result.put("lpBuluId", lotteryPlay.getLotteryPlayBulufangan().getId());
			}
			else
			{
				result.put("lpBuluId", "");
			}
			
			return result;
		}
	 
	 /**
	  * 获取补录信息数据列表
	  * @param page
	  * @param rows
	  * @param name
	  * @param province
	  * @param lotteryType
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getLotteryPlayList", method = RequestMethod.GET)
		public @ResponseBody Map<String,Object> getLotteryPlayList(
				@RequestParam(value="page",required=false) int page,
				@RequestParam(value="rows",required=false) int rows,
				@RequestParam(value="name",required=false) String name,//根据彩种名称
				@RequestParam(value="provinceCode",required=false) String provinceCode,
				@RequestParam(value="lotteryType",required=false) String lotteryType,//彩种，体彩/福彩
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
			if(null != name&&!"".equals(name.trim()))
			{
				params.add("%"+name+"%");
				buffer.append(" and name like ?").append(params.size());
			}
			
			if(null != provinceCode && !"".equals(provinceCode)&& !Constants.PROVINCE_ALL.equals(provinceCode))
			{
				params.add(provinceCode);
				buffer.append(" and province = ?").append(params.size());
			}
			
			if(null != lotteryType && !"".equals(lotteryType))
			{
				params.add(lotteryType);
				buffer.append(" and lotteryType = ?").append(params.size());
			}
			
			
		 	
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			orderBy.put("createTime", "desc");
			
			QueryResult<LotteryPlay> lQueryResult = lotteryPlayService
					.getLotteryPlayList(LotteryPlay.class,
					buffer.toString(), params.toArray(),orderBy, pageable);
					
			List<LotteryPlay> list = lQueryResult.getResultList();
			Long totalrow = lQueryResult.getTotalRecord();
			
			//将实体转换为dto
			List<LotteryPlayDTO> lotteryPlayDTOs = lotteryPlayService.toRDTOS(list);
			
			returnData.put("rows", lotteryPlayDTOs);
			returnData.put("total", totalrow);
		 	
		 	
		 	return returnData;
		}
	 
	 /**
	  * 保存或修改补录信息数据
	  * @param id
	  * @param code
	  * @param name
	  * @param province
	  * @param correspondingTable
	  * @param lotteryNumber
	  * @param lotteryType
	  * @param lpbuId
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/saveOrUpdateLotteryPlay", method = RequestMethod.GET)
		public @ResponseBody ResultBean saveOrUpdateLotteryPlay(
				@RequestParam(value="id",required=false) String id,
				@RequestParam(value="code",required=false) String code,
				@RequestParam(value="name",required=false) String name,
				@RequestParam(value="province",required=false) String province,
				@RequestParam(value="correspondingTable",required=false) String correspondingTable,//对应表
				@RequestParam(value="lotteryNumber",required=false) String lotteryNumber,//开奖号码个数
				@RequestParam(value="lotteryType",required=false) String lotteryType,//彩种，体彩/福彩
				@RequestParam(value="lpbuId",required=false) String lpbuId,//补录方案id
				@RequestParam(value="issueNumLen",required=false) String issueNumLen,//期号长度
				@RequestParam(value="lineCount",required=false) String lineCount,//每天开出的最大期号
				ModelMap model,HttpSession httpSession) throws Exception
		{
		   ResultBean resultBean = new ResultBean ();
		   
		   LotteryPlay lotteryPlay = lotteryPlayService.getLotteryPlayById(id);
		   
		   
		   if(null != lotteryPlay)
		   {
			   
			   lotteryPlay.setCode(code);
			   lotteryPlay.setName(name);
			   lotteryPlay.setProvince(province);
			   lotteryPlay.setCorrespondingTable(correspondingTable);
			   lotteryPlay.setLotteryNumber(lotteryNumber);
			   lotteryPlay.setLotteryType(lotteryType);
			   lotteryPlay.setIssueNumLen(issueNumLen);
			   lotteryPlay.setLineCount(lineCount);
			   
			   LotteryPlayBulufangan lotteryPlayBulufangan = lotteryPlayBuLuPlanService.getLotteryPlayBulufanganById(lpbuId);
			   
			   lotteryPlay.setLotteryPlayBulufangan(lotteryPlayBulufangan);//添加补录信息和补录方案的关联关系
			   lotteryPlay.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			   lotteryPlay.setModifyTime(new Timestamp(System.currentTimeMillis()));
			   
			   
			   
			   lotteryPlayService.update(lotteryPlay);
			   
			   
			   resultBean.setMessage("修改补录信息数据成功!");
			   resultBean.setStatus("success");
			   //日志输出
				 LOG.info("修改补录信息数据id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
			   
		   }
		   else
		   {
			   lotteryPlay = new LotteryPlay();
			   lotteryPlay.setCode(code);
			   lotteryPlay.setName(name);
			   lotteryPlay.setProvince(province);
			   lotteryPlay.setCorrespondingTable(correspondingTable);
			   lotteryPlay.setLotteryNumber(lotteryNumber);
			   lotteryPlay.setLotteryType(lotteryType);
			   lotteryPlay.setIssueNumLen(issueNumLen);
			   lotteryPlay.setLineCount(lineCount);
			   
			   LotteryPlayBulufangan lotteryPlayBulufangan = lotteryPlayBuLuPlanService.getLotteryPlayBulufanganById(lpbuId);
			   
			   lotteryPlay.setLotteryPlayBulufangan(lotteryPlayBulufangan);//添加补录信息和补录方案的关联关系
			   lotteryPlay.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			   lotteryPlay.setModifyTime(new Timestamp(System.currentTimeMillis()));
			   
			   
			   lotteryPlay.setCreator(LoginUtils.getAuthenticatedUserCode(httpSession));
			   lotteryPlay.setCreateTime(new Timestamp(System.currentTimeMillis()));
			   lotteryPlay.setIsDeleted("1");
			   
			   lotteryPlayService.save(lotteryPlay);
			   
			   
			   LOG.info("添加补录信息数据--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
			   
			   resultBean.setMessage("添加补录信息数据成功!");
			   resultBean.setStatus("success");
			   
			   
		   }
		   
		   
		   return resultBean;
		}
	 
	 
	 /**
	  * 删除补录信息数据
	  * @param ids
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/deleteLotteryPlays", method = RequestMethod.POST)
		public @ResponseBody ResultBean  deleteLotteryPlays(
				@RequestParam(value="ids",required=false) String[] ids,
				ModelMap model,HttpSession httpSession) throws Exception {
		 
		 ResultBean resultBean = new ResultBean();
		 
		 LotteryPlay lotteryPlay;
		 for (String id : ids) 
			{
			 	lotteryPlay = lotteryPlayService.getLotteryPlayById(id);
			 	if(null != lotteryPlay)
			 	{
			 		lotteryPlay.setIsDeleted("0");
			 		lotteryPlay.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			 		lotteryPlay.setModifyTime(new Timestamp(System.currentTimeMillis()));
			 		lotteryPlay.setLotteryPlayBulufangan(null);//删除和补录方案的关联关系,置关联字段为null（多对一的删除关联关系的方式；若为多对多的关联管理则可以使用创建新的实体放入关联字段中来删除中间表的关联关系）
			 		
			 		
			 		lotteryPlayService.update(lotteryPlay);
			 		
			 		 //日志输出
					 LOG.info("删除补录信息数据--id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
				   
			 	}
			}
		 String returnMsg = "删除成功!";
		 resultBean.setStatus("success");
		 resultBean.setMessage(returnMsg);
		 
		 return resultBean;
				 
		 
	 }
	
	 /******3.补录方案管理模块******/
	 
	 /**
	  * 根据id获取补录方案数据
	  * @param id
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getDetailLotteryPlayBuluPlan", method = RequestMethod.GET)
		public @ResponseBody LotteryPlayBuluPlanDTO getDetailLotteryPlayBuluPlan(@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	LotteryPlayBulufangan lotteryPlayBulufangan = lotteryPlayBuLuPlanService.getLotteryPlayBulufanganById(id);
			 
			
		 	LotteryPlayBuluPlanDTO lotteryPlayBuluPlanDTO = lotteryPlayBuLuPlanService.toDTO(lotteryPlayBulufangan);
			return lotteryPlayBuluPlanDTO;
		}
	 
	 /**
	  * 获取补录方案数据列表
	  * @param page
	  * @param rows
	  * @param name
	  * @param province
	  * @param lotteryType
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getLotteryPlayBuluPlanList", method = RequestMethod.GET)
		public @ResponseBody Map<String,Object> getLotteryPlayBuluPlanList(
				@RequestParam(value="page",required=false) int page,
				@RequestParam(value="rows",required=false) int rows,
				@RequestParam(value="numOrChar",required=false) String numOrChar,//根据彩种名称
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	Map<String,Object> returnData = new HashMap<String, Object>();
		 	
			
		 	//放置分页参数
			Pageable pageable = new PageRequest(page-1,rows);
			
			//参数
			StringBuffer buffer = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			
			//只查询未删除数据
			params.add("1");//只查询有效的数据
			buffer.append(" isDeleted = ?").append(params.size());
			
			//连接查询条件
			if(null != numOrChar&&!"".equals(numOrChar.trim()))
			{
				params.add(numOrChar);
				buffer.append(" and numOrChar = ?").append(params.size());
			}
			
			
			
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			orderBy.put("numOrChar", "asc");
			orderBy.put("createTime", "desc");
			
			QueryResult<LotteryPlayBulufangan> lQueryResult = lotteryPlayBuLuPlanService
					.getLotteryPlayBulufanganList(LotteryPlayBulufangan.class,
					buffer.toString(), params.toArray(),orderBy, pageable);
					
			List<LotteryPlayBulufangan> list = lQueryResult.getResultList();
			Long totalrow = lQueryResult.getTotalRecord();
			
			//将实体转换为dto
			List<LotteryPlayBuluPlanDTO> lotteryPlayBuluPlanDTOs = lotteryPlayBuLuPlanService.toRDTOS(list);
			
			returnData.put("rows", lotteryPlayBuluPlanDTOs);
			returnData.put("total", totalrow);
		 	
		 	
		 	return returnData;
		}
	  
	 /**
	  * 
	  * 保存或修改补录方案数据
	  * @param id
	  * @param code
	  * @param name
	  * @param province
	  * @param correspondingTable
	  * @param lotteryNumber
	  * @param lotteryType
	  * @param lpbuId
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/saveOrUpdateLotteryPlayBuluPlan", method = RequestMethod.GET)
		public @ResponseBody ResultBean saveOrUpdateLotteryPlayBuluPlan(
				@RequestParam(value="id",required=false) String id,
				@RequestParam(value="planName",required=false) String planName,
				@RequestParam(value="startNumber",required=false) String startNumber,
				@RequestParam(value="endNumber",required=false) String endNumber,
				@RequestParam(value="numOrChar",required=false) String numOrChar,
				@RequestParam(value="otherPlan",required=false) String otherPlan,
				@RequestParam(value="otherNum",required=false) String otherNum,
				@RequestParam(value="repeatNum",required=false) String repeatNum,//开奖号码是否重复
				ModelMap model,HttpSession httpSession) throws Exception
		{
		   ResultBean resultBean = new ResultBean ();
		   
		   LotteryPlayBulufangan lotteryPlayBulufangan = lotteryPlayBuLuPlanService.getLotteryPlayBulufanganById(id);
		   
		   
		   if(null != lotteryPlayBulufangan)
		   {
			   lotteryPlayBulufangan.setPlanName(planName);
			   lotteryPlayBulufangan.setStartNumber(startNumber);
			   lotteryPlayBulufangan.setEndNumber(endNumber);
			   lotteryPlayBulufangan.setNumOrChar(numOrChar);
			   lotteryPlayBulufangan.setOtherPlan(otherPlan);
			   lotteryPlayBulufangan.setOtherNum(otherNum);
			   lotteryPlayBulufangan.setRepeatNum(repeatNum);
			   
			   lotteryPlayBulufangan.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			   lotteryPlayBulufangan.setModifyTime(new Timestamp(System.currentTimeMillis()));
			   
			   
			   
			   lotteryPlayBuLuPlanService.update(lotteryPlayBulufangan);
			   
			   
			   resultBean.setMessage("修改补录方案数据成功!");
			   resultBean.setStatus("success");
			   //日志输出
				 LOG.info("修改补录方案数据id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
			   
		   }
		   else
		   {
			   lotteryPlayBulufangan = new LotteryPlayBulufangan();
			   lotteryPlayBulufangan.setPlanName(planName);
			   lotteryPlayBulufangan.setStartNumber(startNumber);
			   lotteryPlayBulufangan.setEndNumber(endNumber);
			   lotteryPlayBulufangan.setNumOrChar(numOrChar);
			   lotteryPlayBulufangan.setOtherPlan(otherPlan);
			   lotteryPlayBulufangan.setOtherNum(otherNum);
			   lotteryPlayBulufangan.setRepeatNum(repeatNum);
			   
			   
			   lotteryPlayBulufangan.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			   lotteryPlayBulufangan.setModifyTime(new Timestamp(System.currentTimeMillis()));
			   
			   
			   lotteryPlayBulufangan.setCreator(LoginUtils.getAuthenticatedUserCode(httpSession));
			   lotteryPlayBulufangan.setCreateTime(new Timestamp(System.currentTimeMillis()));
			   lotteryPlayBulufangan.setIsDeleted("1");
			   
			   lotteryPlayBuLuPlanService.save(lotteryPlayBulufangan);
			   
			   
			   LOG.info("添加补录方案数据--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
			   
			   resultBean.setMessage("添加补录方案数据成功!");
			   resultBean.setStatus("success");
			   
			   
		   }
		   
		   
		   return resultBean;
		}
	 
	 /**
	  * 删除补录方案数据
	  * @param ids
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/deleteLotteryPlayBuluPlans", method = RequestMethod.POST)
		public @ResponseBody ResultBean  deleteLotteryPlayBuluPlans(
				@RequestParam(value="ids",required=false) String[] ids,
				ModelMap model,HttpSession httpSession) throws Exception {
		 
		 ResultBean resultBean = new ResultBean();
		 
		 LotteryPlayBulufangan lotteryPlayBulufangan ;
		 for (String id : ids) 
			{
			 	lotteryPlayBulufangan = lotteryPlayBuLuPlanService.getLotteryPlayBulufanganById(id);
			 	if(null != lotteryPlayBulufangan)
			 	{
			 		lotteryPlayBulufangan.setIsDeleted("0");
			 		lotteryPlayBulufangan.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			 		lotteryPlayBulufangan.setModifyTime(new Timestamp(System.currentTimeMillis()));
			 		
			 		
			 		lotteryPlayBuLuPlanService.update(lotteryPlayBulufangan);
			 		
			 		 //日志输出
					 LOG.info("删除补录方案数据--id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
				   
			 	}
			}
		 String returnMsg = "删除成功!";
		 resultBean.setStatus("success");
		 resultBean.setMessage(returnMsg);
		 
		 return resultBean;
				 
		 
	 }
	 
	 /**
	  * 校验方案名称唯一
	  * @param id
	  * @param name
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/checkPlanName", method = RequestMethod.POST)
		public @ResponseBody ResultBean  checkPlanName(
				@RequestParam(value="id",required=false) String id,
				@RequestParam(value="name",required=false) String name,
				ModelMap model,HttpSession httpSession) throws Exception {
			
			ResultBean resultBean = new ResultBean ();
			
			//放置分页参数
			Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
			
			//参数
			StringBuffer buffer = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			
			//只查询未删除数据
			params.add("1");//只查询有效的数据
			buffer.append(" isDeleted = ?").append(params.size());
			
			if(null != name && !"".equals(name))
			{
				params.add(name);
				buffer.append(" and planName = ?").append(params.size());
			}
			
			
			if(null != id && !"".equals(id))
			{//校验修改中的值的唯一性
				params.add(id);
				buffer.append(" and id != ?").append(params.size());
			}
			
			//排序
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			
			QueryResult<LotteryPlayBulufangan> alist = lotteryPlayBuLuPlanService.getLotteryPlayBulufanganList(LotteryPlayBulufangan.class, buffer.toString(), params.toArray(),
					orderBy, pageable);
			
			if(alist.getResultList().size()>0)
			{
				resultBean.setExist(true);//若查询的数据条数大于0，则当前输入值已存在，不符合唯一性校验
			}
			else
			{
				resultBean.setExist(false);
			}
			
			return resultBean;
			
		}
	 
	 /**
	  * 校验当前待删除补录方案是否被使用，若在被使用则不可以进行删除操作
	  * @param id
	  * @param model
	  * @param httpSession
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/checkCouldDeleted", method = RequestMethod.GET)
		public @ResponseBody ResultBean  checkCouldDeleted(
				@RequestParam(value="id",required=false) String id,
				ModelMap model,HttpSession httpSession) throws Exception {
			
			ResultBean resultBean = new ResultBean ();
			
			LotteryPlayBulufangan bulufangan = lotteryPlayBuLuPlanService.getLotteryPlayBulufanganById(id);
			
			List<LotteryPlay> list = bulufangan.getLotteryPlays();
			
			
			if(list.size()>0)//若获取关联的补录信息数据大于0个,不可以进行删除
			{
				resultBean.setExist(false);
			}
			else
			{
				resultBean.setExist(true);
			}
			
			return resultBean;
			
		}
}
