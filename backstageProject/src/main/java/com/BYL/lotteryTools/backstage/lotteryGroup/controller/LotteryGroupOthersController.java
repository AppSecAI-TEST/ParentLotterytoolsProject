package com.BYL.lotteryTools.backstage.lotteryGroup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.common.exception.GlobalExceptionHandler;

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
