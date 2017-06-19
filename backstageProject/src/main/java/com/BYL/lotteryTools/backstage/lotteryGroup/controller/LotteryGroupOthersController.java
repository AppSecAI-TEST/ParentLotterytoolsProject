package com.BYL.lotteryTools.backstage.lotteryGroup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.GroupMsgOfPushDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.dto.LotteryGroupDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.GroupMsgOfPush;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.GroupMsgOfPushService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.exception.GlobalExceptionHandler;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.QueryResult;

/**
 * 彩聊群周边业务处理controller
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年6月7日 下午3:43:40
 */
@Controller
@RequestMapping("/lgroupOthers")
public class LotteryGroupOthersController extends GlobalExceptionHandler
{
	private static final Logger LOG = LoggerFactory.getLogger(LotteryGroupOthersController.class);
	
	@Autowired
	private LotteryGroupService lotteryGroupService;
	
	@Autowired
	private RongyunImService rongyunImService;
	
	@Autowired
	private GroupMsgOfPushService groupMsgOfPushService;
	
	/**1.群通知发送方法**/
	
	
	/**
	 * 给群发送消息
	* @Title: sendMsgToGroups 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param files
	* @param @param groupIds
	* @param @param msg
	* @param @param type
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年6月7日 下午5:13:03 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/sendMsgToGroups", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> sendMsgToGroups(
			@RequestParam(value="newsUuid",required=false) String newsUuid,//图片附件id
			@RequestParam(value="groupIds",required=false) String[] groupIds,//要发送消息的群
			@RequestParam(value="msg",required=false) String msg,//发送的文字信息
			@RequestParam(value="type",required=false) String type,//发送信息类型，0：文字 1：图片 2：图文形式
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<LotteryGroup> groups = new ArrayList<LotteryGroup>();
		//TODO:使用机器人推送图片信息时，要区分类型，因为“zoushi”extra值会跳转到走势图，所以需要给其他类型
		
		
		return map;
	}
	
	/**
	 * 获取群推送信息列表
	 */
	@RequestMapping(value="/getPushGroupMsgList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getGroupList(
			GroupMsgOfPushDTO dto,
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		QueryResult<GroupMsgOfPush> queryResult = groupMsgOfPushService.getGroupMsgOfPushList(dto, page, rows);
		List<GroupMsgOfPushDTO> dtos = groupMsgOfPushService.toDTO(queryResult.getResultList());
		
		map.put("rows",dtos);
		map.put("total", queryResult.getTotalRecord());
		
		return map;
	}
	
	/**
	 * 保存或修改群推送通知数据
	* @Title: saveOrUpdatePushGroupMsg 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto
	* @param @param request
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月12日 下午12:05:45 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value="/saveOrUpdatePushGroupMsg", method = RequestMethod.GET)
	public @ResponseBody ResultBean saveOrUpdatePushGroupMsg(
			GroupMsgOfPushDTO dto,
			HttpServletRequest request,HttpSession httpSession)
	{
		ResultBean bean = new ResultBean();
		
		
		
		return bean;
	}
	
	/**2.群申诉模块方法**/
	
	/**
	 * 获取群申诉数据
	* @Title: getAppealsOfGroups 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param groupIds
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年6月7日 下午5:25:05 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getAppealsOfGroups", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getAppealsOfGroups(
			@RequestParam(value="groupId",required=false) String groupIds,//群id
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		//TODO:
		
		return map;
	}
	
	/**
	 * 处理群申诉方式
	* @Title: manageAppealsOfGroups 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param groupIds
	* @param @param type
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年6月7日 下午5:26:34 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/manageAppealsOfGroups", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> manageAppealsOfGroups(
			@RequestParam(value="groupId",required=false) String groupId,//群id
			@RequestParam(value="appealUserIds",required=false) String[] appealUserIds,//被申诉的用户id
			@RequestParam(value="type",required=false) String type,//申诉处理类型，0：警告 1.解散 2.移除用户
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Map<String,Object> map = new HashMap<String, Object>();
		
		//TODO:
		
		
		return map;
	}
	
	
}
