package com.BYL.lotteryTools.backstage.lotteryGroup.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.LotteryGroupDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LGroupLevel;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaBindOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaGroupUpLevelRecord;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LGroupLevelService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaBindbuyerAndGroupService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaGroupUpLevelService;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.CodeSuccessResult;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.exception.GlobalExceptionHandler;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.QRCodeUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

/**
 * 彩票群控制层
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月18日 下午4:52:17
 */
@Controller
@RequestMapping("/lgroup")
public class LotteryGroupController extends GlobalExceptionHandler
{
	private static final Logger LOG = LoggerFactory.getLogger(LotteryGroupController.class);
	
	@Autowired
	private LotteryGroupService lotteryGroupService;
	
	@Autowired
	private RelaGroupUpLevelService relaGroupUpLevelService;
	
	@Autowired
	private RongyunImService rongyunImService;
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	@Autowired
	private UploadfileService uploadfileService;
	
	@Autowired
	private LGroupLevelService lGroupLevelService;
	
	@Autowired
	private RelaBindbuyerAndGroupService relaBindbuyerAndGroupService;
	
	/**
	 * 获取彩票群详情
	* @Title: getDetailLotteryGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年5月10日 下午5:24:17 
	* @return LotteryGroupDTO    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getDetailLotteryGroup", method = RequestMethod.GET)
		public @ResponseBody Map<String,Object> getDetailLotteryGroup(@RequestParam(value="id",required=false) String id,
				@RequestParam(value="userToken",required=false) String userToken,
				ModelMap model,HttpSession httpSession) throws Exception
		{
			Map<String,Object> map = new HashMap<String, Object>();
			LotteryGroup entity = lotteryGroupService.getLotteryGroupById(id);
			
			LotteryGroupDTO dto  = lotteryGroupService.toDTO(entity);
			
			map.put(Constants.MESSAGE_STR, "获取成功");
			map.put(Constants.FLAG_STR, true);
			map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
			map.put("dto", dto);
			return map;
		}
	
	@RequestMapping(value = "/getDetailLotteryGroupFromBackStage", method = RequestMethod.GET)
	public @ResponseBody LotteryGroupDTO getDetailLotteryGroupFromBackStage(@RequestParam(value="id",required=false) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		LotteryGroup entity = lotteryGroupService.getLotteryGroupById(id);
		
		LotteryGroupDTO dto  = lotteryGroupService.toDTO(entity);
		
		return dto;
	}
	
	/**
	 * 获取群列表
	* @Title: getGroupList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月10日 下午1:39:05 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getGroupList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getGroupList(
			LotteryGroupDTO dto,
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		//放置分页参数
		Pageable pageable = new PageRequest(page-1,rows);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		
		if(null != dto.getProvince() && !"".equals(dto.getProvince())&& !Constants.PROVINCE_ALL.equals(dto.getProvince()))
		{
			params.add(dto.getProvince());
			buffer.append(" and province = ?").append(params.size());
		}
		
		if(null != dto.getCity() && !"".equals(dto.getCity())&& !Constants.CITY_ALL.equals(dto.getCity()))
		{
			params.add(dto.getCity());
			buffer.append(" and city = ?").append(params.size());
		}
		
		if(null != dto.getLotteryType() && !"".equals(dto.getLotteryType()))
		{
			params.add(dto.getLotteryType());
			buffer.append(" and lotteryType = ?").append(params.size());
		}
		
		if(null != dto.getId() && !"".equals(dto.getId()))
		{
			params.add(dto.getId());
			buffer.append(" and id = ?").append(params.size());
		}
		
		if(null != dto.getName() && !"".equals(dto.getName()))
		{
			params.add(dto.getName());
			buffer.append(" and name = ?").append(params.size());
		}
		
		
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("createTime", "desc");
		
		QueryResult<LotteryGroup> lQueryResult = lotteryGroupService
				.getLotteryGroupList(LotteryGroup.class,
				buffer.toString(), params.toArray(),orderBy, pageable);
				
		List<LotteryGroup> list = lQueryResult.getResultList();
		
		List<LotteryGroupDTO> dtos = lotteryGroupService.toDTOs(list);
		
		map.put("rows",dtos);
		map.put("total", lQueryResult.getTotalRecord());
		
		return map;
	}
	
	/**
	 * 保存或修改群信息
	* @Title: saveOrUpdateGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月10日 下午2:04:27 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/saveOrUpdateGroup")
	public @ResponseBody ResultBean saveOrUpdateGroup(
			LotteryGroupDTO dto,
			HttpServletRequest request,HttpSession httpSession)
	{
		ResultBean bean = lotteryGroupService.saveOrUpdateGroup(dto, request);
		
		return bean;
	}
	
	/**
	 * 获取没加入当前群的用户列表
	* @Title: getMembersOfNotJoinGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param page
	* @param @param rows
	* @param @param groupId
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月17日 下午12:04:48 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getMembersOfNotJoinGroup", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getMembersOfNotJoinGroup(
			@RequestParam(value="page",required=false)   Integer page,//当前页数
			@RequestParam(value="rows",required=false)    Integer rows,//当前获取数据量
			@RequestParam(value="isRobot",required=false)    String isRobot,
			String groupId,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		Pageable pageable = null;
		if(null != rows && 0 != rows)
		{
			pageable = new PageRequest(page-1,rows);
		}
		else
		{
			pageable = new PageRequest(0,Integer.MAX_VALUE);
		}
		LotteryGroup lotteryGroup = lotteryGroupService.getLotteryGroupById(groupId);
		//连接已加入群的用户
		List<RelaBindOfLbuyerorexpertAndGroup> andGroups = lotteryGroup.getRelaBindOfLbuyerorexpertAndGroups();
		StringBuffer joinUser = new StringBuffer();
		int size = andGroups.size();
		for (int i=0;i<size;i++) 
		{
			if(i == size-1)
			{
				joinUser.append("'"+andGroups.get(i).getLotterybuyerOrExpert().getId()+"'");
			}
			else
			{
				joinUser.append("'"+andGroups.get(i).getLotterybuyerOrExpert().getId()+"'").append(",");
			}
		
		}
		
		//不带分页的群成员查询
		QueryResult<LotterybuyerOrExpert> lQueryResult = relaBindbuyerAndGroupService.getUsersOfNotJoinGroup(pageable, joinUser.toString(),isRobot);
		List<LotterybuyerOrExpert> relalist = lQueryResult.getResultList();
		
		List<LotterybuyerOrExpertDTO> userDtos = lotterybuyerOrExpertService.toDTOs(relalist);
			
		 map.put("flag", true);
		 map.put("message", "获取成功");
		 map.put("memberDtos", userDtos);
		 map.put("rows",userDtos);
		 map.put("total", lQueryResult.getTotalCount());
		
		
		return map;
	}
	
	@RequestMapping(value="/getMembersOfGroupFromBS", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getMembersOfGroupFromBS(
			@RequestParam(value="page",required=false)   Integer page,//当前页数
			@RequestParam(value="rows",required=false)    Integer rows,//当前获取数据量
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			String groupId,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		//获取当前群和用户的关联关系(TODO:当前方法获取的群成员不包括群主和群内机器人)
		Pageable pageable = null;
		if(null != rows && 0 != rows)
		{
			pageable = new PageRequest(page-1,rows);
		}
		else
		{
			pageable = new PageRequest(0,Integer.MAX_VALUE);
		}
		
		//不带分页的群成员查询
		QueryResult<RelaBindOfLbuyerorexpertAndGroup> lQueryResult = relaBindbuyerAndGroupService.getMemberOfJoinGroup(pageable, groupId);
		List<RelaBindOfLbuyerorexpertAndGroup> relalist = lQueryResult.getResultList();
		
		List<LotterybuyerOrExpertDTO> userDtos = new ArrayList<LotterybuyerOrExpertDTO>();
		try
		{
			for (RelaBindOfLbuyerorexpertAndGroup rela : relalist)
			{
				LotterybuyerOrExpertDTO dto = new  LotterybuyerOrExpertDTO();
				dto = lotterybuyerOrExpertService.toDTO(rela.getLotterybuyerOrExpert());
				dto.setIsGroupOwner(rela.getIsGroupOwner());
				userDtos.add(dto);
					
			}
			 LotteryGroup group = lotteryGroupService.getLotteryGroupById(groupId);
			 map.put(Constants.FLAG_STR, true);
			 map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
			 map.put(Constants.MESSAGE_STR, "获取成功");
			 map.put("groupNumber", group.getGroupNumber());//放置群号返回参数
			 map.put("memberDtos", userDtos);
			 map.put("rows",userDtos);
			 map.put("total", lQueryResult.getTotalCount());
		}
		catch(Exception e)
		{
			LOG.error(Constants.ERROR_STR, e);
			 map.put(Constants.FLAG_STR, false);
			 map.put(Constants.CODE_STR, Constants.SERVER_FAIL_CODE);
			 map.put(Constants.MESSAGE_STR, "获取失败");
		}
		return map;
	}
	
	/**
	 * 后台加入群
	* @Title: joinUserInGroupFromBS 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userToken
	* @param @param joinUsers
	* @param @param groupId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月23日 下午2:54:25 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/joinUserInGroupFromBS", method = RequestMethod.GET)
	public @ResponseBody ResultBean joinUserInGroupFromBS(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="joinUsers",required=false)  String[] joinUsers,
			@RequestParam(value="groupId",required=false) String groupId)
	{
		ResultBean resultBean = lotteryGroupService.joinUserInGroup(joinUsers, groupId);
		
		return resultBean;
	}
	
	/**
	 * 后台退群
	* @Title: quitUserFronGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userToken
	* @param @param quitUsers
	* @param @param groupId
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月29日 上午10:43:11 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/quitUserFronGroupFromBS", method = RequestMethod.GET)
	public @ResponseBody ResultBean quitUserFronGroup(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="quitUsers",required=false)   String[] quitUsers,
			@RequestParam(value="groupId",required=false)   String groupId,
			HttpServletRequest request,HttpSession httpSession)
	{
		ResultBean resultBean = lotteryGroupService.quitUserFronGroup(quitUsers, groupId);
		
		return resultBean;
	}
	
	
	@RequestMapping(value="/deleteGroupFromBs", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> deleteGroupFromBs(
			LotteryGroupDTO dto,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = lotteryGroupService.deleteGroup(dto, request, httpSession);
		return map;
	}
}
