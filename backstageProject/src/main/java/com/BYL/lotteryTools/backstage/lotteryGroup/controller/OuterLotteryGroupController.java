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
import com.BYL.lotteryTools.backstage.lotteryGroup.dto.LotteryGroupNoticeDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.dto.RelaApplyOfLbuyerorexpertAndGroupDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LGroupLevel;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroupNotice;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaApplyOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaBindOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaGroupUpLevelRecord;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.SysMessage;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LGroupLevelService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupNoticeService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaApplybuyerAndGroupService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaBindbuyerAndGroupService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaGroupUpLevelService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.SysMessageService;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotteryChatCardDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotteryChatCard;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.RelaLBEUserAndLtcard;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.backstage.outer.controller.PushController;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.CodeSuccessResult;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.backstage.user.entity.City;
import com.BYL.lotteryTools.backstage.user.entity.Province;
import com.BYL.lotteryTools.backstage.user.service.CityService;
import com.BYL.lotteryTools.backstage.user.service.ProvinceService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.exception.GlobalOuterExceptionHandler;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.QRCodeUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

/**
 *彩聊群外部接口类
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年5月11日 下午3:24:54
 */
@Controller
@RequestMapping("/outerLGroup")
public class OuterLotteryGroupController extends GlobalOuterExceptionHandler
{
	private static final Logger LOG = LoggerFactory.getLogger(OuterLotteryGroupController.class);
	
	
	@Autowired
	private RongyunImService rongyunImService;
	
	@Autowired
	private LotteryGroupService lotteryGroupService;
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	@Autowired
	private UploadfileService uploadfileService;
	
	@Autowired
	private RelaBindbuyerAndGroupService relaBindbuyerAndGroupService;
	
	@Autowired
	private LGroupLevelService lGroupLevelService;
	
	@Autowired
	private RelaGroupUpLevelService relaGroupUpLevelService;
	
	@Autowired
	private RelaApplybuyerAndGroupService relaApplybuyerAndGroupService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private LotteryGroupNoticeService lotteryGroupNoticeService;
	
	@Autowired
	private SysMessageService sysMessageService;
	
	public static final String SUCCESS_CODE = "200";//成功返回码
	
	/**
	 * 删除群
	* @Title: deleteGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 下午1:51:25 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/deleteGroup", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> deleteGroup(
			LotteryGroupDTO dto,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		//删除群的同时删除群绑定的机器人
		LotteryGroup entity = lotteryGroupService.getLotteryGroupById(dto.getId());
		
		if(null != entity)
		{
			String groupId = entity.getId();
			entity.setGroupRobotID(null);
			//删除融云的群信息
			CodeSuccessResult result = rongyunImService.groupDismiss(entity.getLotteryBuyerOrExpert().getId(), groupId);
			if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
			{
				LOG.error("融云删除群报错", result.getErrorMessage());
			}
			
			//删除数据库中的群信息
			entity.setIsDeleted(Constants.IS_DELETED);
			entity.setModify(dto.getOwnerId());
			entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
			entity.setLotteryBuyerOrExpert(null);
			
			//删除群的同时删除群的关联关系(用户和群的关联关系)
			Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
			QueryResult<RelaBindOfLbuyerorexpertAndGroup> relas = relaBindbuyerAndGroupService.getMemberOfJoinGroup(pageable, groupId);
			List<RelaBindOfLbuyerorexpertAndGroup> list = relas.getResultList();
			if(null != list)
			{
				for (RelaBindOfLbuyerorexpertAndGroup relaBindOfLbuyerorexpertAndGroup : list) 
				{
					try
					{
						relaBindbuyerAndGroupService.delete(relaBindOfLbuyerorexpertAndGroup);
					}
					catch(Exception e)
					{
						LOG.error("delete,error:", e);
					}
				}
			}
			
			//删除加群申请的关联关系
			List<RelaApplyOfLbuyerorexpertAndGroup> applys = relaApplybuyerAndGroupService.
					getRelaApplyOfLbuyerorexpertAndGroupByGroupId(groupId);
			if(null != applys)
			{
				for (RelaApplyOfLbuyerorexpertAndGroup delApply : applys) 
				{
					relaApplybuyerAndGroupService.delete(delApply);
				}
			}
			
			//删除群等级关联关系
			List<RelaGroupUpLevelRecord> records = relaGroupUpLevelService.getRelaGroupUpLevelRecordByGroupId(groupId);
			if(null != records)
			{
				for (RelaGroupUpLevelRecord relaGroupUpLevelRecord : records) {
					relaGroupUpLevelService.delete(relaGroupUpLevelRecord);
				}
			}
			
			//删除群头像和群二维码
			List<Uploadfile> touxiang = uploadfileService.getUploadfilesByNewsUuid(entity.getTouXiang());
			if(null != touxiang &&touxiang.size()!=0)
				uploadfileService.deleteUploadFile(touxiang, httpSession);//调用删除附件数据和附件文件方法
			
			//删除二维码图片
			String savePath = httpSession.getServletContext().getRealPath(entity.getGroupQRImg());//获取二维码绝对路径
			File dirFile = new File(savePath);
			boolean deleteFlag = dirFile.delete();
			if(deleteFlag)
			{
				LOG.info("删除成功",deleteFlag);
			}
			else
			{
				LOG.error(Constants.ERROR_STR,"删除失败，文件："+entity.getGroupQRImg());//若删除失败记录未删除成功的文件,之后做手动删除
			}
			
			lotteryGroupService.update(entity);
			map.put(Constants.MESSAGE_STR, "删除成功");
			map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
			map.put(Constants.FLAG_STR, true);
		}
		else
		{
			map.put(Constants.MESSAGE_STR, "删除失败");
			map.put(Constants.CODE_STR, Constants.FAIL_CODE_OF_DELETE_GROUP);
			map.put(Constants.FLAG_STR, false);
		}
		return map;
	}
	
	/**
	 * 获取当前群的群成员
	* @Title: getMembersOfGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param groupId
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 下午4:44:23 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getMembersOfGroup", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getMembersOfGroup(
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
	 * 根据群类型获取群列表
	* @Title: getGroupsOfLotteryType 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param page
	* @param @param rows
	* @param @param groupId
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月7日 下午2:46:16 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getGroupsOfLotteryType", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getGroupsOfLotteryType(
			@RequestParam(value="lotteryType",required=true)   String lotteryType,
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<LotteryGroup> list = lotteryGroupService.getLotteryGroupByLotteryType(lotteryType);
		
		map.put("groupList", lotteryGroupService.toDTOs(list));
		map.put(Constants.MESSAGE_STR, "获取成功");
		map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
		map.put(Constants.FLAG_STR, true);
		
		return map;
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
	* @date 2017年4月24日 下午5:06:49 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getGroupList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getGroupList(
			LotteryGroupDTO dto,
			@RequestParam(value="userId",required=false)   String userId,//当前发出获取群列表的的用户id
//			@RequestParam(value="page",required=false)   Integer page,//当前页数
//			@RequestParam(value="row",required=false)    Integer row,//当前获取数据量
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		//放置分页参数
		Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		boolean flag = false;
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		//搜索群时，只能搜索出体彩和福彩群
		/*List<String> paraArr = new ArrayList<String> ();
		paraArr.add("1");//体彩
		paraArr.add("2");//福彩
		paraArr.add("3");//竞彩
		params.add(paraArr);
		buffer.append(" and lotteryType in ?").append(params.size());*/
		params.add(Constants.CENTER_CITY_GROUP_ID);//中心群
		buffer.append(" and lotteryType != ?").append(params.size());
		
