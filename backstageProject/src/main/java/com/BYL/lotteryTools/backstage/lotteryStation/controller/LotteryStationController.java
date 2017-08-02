package com.BYL.lotteryTools.backstage.lotteryStation.controller;

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
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.SysMessage;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.SysMessageService;
import com.BYL.lotteryTools.backstage.lotteryStation.dto.LotteryStationDTO;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.backstage.lotteryStation.service.LotteryStationService;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.backstage.outer.controller.PushController;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;
import com.BYL.lotteryTools.common.util.QueryResult;

/**
 * 
* @Description: TODO(彩票站控制类) 
* @author banna
* @date 2017年3月14日 上午9:53:09
 */
@Controller
@RequestMapping("/lotteryStation")
public class LotteryStationController
{
	private static final Logger LOG = LoggerFactory.getLogger(LotteryStationController.class);
	
	@Autowired
	private LotteryStationService lotteryStationService;
	
	@Autowired
	private UploadfileService uploadfileService;
	
	@Autowired
	private LotteryGroupService lotteryGroupService;
	
	@Autowired
	private SysMessageService sysMessageService;
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	public static final String CREATE_TICAI_GROUP_CARD_ID="1";//体彩建群卡id
	
	public static final String CREATE_FUCAI_GROUP_CARD_ID="2";//福彩建群卡id
	
