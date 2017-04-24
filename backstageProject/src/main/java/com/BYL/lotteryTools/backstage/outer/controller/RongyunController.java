package com.BYL.lotteryTools.backstage.outer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;

/**
 * 融云控制层
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年4月14日 下午2:27:11
 */
@Controller
@RequestMapping("/rongyun")
public class RongyunController 
{
	@Autowired
	private RongyunImService rongyunImService;
	
	
	/**
	 * 根据用户信息获取token
	* @Title: getToken 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId
	* @param @param username
	* @param @param imguri
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月14日 下午1:58:12 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getToken",method=RequestMethod.GET)
	public @ResponseBody String  getToken(@RequestParam(value="userId",required=false) String userId,
			@RequestParam(value="username",required=false) String username,
			@RequestParam(value="imguri",required=false) String imguri)
	{
		String token = rongyunImService.getUserToken(userId, username, imguri);
		
		return token;
	}
	
	/**
	 * 创建群组
	* @Title: createGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param createUserId
	* @param @param groupId
	* @param @param groupName
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月14日 下午2:13:07 
	* @return String    返回类型 
	* @throws
	 */
	/*@RequestMapping(value="/createGroup",method=RequestMethod.GET)
	public @ResponseBody String  createGroup(@RequestParam(value="joinUserId",required=false) String[] joinUserId,
			@RequestParam(value="groupId",required=false) String groupId,
			@RequestParam(value="groupName",required=false) String groupName)
	{
		String result = rongyunImService.createGroup(joinUserId, groupId, groupName);
		
		return result;
	}*/
}