		//传汉字
		List<Province> prolist = provinceService.getProvinceByPname("%"+dto.getProvince()+"%");
		if(null != prolist && prolist.size()>0)
		{
			flag = true;
			params.add(prolist.get(0).getPcode());
			buffer.append(" and province = ?").append(params.size());
		}
		else
		{
			//传汉字
			List<City> cityList = cityService.getCityByCname("%"+dto.getCity()+"%");
			if(null != cityList && cityList.size()>0)
			{
				flag = true;
				params.add(cityList.get(0).getCcode());
				buffer.append(" and city = ?").append(params.size());
			}
			else
			{
				//传汉字
				String lotteryType = "";
				if(null != dto.getLotteryType() &&!"".equals(dto.getLotteryType()))
				{
					lotteryType = "体彩".equals(dto.getLotteryType())?"1":"2";
				}
				if(!"".equals(lotteryType))
				{
					flag = true;
					params.add(lotteryType);
					buffer.append(" and lotteryType = ?").append(params.size());
				}
				else
				{
					//按群号精确查找
					if(null != dto.getGroupNumber() && !"".equals(dto.getGroupNumber()))
					{
						flag = true;
						params.add(dto.getGroupNumber());
						buffer.append(" and groupNumber = ?").append(params.size());
					}
					else
					{
						//按群名称精确查找
						if(null != dto.getName() && !"".equals(dto.getName()))
						{
							flag = true;
							params.add(dto.getName());
							buffer.append(" and name = ?").append(params.size());
						}
					}
				}
			}
		}		
		
		//排序
		List<LotteryGroupDTO> dtos =  null;
		if(flag)
		{
			LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
			orderBy.put("createTime", "desc");
			
			QueryResult<LotteryGroup> lQueryResult = lotteryGroupService
					.getLotteryGroupList(LotteryGroup.class,
					buffer.toString(), params.toArray(),orderBy, pageable);
					
			List<LotteryGroup> list = lQueryResult.getResultList();
			
			dtos = lotteryGroupService.toDTOs(list);
			
			if(null != userId && !"".equals(userId))
			{
				for (LotteryGroupDTO group : dtos) 
				{
					//判断当前用户是否已加入此群
					RelaBindOfLbuyerorexpertAndGroup rela = relaBindbuyerAndGroupService.
							getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(userId, group.getId());
					
					if(null != rela)
					{
						group.setIsJoinOfUser("1");//当前用户已加入当前群
						group.setIsOwner(rela.getIsGroupOwner());
					}
					else
					{
						group.setIsJoinOfUser("0");
						//判断当前用户是否已申请加群(status is null 是还没进行审核的加群申请)
						List<RelaApplyOfLbuyerorexpertAndGroup> applys = relaApplybuyerAndGroupService.
								getRelaApplyOfLbuyerorexpertAndGroupByCreatorAndStatus(userId,group.getId());
						if(null != applys && applys.size()>0)
						{
							group.setAlreadyApplyOfUser("1");
						}
						else
						{
							group.setAlreadyApplyOfUser("0");
						}
					}
				}
			}
		}
		
