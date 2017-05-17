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
	private Logger logger = LoggerFactory.getLogger(LotteryGroupController.class);
	
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
		public @ResponseBody LotteryGroupDTO getDetailLotteryGroup(@RequestParam(value="id",required=false) String id,
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
		ResultBean bean = new ResultBean();
		
		LotteryGroup entity = lotteryGroupService.getLotteryGroupById(dto.getId());
		
		if(null != entity)
		{//修改群信息
			try
			{
				String lastName = entity.getName();
				String lastOwnerId = entity.getLotteryBuyerOrExpert().getId();
				
				entity.setName(dto.getName());
				entity.setGroupLevel(dto.getGroupLevel());
				entity.setFabuKj(dto.getFabuKj());
				entity.setFabuZs(dto.getFabuZs());
				entity.setIntroduction(dto.getIntroduction());
				entity.setJoinType(dto.getJoinType());
				entity.setLotteryType(dto.getLotteryType());
				entity.setProvince(dto.getProvince());
				entity.setCity(dto.getCity());
				entity.setSsKjChaxun(dto.getSsKjChaxun());
				entity.setSsYlChaxun(dto.getSsYlChaxun());
				entity.setSsZjChaxun(dto.getSsZjChaxun());
				if(!lastOwnerId.equals(dto.getOwnerId()))
				{
					LotterybuyerOrExpert owner = lotterybuyerOrExpertService.
							getLotterybuyerOrExpertById(dto.getOwnerId());
					entity.setLotteryBuyerOrExpert(owner);//更改群与群主的关系
					
					//2017-5-16ADD：建立群主和群的加入关系
					RelaBindOfLbuyerorexpertAndGroup rela 
						= relaBindbuyerAndGroupService.getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(lastOwnerId, dto.getId());
					if(null != rela)
						relaBindbuyerAndGroupService.delete(rela);
					
					rela = new RelaBindOfLbuyerorexpertAndGroup();
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
				}
				
				
				
				
				if(!lastName.equals(entity.getName()))
				{
					String logo = null;//内嵌logo图片，若群头像不为空，则嵌入群头像
					String uploadPath = "upload";
					String path = request.getSession().getServletContext().getRealPath(uploadPath); 
					Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(entity.getTouXiang());
					if(null != uploadfile)
						logo = path+File.separator+uploadfile.getUploadRealName();
					
					String fileName = QRCodeUtil.encode(entity.getGroupNumber(), logo, path, true,entity.getGroupNumber());
					entity.setGroupQRImg(File.separator+uploadPath+File.separator+fileName);
				}
				lotteryGroupService.update(entity);
				
				
				
				bean.setFlag(true);
				bean.setMessage("修改成功");
			}
			catch(Exception e)
			{
				
			}
			
		
		}
		else
		{
			//在服务器创建群信息
			try 
			{
				entity = new LotteryGroup();
				BeanUtil.copyBeanProperties(entity, dto);
				entity.setId(UUID.randomUUID().toString());//生成id
				
				entity.setGroupNumber(lotteryGroupService.generateGroupNumber());//放置群号
				LotterybuyerOrExpert owner = lotterybuyerOrExpertService.
						getLotterybuyerOrExpertById(dto.getOwnerId());
				entity.setLotteryBuyerOrExpert(owner);//放置群与群主的关系
				
				//处理群头像
				Uploadfile uploadfile =null;
				if(null != dto.getTouXiangImg())
				{
					String newsUuid = UUID.randomUUID().toString();
					try {
							 uploadfile = uploadfileService.uploadFiles(dto.getTouXiangImg(),request,newsUuid);
						
					} catch (Exception e) {
						logger.error("error:", e);
					}
					entity.setTouXiang(uploadfile.getNewsUuid());
				}
				else
				{
					if(null != dto.getTouXiang() && !"".equals(dto.getTouXiang()))
					{
						uploadfile = uploadfileService.getUploadfileByNewsUuid(dto.getTouXiang());
						
					}
				}
				
				//TODO:创建群的同时创建群的机器人,如果区域彩种机器人已经存在，或者机器人加群数以及饱和，则要再创建机器人
				String robotUserId = lotterybuyerOrExpertService.
						createRobotUser(dto.getProvince(), dto.getCity(), dto.getLotteryType());
				
				entity.setGroupRobotID(robotUserId);
				
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
				LGroupLevel L1 = lGroupLevelService.getLGroupLevelByID(level1Id);//获取L1等级的实体数据
				level.setAfterLevel(L1);//一级群
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
				
				//在融云创建群信息
				String[] joinUserId = {dto.getOwnerId(),robotUserId};//群主id加入要加入群的数组中,机器人加入群组中
				CodeSuccessResult result = rongyunImService.createGroup(joinUserId, entity.getId(), entity.getName());
				
				if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
				{//若创建失败
					bean.setMessage( result.getErrorMessage());//创建失败返回融云端群创建失败信息
					logger.error("createGroup error:", result.getErrorMessage());
					bean.setFlag(false);
				}
				else
				{
					bean.setFlag(true);
					bean.setMessage("创建成功");
					
					//创建成功后，将当前群主的建群卡个数减1
				}
				
				
				
				
			} catch (Exception e) 
			{
				logger.error("error:", e);
				bean.setMessage("创建失败");
				bean.setFlag(false);
			}
			
		}
	
		
		
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
		QueryResult<LotterybuyerOrExpert> lQueryResult = relaBindbuyerAndGroupService.getUsersOfNotJoinGroup(pageable, joinUser.toString());
		List<LotterybuyerOrExpert> relalist = lQueryResult.getResultList();
		
		List<LotterybuyerOrExpertDTO> userDtos = lotterybuyerOrExpertService.toDTOs(relalist);
			
		 map.put("flag", true);
		 map.put("message", "获取成功");
		 map.put("memberDtos", userDtos);
		 map.put("rows",userDtos);
		 map.put("total", lQueryResult.getTotalCount());
		
		
		return map;
	}
	
}
