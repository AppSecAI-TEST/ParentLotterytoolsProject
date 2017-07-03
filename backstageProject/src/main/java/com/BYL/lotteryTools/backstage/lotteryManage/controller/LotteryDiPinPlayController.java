package com.BYL.lotteryTools.backstage.lotteryManage.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryDiPinPlayDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryDiPinPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlayBulufangan;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryDiPinPlayService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.exception.GlobalExceptionHandler;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;
import com.BYL.lotteryTools.common.util.QueryResult;


@Controller
@RequestMapping("/lDipinPlay")
public class LotteryDiPinPlayController extends GlobalExceptionHandler
{

	@Autowired
	private LotteryDiPinPlayService lotteryDiPinPlayService;
	
	/**
	 * 根据id获取低频实体详情
	* @Title: getLotteryDiPinPlayById 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月26日 下午2:59:28 
	* @return LotteryDiPinPlayDTO    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getLotteryDiPinPlayById",method = RequestMethod.GET)
	public @ResponseBody LotteryDiPinPlayDTO getLotteryDiPinPlayById(
			@RequestParam(value="id",required=false) String id)
	{
		LotteryDiPinPlay entity = lotteryDiPinPlayService.getLotteryDiPinPlayById(id);
		LotteryDiPinPlayDTO dto = lotteryDiPinPlayService.toDTO(entity);
		
		return dto;
	}
	
	/**
	 * 获取低频玩法列表
	* @Title: getLotteryDiPinPlayList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @param rows
	* @param @param page
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月26日 下午2:59:42 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getLotteryDiPinPlayList",method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getLotteryDiPinPlayList(
			LotteryDiPinPlayDTO dto,
			@RequestParam(value="rows",required=false) int rows,
			@RequestParam(value="page",required=false) int page)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		QueryResult<LotteryDiPinPlay> queryResult = lotteryDiPinPlayService.getLotteryDiPinPlayList(dto, rows, page);
		
		List<LotteryDiPinPlay> list=  queryResult.getResultList();
		
		List<LotteryDiPinPlayDTO> dtos = lotteryDiPinPlayService.toDTOs(list);
		
		map.put("rows", dtos);
		map.put("total", queryResult.getTotalCount());
		map.put(Constants.MESSAGE_STR, "获取成功");
		return map;
	}
	
	/**
	 * 保存或修改低频玩法
	* @Title: saveOrUpdate 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月26日 下午2:59:56 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/saveOrUpdate",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> saveOrUpdate(
			LotteryDiPinPlayDTO dto,
			HttpSession session) throws Exception
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		LotteryDiPinPlay entity = lotteryDiPinPlayService.getLotteryDiPinPlayById(dto.getId());
		if(null != entity)
		{
			//修改
			entity.setPlanCode(dto.getPlanCode());
			entity.setEndNumber(dto.getEndNumber());
			entity.setMoreEndNumber(dto.getMoreEndNumber());
			entity.setMorePartKj(dto.getMorePartKj());
			entity.setMoreStartNumber(dto.getMoreStartNumber());
			entity.setNumOrChar(dto.getNumOrChar());
			entity.setOtherNum(dto.getOtherNum());
			entity.setOtherPlan(dto.getOtherPlan());
			entity.setPlanName(dto.getPlanName());
			entity.setRepeatNum(dto.getRepeatNum());
			entity.setStartNumber(dto.getStartNumber());
			entity.setLotteryType(dto.getLotteryType());
			entity.setBlueLotteryNumber(dto.getBlueLotteryNumber());
			entity.setLotteryNumber(dto.getLotteryNumber());
			entity.setIssueNumLen(dto.getIssueNumLen());
			entity.setCorrespondingTable(dto.getCorrespondingTable());
			entity.setModify(LoginUtils.getAuthenticatedUserCode(session));
			entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
			
			lotteryDiPinPlayService.update(entity);
			map.put(Constants.FLAG_STR, true);
			map.put(Constants.MESSAGE_STR, "修改成功");
		}
		else
		{
			//添加
			entity = new LotteryDiPinPlay();
			BeanUtil.copyBeanProperties(entity, dto);
			
			entity.setCreator(LoginUtils.getAuthenticatedUserCode(session));
			entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
			entity.setModify(LoginUtils.getAuthenticatedUserCode(session));
			entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
			entity.setIsDeleted(Constants.IS_NOT_DELETED);
			
			lotteryDiPinPlayService.save(entity);
			map.put(Constants.FLAG_STR, true);
			map.put(Constants.MESSAGE_STR, "添加成功");
		}
		
		return map;
	}
	
	/**
	 * 删除低频彩种数据方案
	* @Title: deleteLotteryDiPinPlay 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param ids
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年6月26日 下午5:49:17 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deleteLotteryDiPinPlay", method = RequestMethod.POST)
	public @ResponseBody ResultBean  deleteLotteryDiPinPlay(
			@RequestParam(value="ids",required=false) String[] ids,
			ModelMap model,HttpSession httpSession) throws Exception 
	{
		 ResultBean resultBean = new ResultBean();
		 
		 for (String id : ids) {
			 
			 LotteryDiPinPlay entity = lotteryDiPinPlayService.getLotteryDiPinPlayById(id);
			 entity.setIsDeleted(Constants.IS_DELETED);
			 entity.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			 entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
			 lotteryDiPinPlayService.update(entity);
		 }
		 
		 resultBean.setFlag(true);
		 resultBean.setMessage("删除成功");
		 return resultBean;
	}
	
	/**
	 * 
	* @Title: checkPlanName 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @param rows
	* @param @param page
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年7月3日 下午2:11:55 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/checkPlanName", method = RequestMethod.GET)
	public @ResponseBody ResultBean  checkPlanName(
			LotteryDiPinPlayDTO dto,
			ModelMap model,HttpSession httpSession) throws Exception {
		
		ResultBean resultBean = new ResultBean ();
		
		
		QueryResult<LotteryDiPinPlay> alist = lotteryDiPinPlayService.getLotteryDiPinPlayList(dto, Integer.MAX_VALUE, 1);
		
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
}