		map.put(Constants.FLAG_STR, true);
		map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
		map.put(Constants.MESSAGE_STR, "获取成功");
		map.put("groupDtos", dtos);
		return map;
	}
	
	/**
	 * 获取当前群主管理的群
	* @Title: getGroupsOfOwner 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 下午3:52:17 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getGroupsOfOwner", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getGroupsOfOwner(
			String userId,
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			HttpServletRequest request,HttpSession httpSession)
	{
		 Map<String,Object> map = new HashMap<String, Object>();
		 List<LotteryGroupDTO> groupDtos = new ArrayList<LotteryGroupDTO>();
		 LotterybuyerOrExpert manager = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(userId);
		 List<LotteryGroup> groups = manager.getLotteryGroups();
		 
		 try
		 {
			 for (LotteryGroup lotteryGroup : groups) 
			 {
				 LotteryGroupDTO dto = new LotteryGroupDTO();
//							 BeanUtil.copyBeanProperties(dto, lotteryGroup);
				 dto = lotteryGroupService.toDTO(lotteryGroup);
				 groupDtos.add(dto);
			 }
			 
			 map.put(Constants.FLAG_STR, true);
			 map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
			 map.put(Constants.MESSAGE_STR, "获取成功");
			 map.put("groupDtos", groupDtos);
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
	 * 获取当前用户加入的群
	* @Title: getGroupsOfUserjoins 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param UserId
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 下午3:00:47 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getGroupsOfUserjoins", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getGroupsOfUserjoins(
			String userId,
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			HttpServletRequest request,HttpSession httpSession)
	{
		 Map<String,Object> map = new HashMap<String, Object>();
		
		 List<LotteryGroupDTO> groupDtos = new ArrayList<LotteryGroupDTO>();
		 LotterybuyerOrExpert user = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(userId);
		 try
		 {
			 if(null != user)
			 {
				 List<RelaBindOfLbuyerorexpertAndGroup> relaGroups = relaBindbuyerAndGroupService.getRelaList(userId);
				 
				 for (RelaBindOfLbuyerorexpertAndGroup relaGroup : relaGroups)
				 {
					if(null != relaGroup.getLotteryGroup())
					{
						LotteryGroupDTO dto = new LotteryGroupDTO();
						dto = lotteryGroupService.toDTO(relaGroup.getLotteryGroup());
						dto.setIsOwner(relaGroup.getIsGroupOwner());
						dto.setIsTop(relaGroup.getIsTop());
						groupDtos.add(dto);
					}
				 }
				 
				 map.put(Constants.FLAG_STR, true);
				 map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
				 map.put(Constants.MESSAGE_STR, "获取成功");
				 map.put("groupDtos", groupDtos);
			 }
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
	 * 申请加入群
	* @Title: applyJoinGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @param groupId
	* @param @param applyMessage
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月9日 上午9:57:57 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/applyJoinGroup", method = RequestMethod.GET)
	public @ResponseBody ResultBean applyJoinGroup(
			@RequestParam(value="userId",required=false) String userId,
			@RequestParam(value="groupId",required=false) String groupId,
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="applyMessage",required=false) String applyMessage)
	{
		ResultBean resultBean = new ResultBean();
		
		//建立用户和群的申请关系
		RelaApplyOfLbuyerorexpertAndGroup entity = new RelaApplyOfLbuyerorexpertAndGroup();
		LotterybuyerOrExpert user = null;
		if(null != userId)
		 user = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(userId);
		
		LotteryGroup lotteryGroup = null;
		if(null != groupId)
			lotteryGroup = lotteryGroupService.getLotteryGroupById(groupId);
		
		entity.setId(UUID.randomUUID().toString());//生成主键id
		entity.setIsDeleted(Constants.IS_NOT_DELETED);
		entity.setLotterybuyerOrExpert(user);
		entity.setLotteryGroup(lotteryGroup);
		
		if(null != applyMessage &&!"".equals(applyMessage))//若申请信息不为空，则将申请信息放入
			entity.setApplyMessage(applyMessage);
		
		entity.setCreator(userId);
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		entity.setModify(userId);
		entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
		if(null != lotteryGroup && null != lotteryGroup.getLotteryBuyerOrExpert())
			entity.setApprovalUser(lotteryGroup.getLotteryBuyerOrExpert().getId());//添加群主id
		
		//保存用户的申请
		if(null != userId && null != groupId)
			relaApplybuyerAndGroupService.save(entity);
		
		if(null != lotteryGroup)
		{
			LotterybuyerOrExpert groupOwner = lotteryGroup.getLotteryBuyerOrExpert();
			String[] tagsand = {groupOwner.getTelephone()};//推送给群主id，推送给群主审核
			PushController.sendPushWithCallback(tagsand, null, "1", "group");//推送给群主展示的是“1”
		}
		
		resultBean.setFlag(true);
		resultBean.setResultCode(Constants.SUCCESS_CODE);
		resultBean.setMessage("申请成功");
		
	
		return resultBean;
	}
	
	/**
	 * 群主审批加群申请
	* @Title: gOwnerApprovalApplys 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @param groupId
	* @param @param isPass
	* @param @param notPassMessage
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月9日 上午11:09:39 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/gOwnerApprovalApplys", method = RequestMethod.GET)
	public @ResponseBody ResultBean gOwnerApprovalApplys(
			@RequestParam(value="userId",required=false) String userId,
			@RequestParam(value="groupId",required=false) String groupId,
			@RequestParam(value="groupOwnerId",required=false) String groupOwnerId,//群主id
			@RequestParam(value="isPass",required=false) String isPass,//1：通过0：不通过
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="notPassMessage",required=false) String notPassMessage)
	{
		ResultBean resultBean = new ResultBean();
		
		//##如果审批通过，执行将用户加入群的操作##
		//根据用户id和群id获取用户申请加入群的信息
		List<RelaApplyOfLbuyerorexpertAndGroup> entities = relaApplybuyerAndGroupService.
				getRelaApplyOfLbuyerorexpertAndGroupByUserIdAndGroupId(userId, groupId);
		
		RelaApplyOfLbuyerorexpertAndGroup entity = null;
		if(null != entities  && entities.size()>0)
		{
			entity = entities.get(0);
			entity.setStatus(isPass);//放置审核状态
			
			if("1".equals(isPass))
			{//审核通过，执行加群操作
				String[] joinUsers = {userId};//组合加群用户数组
				this.joinUserInGroup(userToken,joinUsers, groupId);
			}
			else
				if("0".equals(isPass))
				{//审核不通过，执行不通过信息的添加
					if(null != notPassMessage && !"".equals(notPassMessage))
					  entity.setNotPassMessage(notPassMessage);
				}
			entity.setModify(groupOwnerId);
			entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
			
			relaApplybuyerAndGroupService.update(entity);
			
			//TODO:将群主审核的结果推送给申请加群的用户,群主审核的tag是apply
			if(null != entity.getLotterybuyerOrExpert())
			{
				String[] tagsand = {entity.getLotterybuyerOrExpert().getTelephone()};//推送给申请加群的用户手机号
				PushController.sendPushWithCallback(tagsand, null, "0", "group");//推送给用户展示的是“0”
			}
		}
		resultBean.setFlag(true);
		resultBean.setResultCode(Constants.SUCCESS_CODE);
		resultBean.setMessage("审核成功");
		
		return resultBean;
	}
	
	/**
	 * 获取当前用户的申请加群的列表
	* @Title: getApplyGroupList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月9日 上午11:42:52 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getApplyGroupList", method = RequestMethod.GET)
	public @ResponseBody Map<String , Object> getApplyGroupList(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="userId",required=false) String userId)
	{
		Map<String , Object> map = new HashMap<String, Object>();
		
		try
		{
			List<RelaApplyOfLbuyerorexpertAndGroup> entities = relaApplybuyerAndGroupService.
					getRelaApplyOfLbuyerorexpertAndGroupByCreator(userId);
			List<RelaApplyOfLbuyerorexpertAndGroupDTO> dtos = relaApplybuyerAndGroupService.toDTOS(entities);
			map.put("applyList", dtos);
			map.put(Constants.FLAG_STR, true);
			map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
			map.put(Constants.MESSAGE_STR, "获取成功");
		}
		catch(Exception e)
		{
			LOG.error(Constants.ERROR_STR, e);
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.CODE_STR, Constants.SERVER_FAIL_CODE);
			map.put(Constants.MESSAGE_STR, "服务器错误");
		}
		
		return map;
	}
	
	/**
	 * 获取当前用户（群主）需要审核的申请加群列表
	* @Title: getApprovalList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月9日 上午11:47:45 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getApprovalList", method = RequestMethod.GET)
	public @ResponseBody Map<String , Object> getApprovalList(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="ownerId",required=false) String ownerId)
	{
		Map<String , Object> map = new HashMap<String, Object>();
		
		try
		{
			List<RelaApplyOfLbuyerorexpertAndGroup> entities = relaApplybuyerAndGroupService.
					getRelaApplyOfLbuyerorexpertAndGroupByApprovalUser(ownerId);
			
			List<RelaApplyOfLbuyerorexpertAndGroupDTO> dtos = relaApplybuyerAndGroupService.toDTOS(entities);
			
			map.put("approvalList", dtos);
			map.put(Constants.FLAG_STR, true);
			map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
			map.put(Constants.MESSAGE_STR, "获取成功");
		}
		catch(Exception e)
		{
			LOG.error("error", e);
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.CODE_STR, Constants.SERVER_FAIL_CODE);
			map.put(Constants.MESSAGE_STR, "服务器错误");
		}
		
		return map;
	}
	
	/**
	 * 根据群号获取群信息
	* @Title: getGroupByGroupnumber 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param groupNumber
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月9日 下午2:51:00 
	* @return LotteryGroupDTO    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getGroupByGroupnumber", method = RequestMethod.GET)
	public @ResponseBody LotteryGroupDTO getGroupByGroupnumber(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="groupNumber",required=false)  String groupNumber,
			@RequestParam(value="userId",required=false)  String userId)
	{
		LotteryGroup group = lotteryGroupService.getLotteryGroupByGroupNumber(groupNumber);
		LotteryGroupDTO dto = lotteryGroupService.toDTO(group);
		if(null != userId && null != group && !"".equals(userId))
		{
			//判断当前用户是否已加入或已申请群
			RelaBindOfLbuyerorexpertAndGroup relaJoin = relaBindbuyerAndGroupService.
					getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(userId, group.getId());
			if(null != relaJoin)
			{
				dto.setIsJoinOfUser("1");
			}
			else
			{
				dto.setIsJoinOfUser("0");
				List<RelaApplyOfLbuyerorexpertAndGroup> relaApplys = relaApplybuyerAndGroupService.
						getRelaApplyOfLbuyerorexpertAndGroupByUserIdAndGroupId(userId, group.getId());
				if(null != relaApplys && relaApplys.size()>0)
				{
					dto.setAlreadyApplyOfUser("1");
				}
				else
				{
					dto.setAlreadyApplyOfUser("0");
				}
			}
		}
		
		return dto;
	}
	
	/**
	 * 向群中加入用户
	* @Title: joinUserInGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param joinUsers
	* @param @param groupId
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 下午2:12:12 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/joinUserInGroup", method = RequestMethod.GET)
	public @ResponseBody ResultBean joinUserInGroup(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="joinUsers",required=false)  String[] joinUsers,
			@RequestParam(value="groupId",required=false) String groupId)
	{
		ResultBean resultBean = lotteryGroupService.joinUserInGroup(joinUsers, groupId);
		
		return resultBean;
	}
	
	/**
	 * 从群中移除用户(机器人用户不可以被删除，前台也要做校验)
	* @Title: quitUserFronGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param quitUsers
	* @param @param groupId
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 下午2:12:02 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/quitUserFronGroup", method = RequestMethod.GET)
	public @ResponseBody ResultBean quitUserFronGroup(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="quitUsers",required=false)   String[] quitUsers,
			@RequestParam(value="groupId",required=false)   String groupId,
			HttpServletRequest request,HttpSession httpSession)
	{
		ResultBean resultBean = new ResultBean();
		//解除群和要加入用户的关联
		for (String userId : quitUsers) 
		{
			//根据用户id和群id获取关联关系
			RelaBindOfLbuyerorexpertAndGroup rela = relaBindbuyerAndGroupService.
					getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(userId, groupId);
			LotterybuyerOrExpert user = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(userId);
			//机器人用户不可以被删除
			if(null != rela && !"1".equals(user.getIsRobot()))
			{
				//删除关联
				rela.setLotterybuyerOrExpert(null);
				rela.setLotteryGroup(null);
				rela.setIsDeleted(Constants.IS_DELETED);
				relaBindbuyerAndGroupService.update(rela);
			}
			
		}
		//删除融云中群和用户的关系
		try
		{
			CodeSuccessResult result = rongyunImService.quitUserFronGroup(quitUsers, groupId);
			if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
			{
				LOG.error("融云执行用户退群时错误：", result.getErrorMessage());
			}
		}
		catch(Exception e)
		{
			LOG.error(Constants.ERROR_STR, e);
			resultBean.setFlag(false);
			resultBean.setResultCode(Constants.SERVER_FAIL_CODE);
			resultBean.setMessage("服务器错误");
		}
		
		resultBean.setFlag(true);
		resultBean.setResultCode(Constants.SUCCESS_CODE);
		resultBean.setMessage("退群成功");
		return resultBean;
	}
	
	/**
	 * 修改群信息
	* @Title: updateGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 下午2:18:17 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/updateGroup", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> updateGroup(
			LotteryGroupDTO dto,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		LotteryGroup group = lotteryGroupService.getLotteryGroupById(dto.getId());
		try
		{
			if(null != dto.getFabuKj() && !"".equals(dto.getFabuKj()))
			{
				group.setFabuKj(dto.getFabuKj());
				if(null != dto.getFabuZs() && !"".equals(dto.getFabuZs()))
				{
					group.setFabuZs(dto.getFabuZs());
				}
				if(null != dto.getSsKjChaxun() && !"".equals(dto.getSsKjChaxun()))
				{
					group.setSsKjChaxun(dto.getSsKjChaxun());
				}
				if(null != dto.getSsYlChaxun() && !"".equals(dto.getSsYlChaxun()))
				{
					group.setSsYlChaxun(dto.getSsYlChaxun());
				}
				if(null != dto.getSsZjChaxun() && !"".equals(dto.getSsZjChaxun()))
				{
					group.setSsZjChaxun(dto.getSsZjChaxun());
				}
				lotteryGroupService.update(group);
			}
			
			
			
			//修改群新新信息（修改群名称）
			if(null != dto.getName() && !"".equals(dto.getName()))
			{
				group.setName(dto.getName());
				//更改群名称
				lotteryGroupService.update(group);
				map.put(Constants.FLAG_STR, true);
				map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
				map.put(Constants.MESSAGE_STR, "更新群信息成功");
				map.put("group", lotteryGroupService.toDTO(group));
				
				//刷新群的信息
				CodeSuccessResult result = rongyunImService.refreshGroup
						(dto.getId(), dto.getName());
				if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
				{
					LOG.error("融云刷新群信息时出错：", result.getErrorMessage());
				}
			}
			
			
			//修改群简介
			if(null != dto.getIntroduction() && !"".equals(dto.getIntroduction()))
			{
//						LotteryGroup group = lotteryGroupService.getLotteryGroupById(dto.getId());
				group.setIntroduction(dto.getIntroduction());
				
				//更改群名称
				lotteryGroupService.update(group);
				map.put(Constants.FLAG_STR, true);
				map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
				map.put(Constants.MESSAGE_STR, "更新群信息成功");
				map.put("group", lotteryGroupService.toDTO(group));
			}
			
			//TODO:群升级，群升级时才传递这个参数(升到几级，升2级传值2)
			if(null != dto.getUpLevel() && !"".equals(dto.getUpLevel()))
			{
//						LotteryGroup group = lotteryGroupService.getLotteryGroupById(dto.getId());
				
				//获取上次群升级的记录，获取上次的等级
				LGroupLevel beforeLevel = lGroupLevelService.getLGroupLevelByID(group.getGroupLevel());//获取上一次等级的数据
				
				LGroupLevel afterLevel = lGroupLevelService.getLGroupLevelByID(dto.getUpLevel());//要升到级数的群等级数据
				//放置群升级记录表数据
				RelaGroupUpLevelRecord level = new RelaGroupUpLevelRecord();
				level.setAfterLevel(afterLevel);
				level.setBeforeLevel(beforeLevel);
				level.setCreateTime(new Timestamp(System.currentTimeMillis()));
				level.setCreator(group.getId());
				level.setIsDeleted(Constants.IS_NOT_DELETED);
				level.setLotteryGroup(group);
				level.setModifyTime(new Timestamp(System.currentTimeMillis()));
				level.setModify(group.getId());
				level.setOperator(dto.getOwnerId());
				relaGroupUpLevelService.save(level);//保存群等级记录表数据
				
				group.setGroupLevel(dto.getUpLevel());//放置当前群等级，也就是要升到的群等级数，eg：升到2级就传2
				group.setMemberCount(afterLevel.getMemberCount());//更新membercount
				
				//更改群人数
				lotteryGroupService.update(group);
				map.put(Constants.FLAG_STR, true);
				map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
				map.put(Constants.MESSAGE_STR, "升级群成功");
				map.put("group", lotteryGroupService.toDTO(group));
			}
		}
		catch(Exception e)
		{
			LOG.error(Constants.ERROR_STR, e);
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.CODE_STR, Constants.SERVER_FAIL_CODE);
			map.put(Constants.MESSAGE_STR, "更新群信息失败");
		}
		
		return map;
	}
	
	/**
	 * 创建群
	* @Title: createGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 上午9:57:27 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/createGroup")
	public @ResponseBody Map<String,Object> createGroup(
			LotteryGroupDTO dto,
			HttpServletRequest request)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		LotteryGroup entity = new LotteryGroup();
		//在服务器创建群信息
		try 
		{
			if(null != dto.getOwnerId() && !"".equals(dto.getOwnerId()))
			{
				BeanUtil.copyBeanProperties(entity, dto);
				entity.setId(UUID.randomUUID().toString());//生成id
				
				entity.setGroupNumber(lotteryGroupService.generateGroupNumber());//放置群号
				LotterybuyerOrExpert owner = lotterybuyerOrExpertService.
						getLotterybuyerOrExpertById(dto.getOwnerId());
				entity.setLotteryBuyerOrExpert(owner);//放置群与群主的关系
				//Add by banna in 2017/6/6
				entity.setDetailLotteryType(dto.getLotteryType());//放置详细彩种字段
				
				//处理群头像
				Uploadfile uploadfile =null;
				if(null != dto.getTouXiangImg())
				{
					String newsUuid = UUID.randomUUID().toString();
					try {
							 uploadfile = uploadfileService.uploadFiles(dto.getTouXiangImg(),request,newsUuid);
						
					} catch (Exception e) {
						LOG.error(Constants.ERROR_STR, e);
					}
					if(null != uploadfile)
						entity.setTouXiang(uploadfile.getNewsUuid());
				}
				
				//TODO:创建群的同时创建群的机器人,如果区域彩种机器人已经存在，或者机器人加群数以及饱和，则要再创建机器人
				String robotUserId = lotterybuyerOrExpertService.
						createRobotUser(dto.getProvince(), dto.getCity(), dto.getLotteryType(),request);
				
				entity.setGroupRobotID(robotUserId);
				
				//设置群公告默认不被审核
				entity.setNoticeReview(0);
				
				//TODO:放置群等级
				String level1Id = "1";//等级1群的等级id
				entity.setMemberCount(20);//以及群
				entity.setGroupLevel(level1Id);
				
				entity.setIsDeleted(Constants.IS_NOT_DELETED);
				entity.setCreator(dto.getOwnerId());
				entity.setCreateTime(new Timestamp((System.currentTimeMillis())));
				entity.setModify(dto.getOwnerId());
				entity.setModifyTime(new Timestamp((System.currentTimeMillis())));
				
				//生成群二维码
				String logo = null;//内嵌logo图片，若群头像不为空，则嵌入群头像
				String uploadPath = "upload";
				String path = request.getSession().getServletContext().getRealPath(uploadPath); 
				if(null != uploadfile)
					logo = path+File.separator+uploadfile.getUploadRealName();
				
				String fileName = QRCodeUtil.encode(entity.getGroupNumber(), logo, path, true,entity.getGroupNumber());
				entity.setGroupQRImg(File.separator+uploadPath+File.separator+fileName);
				//保存群信息
				lotteryGroupService.save(entity);
				
				//放置群升级记录表数据
				RelaGroupUpLevelRecord level = new RelaGroupUpLevelRecord();
				LGroupLevel l1 = lGroupLevelService.getLGroupLevelByID(level1Id);//获取L1等级的实体数据
				level.setAfterLevel(l1);//一级群
				level.setBeforeLevel(null);
				level.setCreateTime(new Timestamp(System.currentTimeMillis()));
				level.setCreator(entity.getId());
				level.setIsDeleted(Constants.IS_NOT_DELETED);
				level.setLotteryGroup(entity);
				level.setModifyTime(new Timestamp(System.currentTimeMillis()));
				level.setModify(entity.getId());
				level.setOperator(dto.getOwnerId());
				relaGroupUpLevelService.save(level);//保存群等级记录表数据
				
				//2017-5-11ADD：建立群主和群的加入关系
				RelaBindOfLbuyerorexpertAndGroup rela = new RelaBindOfLbuyerorexpertAndGroup();
				rela.setIsDeleted(Constants.IS_NOT_DELETED);
				rela.setIsReceive("1");
				rela.setIsTop("0");//是否置顶1：置顶 0：不置顶
				rela.setIsGroupOwner("1");//群主
				rela.setLotterybuyerOrExpert(owner);
				rela.setLotteryGroup(entity);
				rela.setCreator(dto.getOwnerId());
				rela.setCreateTime(new Timestamp(System.currentTimeMillis()));
				rela.setModify(dto.getOwnerId());
				rela.setModifyTime(new Timestamp(System.currentTimeMillis()));
				//保存关联
				relaBindbuyerAndGroupService.save(rela);
				
				//创建机器人和群的关联关系
				LotterybuyerOrExpert robot = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(robotUserId);
				RelaBindOfLbuyerorexpertAndGroup relaRobot = new RelaBindOfLbuyerorexpertAndGroup();
				relaRobot.setIsDeleted(Constants.IS_NOT_DELETED);
				relaRobot.setIsReceive("1");
				relaRobot.setIsTop("0");//是否置顶1：置顶 0：不置顶
				relaRobot.setIsGroupOwner("0");//群主
				relaRobot.setLotterybuyerOrExpert(robot);
				relaRobot.setLotteryGroup(entity);
				relaRobot.setCreator(robotUserId);
				relaRobot.setCreateTime(new Timestamp(System.currentTimeMillis()));
				relaRobot.setModify(robotUserId);
				relaRobot.setModifyTime(new Timestamp(System.currentTimeMillis()));
				//保存关联
				relaBindbuyerAndGroupService.save(relaRobot);
				
				//在融云创建群信息
				String[] joinUserId = {dto.getOwnerId(),robotUserId};//群主id加入要加入群的数组中,机器人加入群组中
				CodeSuccessResult result = rongyunImService.createGroup(joinUserId, entity.getId(), entity.getName());
				
				if(!SUCCESS_CODE.equals(result.getCode().toString()))
				{//若创建失败
					map.put(Constants.MESSAGE_STR, result.getErrorMessage());//创建失败返回融云端群创建失败信息
					LOG.error("createGroup error:", result.getErrorMessage());
					map.put(Constants.CODE_STR, Constants.FAIL_CODE_OF_CREATE_GROUP);
					map.put(Constants.FLAG_STR, false);
				}
				else
				{
					map.put("group", lotteryGroupService.toDTO(entity));//返回创建成功的群信息
					map.put(Constants.MESSAGE_STR, "创建成功");
					map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
					map.put(Constants.FLAG_STR, true);
					
					/*建群成功后，使用机器人向群内发送消息*/
					String[] group = {entity.getId()};
					CodeSuccessResult sendResult =  rongyunImService.sendMessgeToGroups
							(robotUserId, group, "建群成功！");
					if(!SUCCESS_CODE.equals(sendResult.getCode().toString()))
					{
						LOG.error("send create group msg error:", result.getErrorMessage());
					}
					
					//TODO:1.创建成功后，将当前群主的建群卡个数减1
					RelaLBEUserAndLtcard card = lotterybuyerOrExpertService.
							getRelaLBEUserAndLtcardByUserIdAndCardId(dto.getOwnerId(), dto.getLotteryType());
					if(null != card)
					{
						card.setNotUseCount(0 != card.getNotUseCount()?card.getNotUseCount()-1:0);
						card.setUseCount(card.getUseCount()+1);
						card.setModify(dto.getOwnerId());
						card.setModifyTime(new Timestamp(System.currentTimeMillis()));
						lotterybuyerOrExpertService.updateRelaLBEUserAndLtcard(card);
					}
				}
			}
		} catch (Exception e) 
		{
			LOG.error(Constants.ERROR_STR, e);
			map.put(Constants.MESSAGE_STR, "创建失败");
			map.put(Constants.CODE_STR, Constants.SERVER_FAIL_CODE);
			map.put(Constants.FLAG_STR, false);
		}
		
		return map;
	}
	
	
	/**
	 * 用户操作置顶/取消置顶群操作
	* @Title: updateGroupIsTop 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param isTop
	* @param @param userId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月11日 上午9:53:02 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/updateGroupIsTop", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> updateGroupIsTop(
			@RequestParam(value="isTop",required=false)  String isTop,
			@RequestParam(value="userId",required=false) String userId,
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="groupId",required=false) String groupId)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		//更新置顶状态
		RelaBindOfLbuyerorexpertAndGroup entity = relaBindbuyerAndGroupService.
				getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(userId, groupId);
		entity.setIsTop(isTop);
		entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
		entity.setModify(userId);
		relaBindbuyerAndGroupService.update(entity);
		
		//获取置顶后的群列表
		List<LotteryGroupDTO> dtos = new ArrayList<LotteryGroupDTO>();
		 List<RelaBindOfLbuyerorexpertAndGroup> relaGroups = relaBindbuyerAndGroupService.getRelaList(userId);
		 for (RelaBindOfLbuyerorexpertAndGroup relaGroup : relaGroups)
		 {//将排序关系转换为群信息
			if(null != relaGroup.getLotteryGroup())
			{
				LotteryGroupDTO dto = new LotteryGroupDTO();
				dto = lotteryGroupService.toDTO(relaGroup.getLotteryGroup());
				dto.setIsOwner(relaGroup.getIsGroupOwner());
				dto.setIsTop(relaGroup.getIsTop());
				dtos.add(dto);
			}
		 }
		 
		 map.put(Constants.FLAG_STR, true);
		 map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
		 map.put(Constants.MESSAGE_STR, "更新成功");
		 map.put("groupDtos", dtos);
		
		return map;
	}
	
	/**
	 * 查看建群卡用户卡片余额
	* @Title: checkCardYue 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @param groupId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月25日 下午3:51:40 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	/*@RequestMapping(value="/checkCreateGroupCardYueOfUser", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> checkCreateGroupCardYueOfUser(
			@RequestParam(value="userId",required=false) String userId)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		RelaLBEUserAndLtcard card = lotterybuyerOrExpertService.
				getRelaLBEUserAndLtcardByUserIdAndCardId(userId, LotteryStationController.CREATE_GROUP_CARD_ID);

		if(null != card)
			map.put("couldUse", card.getNotUseCount());
		else
			map.put("couldUse", 0);
				
		return map;
	}
	*/
	/**
	 * 校验当前用户当前卡片的余额
	* @Title: checkCardYueOfUser 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @param cardId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月25日 下午3:54:48 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/checkCardYueOfUser", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> checkCardYueOfUser(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="userId",required=false) String userId,
			@RequestParam(value="cardId",required=false) String cardId)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		RelaLBEUserAndLtcard card = lotterybuyerOrExpertService.
				getRelaLBEUserAndLtcardByUserIdAndCardId(userId, cardId);
		if(null != card)
		{
			map.put("couldUse", card.getNotUseCount());
			
		}
		else
		{
			map.put("couldUse", 0);
		}
		map.put(Constants.MESSAGE_STR, "获取成功");
		map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
		map.put(Constants.FLAG_STR, true);	
		return map;
	}
	
	
	/**
	 * 获取所有的类型彩聊卡
	* @Title: getAllLotteryChatCards 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月26日 上午10:17:19 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getAllLotteryChatCards", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getAllLotteryChatCards(
			@RequestParam(value="userToken",required=false) String userToken//用户token
			)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<LotteryChatCard> cards = lotterybuyerOrExpertService.findAllLotteryChatCards();
		
		List<LotteryChatCardDTO> dtos = lotterybuyerOrExpertService.toLotteryChatCardDTOs(cards);
		
		map.put("cards", dtos);
		map.put(Constants.MESSAGE_STR, "获取成功");
		map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
		map.put(Constants.FLAG_STR, true);	
		
		return map;
	}
	
	/**
	 * 
	* @Title: getAllLotteryChatCardsOfUser 
	* @Description: 获取当前用户的卡包
	* @param @param userId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月26日 下午4:36:00 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getAllLotteryChatCardsOfUser", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getAllLotteryChatCardsOfUser(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="userId",required=false) String userId)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
			
				List<LotteryChatCard> cards = lotterybuyerOrExpertService.findAllLotteryChatCards();
				
				List<LotteryChatCardDTO> dtos = lotterybuyerOrExpertService.toLotteryChatCardDTOs(cards);
				
				for (LotteryChatCardDTO lotteryChatCardDTO : dtos) {
					RelaLBEUserAndLtcard relaCard = lotterybuyerOrExpertService.
							getRelaLBEUserAndLtcardByUserIdAndCardId(userId, lotteryChatCardDTO.getId());
					if(null != relaCard)
					{
						lotteryChatCardDTO.setCount(relaCard.getNotUseCount());
					}
					else
					{
						lotteryChatCardDTO.setCount(0);
					}
				}
				
				map.put("cards", dtos);
				map.put(Constants.MESSAGE_STR, "获取成功");
				map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
				map.put(Constants.FLAG_STR, true);	
			
		
		return map;
	}
	
	/**
	 * 更新用户的卡片数
	* @Title: updateNumberOfCardForUser 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @param cardId
	* @param @param number
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月26日 上午10:19:52 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/updateNumberOfCardForUser", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> updateNumberOfCardForUser(
			@RequestParam(value="userId",required=false) String userId,
			@RequestParam(value="cardId",required=false) String cardId,
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="number",required=false) Integer number)
	{
		Map<String,Object> map = new HashMap<String, Object>();	
		
		LotterybuyerOrExpert owner = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(userId);
		//更新用户的卡片数
		lotterybuyerOrExpertService.updateCardsOfUser(owner, cardId, number);
		
		map.put(Constants.MESSAGE_STR, "更新成功");
		map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
		map.put(Constants.FLAG_STR, true);	
		return map;
	}
	
	/*****************群公告接口*****************/
	
	/**
	 * 提交群公告，并且推送
	* @Title: submitGroupNotice 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月1日 下午4:23:56 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/submitGroupNotice", method = RequestMethod.GET)
	public @ResponseBody Map<String , Object> submitGroupNotice(
			LotteryGroupNoticeDTO dto) throws Exception
	{
		Map<String , Object> result = new HashMap<String, Object>();
		
		LotteryGroupNotice entity = new LotteryGroupNotice();
		
		BeanUtil.copyBeanProperties(entity, dto);
		LotteryGroup group = lotteryGroupService.getLotteryGroupById(dto.getGroupId());
		entity.setId(UUID.randomUUID().toString());
		entity.setLotteryGroup(group);
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		entity.setModify(dto.getCreator());
		entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
		entity.setIsDeleted(Constants.IS_NOT_DELETED);
		boolean tuisongFlag  = false;
		if(0 == group.getNoticeReview())
		{
			entity.setStatus("1");
			tuisongFlag = true;
			/*不需要审核直接通过的群公告，要将之前群的公告数据删除*/
			this.deletePreGroupNotice(group.getId());
		}
		lotteryGroupNoticeService.save(entity);
		
		result.put(Constants.FLAG_STR, true);
		result.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
		result.put(Constants.MESSAGE_STR, "添加成功");
		
		if(tuisongFlag)
		{
			//将群公告推送到群中
			String[] tagsand = {group.getGroupNumber()};//推送给群主id，推送给群主审核
			PushController.sendPushWithCallback(tagsand, null, dto.getNotice(), "groupNotice");//推送给群主展示的是“1”
		}
		return result;
	}
	
	/**
	 * 删除当前群之前的群公告
	* @Title: deletePreGroupNotice 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param groupId    设定文件 
	* @author banna
	* @date 2017年6月5日 上午10:49:59 
	* @return void    返回类型 
	* @throws
	 */
	private void deletePreGroupNotice(String groupId)
	{
		String status = "1";
		List<LotteryGroupNotice> list = lotteryGroupNoticeService.getLotteryGroupNoticeByGroupId(status,groupId);
		
		for (LotteryGroupNotice groupNotice : list) {
			groupNotice.setIsDeleted(Constants.IS_DELETED);
			groupNotice.setModify("system");
			groupNotice.setModifyTime(new Timestamp(System.currentTimeMillis()));
			
			lotteryGroupNoticeService.update(groupNotice);
			LOG.info("删除成功:groupNoticeId:"+groupNotice.getId());
		}
	}
	/**
	 * 获取当前群的群公告
	* @Title: getGroupNoticeOfGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param groupId
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年6月1日 下午4:44:19 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getGroupNoticeOfGroup", method = RequestMethod.GET)
	public @ResponseBody Map<String , Object> getGroupNoticeOfGroup(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="groupId",required=false) String groupId) throws Exception
	{
		Map<String , Object> map = new HashMap<String, Object>();
		
		String status = "1";
		List<LotteryGroupNotice> list = lotteryGroupNoticeService.getLotteryGroupNoticeByGroupId(status,groupId);
		
		List<LotteryGroupNoticeDTO> dtos = lotteryGroupNoticeService.toDTOs(list);
		
		map.put("dtos", dtos);
		map.put(Constants.FLAG_STR, true);
		map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
		map.put(Constants.MESSAGE_STR, "获取成功");
		
		return map;
	}
	
	/**
	 * 更改群公告状态
	* @Title: updateGroupNoticeOfGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param noticeId
	* @param @param userId
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年6月1日 下午5:38:40 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/updateGroupNoticeOfGroup", method = RequestMethod.GET)
	public @ResponseBody Map<String , Object> updateGroupNoticeOfGroup(
			@RequestParam(value="noticeId",required=false) String noticeId,
			@RequestParam(value="userId",required=false) String userId,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="notPassMessage",required=false) String notPassMessage) throws Exception
	{
		Map<String , Object> map = new HashMap<String, Object>();
		/*if(null != userToken && !"".equals(userToken))
		{
			//校验token是否相同
			boolean tokenFlag = TokenUtil.checkToken(userToken);
			if(!tokenFlag)
			{//token不相同
				map.put(Constants.FLAG_STR, false);
				map.put(Constants.MESSAGE_STR, "token过期,请重新登录!");
			}
			else
			{*/
				LotteryGroupNotice groupNotice = lotteryGroupNoticeService.getLotteryGroupNoticeByID(noticeId);
				
				groupNotice.setStatus(status);
				groupNotice.setModify(userId);
				groupNotice.setModifyTime(new Timestamp(System.currentTimeMillis()));
				if("1".equals(status))
				{//若审批通过则进行推送
					//将群公告推送到群中
					String[] tagsand = {groupNotice.getLotteryGroup().getGroupNumber()};//推送给群主id，推送给群主审核
					PushController.sendPushWithCallback(tagsand, null, groupNotice.getNotice(), "groupNotice");//推送给群主展示的是“1”
					/*审核通过，则将之前的公告删除*/
					this.deletePreGroupNotice(groupNotice.getLotteryGroup().getId());
				}
				else
					if("0".equals(status))
					{
						LotteryGroup ownerGroup = groupNotice.getLotteryGroup();
						groupNotice.setNotPassMessage(notPassMessage);
						String[] tagsand = {ownerGroup.getLotteryBuyerOrExpert().getTelephone()};//推送给群主
						String message = "群名称为:"+ownerGroup.getName()+" 申请发布的群公告审核未通过,未通过原因:"+notPassMessage;
						PushController.sendPushWithCallback
							(tagsand, null,message , "sysMessage");//推送给群主展示的是“1”
						
						/*记录系统消息推送到系统消息表*/
						SysMessage sysMessage = new SysMessage();
						sysMessage.setTarget(ownerGroup.getLotteryBuyerOrExpert().getTelephone());
						sysMessage.setMessage(message);
						sysMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
						sysMessage.setCreator("system");
						sysMessage.setModifyTime(new Timestamp(System.currentTimeMillis()));
						sysMessage.setModify("system");
						sysMessage.setIsDeleted(Constants.IS_NOT_DELETED);
						sysMessageService.save(sysMessage);
					}
				lotteryGroupNoticeService.update(groupNotice);
				map.put(Constants.FLAG_STR, true);
				map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
				map.put(Constants.MESSAGE_STR, "更新成功");
				/*}
		}
		else
		{
			map.put(Constants.FLAG_STR, false);
			map.put(Constants.MESSAGE_STR, "token值不存在!");
		}*/
		return map;
	}
	
	/**
	 * 删除群公告
	* @Title: deleteGroupNotice 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param noticeId
	* @param @param userId
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年6月1日 下午5:41:39 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/deleteGroupNotice", method = RequestMethod.GET)
	public @ResponseBody Map<String , Object> deleteGroupNotice(
			@RequestParam(value="noticeId",required=false) String noticeId,
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="userId",required=false) String userId) throws Exception
	{
		Map<String , Object> map = new HashMap<String, Object>();
		
		LotteryGroupNotice groupNotice = lotteryGroupNoticeService.getLotteryGroupNoticeByID(noticeId);
		
		groupNotice.setIsDeleted(Constants.IS_DELETED);
		groupNotice.setModify(userId);
		groupNotice.setModifyTime(new Timestamp(System.currentTimeMillis()));
		
		lotteryGroupNoticeService.update(groupNotice);
		
		map.put(Constants.FLAG_STR, true);
		map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
		map.put(Constants.MESSAGE_STR, "删除成功");
		
		return map;
	}
	
	/**
	 * 获取群公告列表
	* @Title: getGroupNoticeList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param groupId
	* @param @param page
	* @param @param row
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月2日 下午1:37:57 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getGroupNoticeList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getGroupNoticeList(
			@RequestParam(value="status",required=false)   String status,
			@RequestParam(value="page",required=false)   Integer page,//当前页数
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="rows",required=false)    Integer rows,//当前获取数据量
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
		
		if(null != status && !"".equals(status))
		{
			params.add(status);
			buffer.append(" and status = ?").append(params.size());
		}
		
		//排序
		List<LotteryGroupNoticeDTO> dtos =  null;
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("createTime", "desc");
		
		QueryResult<LotteryGroupNotice> lQueryResult = lotteryGroupNoticeService
				.getLotteryGroupNoticeList(LotteryGroupNotice.class,
				buffer.toString(), params.toArray(),orderBy, pageable);
				
		List<LotteryGroupNotice> list = lQueryResult.getResultList();
		
		dtos = lotteryGroupNoticeService.toDTOs(list);

		
		map.put("rows",dtos);
		map.put("total", lQueryResult.getTotalRecord());
		
		return map;
	}
	
	/**
	 * 获取群公告的详情
	* @Title: getDetailLotteryGroupNotice 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年6月2日 下午1:42:32 
	* @return LotteryGroupNoticeDTO    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getDetailLotteryGroupNotice", method = RequestMethod.GET)
	public @ResponseBody LotteryGroupNoticeDTO getDetailLotteryGroupNotice(
			@RequestParam(value="userToken",required=false) String userToken,//用户token
			@RequestParam(value="id",required=false) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		LotteryGroupNotice entity = lotteryGroupNoticeService.getLotteryGroupNoticeByID(id);
		
		LotteryGroupNoticeDTO dto  = lotteryGroupNoticeService.toDTO(entity);
		
		return dto;
	}
	
}