	/**
	 * 
	* @Title: getDetailStation 
	* @Description: TODO(获取彩票站数据详情) 
	* @param @param id
	* @param @return    设定文件 
	* @author banna
	* @date 2017年3月14日 上午9:25:10 
	* @return LotteryStationDTO    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getDetailStation" , method = RequestMethod.GET)
	public @ResponseBody LotteryStationDTO getDetailStation(
			@RequestParam(value="id",required=true) String id)
	{
		LotteryStationDTO lotteryStationDTO = new LotteryStationDTO();
		
		LotteryStation lotteryStation = lotteryStationService.getLotteryStationById(id);
		
		lotteryStationDTO = lotteryStationService.toDTO(lotteryStation);
		
		return lotteryStationDTO;
	}
	
	/**
	 * 
	* @Title: getLotteryStationList 
	* @Description: TODO(获取彩票站列表) 
	* @param @param page
	* @param @param rows
	* @param @param name
	* @param @param provinceCode
	* @param @param lotteryType
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月14日 上午10:32:37 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getLotteryStationList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getLotteryStationList(
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="provinceCode",required=false) String provinceCode,
			@RequestParam(value="lotteryType",required=false) String lotteryType,//彩种，体彩/福彩
			@RequestParam(value="fromApp",required=false) String fromApp,//来自app的彩票站信息
			@RequestParam(value="approvalStatus",required=false) String approvalStatus,
			@RequestParam(value="status",required=false) String status,
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
		
		if(null != approvalStatus&&!"".equals(approvalStatus.trim()))
		{
			params.add(approvalStatus);
			buffer.append(" and approvalStatus = ?").append(params.size());
		}
		
		if(null != status&&!"".equals(status.trim()))
		{
			params.add(status);
			buffer.append(" and status = ?").append(params.size());
		}
		
		if(null != fromApp&&!"".equals(fromApp.trim()))
		{
			params.add(fromApp);
			buffer.append(" and fromApp = ?").append(params.size());
		}
		
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
		
		QueryResult<LotteryStation> lQueryResult = lotteryStationService
				.getLotteryStationList(LotteryStation.class,
				buffer.toString(), params.toArray(),orderBy, pageable);
				
		List<LotteryStation> list = lQueryResult.getResultList();
		Long totalrow = lQueryResult.getTotalRecord();
		
		//将实体转换为dto
		List<LotteryStationDTO> lotteryStationDTOs = lotteryStationService.toDTOs(list);
		
		returnData.put("rows", lotteryStationDTOs);
		returnData.put("total", totalrow);
	 	
	 	
	 	return returnData;
	}
	
	
	@RequestMapping(value = "/saveOrUpdateLotteryStaion", method = RequestMethod.GET)
	public @ResponseBody ResultBean saveOrUpdateLotteryStaion(
			LotteryStationDTO lotteryStationDTO,
			/*@RequestParam(value="id",required=false) String id,
			@RequestParam(value="stationOwner",required=false) String stationOwner,
			@RequestParam(value="telephone",required=false) String telephone,
			@RequestParam(value="stationNumber",required=false) String stationNumber,
			@RequestParam(value="code",required=false) String code,
			@RequestParam(value="password",required=false) String password,
			@RequestParam(value="province",required=false) String province,
			@RequestParam(value="city",required=false) String city,
			@RequestParam(value="country",required=false) String country,
			@RequestParam(value="coordinate",required=false) String coordinate,
			@RequestParam(value="address",required=false) String address,
			@RequestParam(value="stationInterview",required=false) String stationInterview,
			@RequestParam(value="openDoorTimeStr",required=false) String openDoorTimeStr,
			@RequestParam(value="closeDoorTime",required=false) String closeDoorTime,*/
			ModelMap model,HttpSession httpSession) throws Exception
	{
	   ResultBean resultBean = new ResultBean ();
	   
	   LotteryStation lotteryStation = lotteryStationService.getLotteryStationById(lotteryStationDTO.getId());
	   
	   
	   if(null != lotteryStation)
	   {
//		   lotteryStation.setStatus(lotteryStationDTO.getStatus());//更新彩票站信息审批状态
//		   lotteryStation.setApprovalStatus(lotteryStationDTO.getApprovalStatus());
		   //TODO：后台可以改变彩票站信息吗?
		   lotteryStation.setStationName(lotteryStationDTO.getStationName());
		   
		   lotteryStation.setModifyTime(new Timestamp(System.currentTimeMillis()));
		   lotteryStation.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
		   LOG.info("修改彩票站--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
		   
		   resultBean.setMessage("添加彩票站数据成功!");
		   resultBean.setStatus("success");
	   }
	   else
	   {
		   lotteryStation = new LotteryStation();
		   BeanUtil.copyBeanProperties(lotteryStation, lotteryStationDTO);
		   //获取彩票站站主信息
		   LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.
					getLotterybuyerOrExpertById(lotteryStationDTO.getUserId());
			
		   lotteryStation.setFromApp("0");//后台录入的彩票站信息
		   lotteryStation.setLotteryBuyerOrExpert(lotterybuyerOrExpert);
		   lotteryStation.setApprovalStatus("0");//审核中
		   lotteryStation.setIsDeleted(Constants.IS_NOT_DELETED);
		   lotteryStation.setCreateTime(new Timestamp(System.currentTimeMillis()));
		   lotteryStation.setCreator(LoginUtils.getAuthenticatedUserCode(httpSession));
		   lotteryStation.setModifyTime(new Timestamp(System.currentTimeMillis()));
		   lotteryStation.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
		   LOG.info("添加彩票站--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
		   
		   resultBean.setMessage("添加彩票站数据成功!");
		   resultBean.setStatus("success");
		   
		   
	   }
	   
	   
	   return resultBean;
	
	
	
	}
	
	/**
	 * 更新提交认证彩票站的状态
	* @Title: updateRenzhengStatusLotteryStaion 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotteryStationDTO
	* @param @param request
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月19日 下午3:09:14 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/updateRenzhengStatusLotteryStaion", method = RequestMethod.GET)
	public @ResponseBody ResultBean updateRenzhengStatusLotteryStaion(
				LotteryStationDTO lotteryStationDTO,HttpServletRequest request)
	{
		ResultBean resultBean = new ResultBean();
		
		LotteryStation lotteryStation  = lotteryStationService.getLotteryStationById(lotteryStationDTO.getId());
		
		if(null != lotteryStation)
		{
			lotteryStation.setStatus(lotteryStationDTO.getStatus());
			lotteryStation.setApprovalStatus(lotteryStationDTO.getApprovalStatus());
			lotteryStation.setNotAllowReason(lotteryStationDTO.getNotAllowReason());
			
			//TODO:审核通过后生成彩票站邀请码
			if("1".equals(lotteryStationDTO.getStatus()))
			{//审核通过生成邀请码
				lotteryStation.setInviteCode(lotteryStation.getStationNumber()+this.generateInviteCode());
				
				//佰艺霖用户给一张建群卡
				/*if("1".equals(lotteryStation.getIsBylStation()))
				{
					LotterybuyerOrExpert owner = lotteryStation.getLotteryBuyerOrExpert();
					//TODO:认证成功后，赠一张建群卡
					if("1".equals(lotteryStation.getLotteryType()))
						lotterybuyerOrExpertService.updateCardsOfUser(owner, LotteryStationController.CREATE_TICAI_GROUP_CARD_ID,1);//彩种类型和建群卡id相同
					else
						if("2".equals(lotteryStation.getLotteryType()))
							lotterybuyerOrExpertService.updateCardsOfUser(owner, LotteryStationController.CREATE_FUCAI_GROUP_CARD_ID,1);//彩种类型和建群卡id相同
					
				}*/
				
				//审核通过后，新建群,头像是上传站点信息时
				 /*LotteryGroupDTO groupDTO = new LotteryGroupDTO();
				 groupDTO.setId(UUID.randomUUID().toString());
				 groupDTO.setName(lotteryStation.getStationName()+"奖聊群");
				 groupDTO.setProvince(lotteryStation.getProvince());
				 groupDTO.setCity(lotteryStation.getCity());
				 groupDTO.setLotteryType(lotteryStation.getLotteryType());
				 groupDTO.setDetailLotteryType(lotteryStation.getLotteryType());
				 groupDTO.setFabuKj(1);
				 groupDTO.setFabuZs(1);
				 groupDTO.setSsKjChaxun(0);
				 groupDTO.setSsYlChaxun(0);
				 groupDTO.setSsZjChaxun(0);
				 groupDTO.setNoticeReview(0);
				 groupDTO.setJoinType(0);//0为自由加入
				 groupDTO.setOwnerId(lotteryStation.getLotteryBuyerOrExpert().getId());
				 lotteryGroupService.saveOrUpdateGroup(groupDTO, request);*/
				
				//TODO:审批成功后，将站主用户加入到中心群中
				boolean flag  = lotteryGroupService.joinInCityCenterGroup
						(lotteryStation.getProvince(), "4", lotteryStation.getCity(), lotteryStation.getLotteryType(),lotteryStation.getLotteryBuyerOrExpert().getId());
				LOG.info("加入中心群:"+flag);
				
			}
			
			
			//更新彩票站站主为站主身份
			LotterybuyerOrExpert lotterybuyerOrExpert = lotteryStation.getLotteryBuyerOrExpert();
			lotterybuyerOrExpert.setIsStationOwner("1");//有认证通过的彩票站，则认证其拥有着为站主
			lotterybuyerOrExpertService.update(lotterybuyerOrExpert);
			
			String[] tagsand = {lotterybuyerOrExpert.getTelephone()};//推送给申请认证的用户
			StringBuffer message = new StringBuffer("您申请的站点号为:"+lotteryStation.getStationNumber()+"的彩站认证状态为:"
					+("1".equals(lotteryStationDTO.getStatus())?"通过":"未通过"));
			
			//若审核通过，则信息中连接邀请码
			if("1".equals(lotteryStationDTO.getStatus()))
			{
				message.append(",您的邀请码为:"+lotteryStation.getInviteCode());
			}
			
			PushController.sendPushWithCallback
				(tagsand, null,message.toString() , "sysMessage");//推送给群主展示的是“1”
			
			/*记录系统消息推送到系统消息表*/
			SysMessage sysMessage = new SysMessage();
			sysMessage.setTarget(lotterybuyerOrExpert.getTelephone());
			sysMessage.setMessage(message.toString());
			sysMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
			sysMessage.setCreator("system");
			sysMessage.setModifyTime(new Timestamp(System.currentTimeMillis()));
			sysMessage.setModify("system");
			sysMessage.setIsDeleted(Constants.IS_NOT_DELETED);
			sysMessageService.save(sysMessage);
			
			//更新彩票站的认证状态信息
			lotteryStationService.update(lotteryStation);
			resultBean.setStatus("success");
			resultBean.setMessage("审核成功");
		}
		else
		{
			resultBean.setStatus("error");
			resultBean.setMessage("审核失败");
		}
		
		
		return resultBean;
	}
	
	
	
	//TODO:生成站点邀请码
	private  String generateInviteCode()
	{
		List<LotteryStation> alllist = lotteryStationService.findAll();
		
		int code = alllist.size()+1;
		StringBuffer str = new StringBuffer(code+"");
		//6位邀请码
		while(str.length()<3)
		{
			str.insert(0, "0");
		}
		return str.toString();
	}
	
	

	/**
	 * 
	* @Title: deleteLotteryStations 
	* @Description: TODO(删除彩票站数据) 
	* @param @param ids
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月14日 上午10:35:59 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deleteLotteryStations", method = RequestMethod.POST)
	public @ResponseBody ResultBean  deleteLotteryStations(
			@RequestParam(value="ids",required=false) String[] ids,
			ModelMap model,HttpSession httpSession) throws Exception 
	{
	 
		 ResultBean resultBean = new ResultBean();
		 
		 LotteryStation lotteryStation;
		 for (String id : ids) 
			{
			 	lotteryStation	 = lotteryStationService.getLotteryStationById(id);
			 	if(null != lotteryStation)
			 	{
			 		lotteryStation.setLotteryBuyerOrExpert(null);//移除和站主用户的关系
			 		lotteryStation.setIsDeleted("0");
			 		lotteryStation.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			 		lotteryStation.setModifyTime(new Timestamp(System.currentTimeMillis()));
			 		
			 		//删除彩票站关联数据
			 		
			 		
			 		
			 		
			 		lotteryStationService.update(lotteryStation);
			 		
			 		 //日志输出
					 LOG.info("删除彩票站数据--id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
				   
			 	}
			}
		 String returnMsg = "删除成功!";
		 resultBean.setStatus("success");
		 resultBean.setMessage(returnMsg);
		 
		 return resultBean;
			 
	 
	}
	
	
}
