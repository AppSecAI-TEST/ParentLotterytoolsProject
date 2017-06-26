package com.BYL.lotteryTools.backstage.lotteryManage.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryDiPinPlayDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryDiPinPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryDiPinPlayService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.exception.GlobalExceptionHandler;
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
			LotteryDiPinPlayDTO dto)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		LotteryDiPinPlay entity = lotteryDiPinPlayService.getLotteryDiPinPlayById(dto.getId());
		if(null != entity)
		{
			//修改
		}
		else
		{
			//添加
		}
		
		return map;
	}
	
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
	
	
}
