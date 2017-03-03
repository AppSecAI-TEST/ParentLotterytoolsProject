package com.BYL.lotteryTools.backstage.user.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.role.entity.Role;
import com.BYL.lotteryTools.backstage.user.dto.AccountBean;
import com.BYL.lotteryTools.backstage.user.dto.UserRelaRoleBean;
import com.BYL.lotteryTools.backstage.user.entity.User;
import com.BYL.lotteryTools.backstage.user.service.UserService;
import com.BYL.lotteryTools.common.bean.DictBean;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;
import com.BYL.lotteryTools.common.util.MyMD5Util;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController 
{
	private Logger logger  = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 添加或修改用户信息
	* @Title: saveOrUpdate 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param accountBean
	* @param @param model
	* @param @param httpSession
	* @param @return    设定文件 
	* @author banna
	* @date 2017年3月2日 上午10:49:41 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ResultBean saveOrUpdate(
			AccountBean accountBean,
			ModelMap model,HttpSession httpSession)  
	{
		ResultBean resultBean = new ResultBean();
		
		User user = userService.getUserById(accountBean.getId());
		try 
		{
			
			if(null != user)
			{//修改
				String passwordInDB = user.getPassword();
				List<Role> rolesInDB = user.getRoles();
				BeanUtil.copyBeanProperties(user, accountBean);
				if(!passwordInDB.equals(accountBean.getPassword()))
				{
					user.setPassword(MyMD5Util.getEncryptedPwd(accountBean.getPassword()));
				}
				user.setRoles(rolesInDB);
				user.setProvinceCode(accountBean.getProvince());
				user.setCityCode(accountBean.getCity());
				user.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
				user.setModifyTime(new Timestamp(System.currentTimeMillis()));
				userService.update(user);//修改用户
			}
			else
			{//添加
				user = new User();
				BeanUtil.copyBeanProperties(user, accountBean);
				user.setPassword(MyMD5Util.getEncryptedPwd(accountBean.getPassword()));
				user.setProvinceCode(accountBean.getProvince());
				user.setCityCode(accountBean.getCity());
				user.setIsDeleted(Constants.IS_NOT_DELETED);
				user.setCreator(LoginUtils.getAuthenticatedUserCode(httpSession));
				user.setCreateTime(new Timestamp(System.currentTimeMillis()));
				user.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
				user.setModifyTime(new Timestamp(System.currentTimeMillis()));
				
				userService.save(user);//添加用户
			}
			resultBean.setMessage("操作成功!");
			resultBean.setStatus("success");
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			resultBean.setMessage("操作异常!");
			resultBean.setStatus("failure");
		}
		
		
		
		return resultBean;
	}
	
	/**
	 * 删除用户
	* @Title: deleteUserByIds 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param ids
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月2日 上午11:07:20 
	* @return ResultBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deleteUserByIds", method = RequestMethod.POST)
	public @ResponseBody ResultBean deleteUserByIds(
			@RequestParam(value="ids",required=false) String[] ids,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		ResultBean resultBean = new ResultBean();
		
		User user;
		for (String id : ids) 
		{
			user = new User();
			user = userService.getUserById(id);
			user.setIsDeleted(Constants.IS_DELETED);;//设置当前数据为已删除状态
			user.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			user.setModifyTime(new Timestamp(System.currentTimeMillis()));
			userService.update(user);//保存更改状态的角色实体
			
			//日志输出
			logger.info("删除user--userId="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
			   
		}
		
		resultBean.setStatus("success");
		resultBean.setMessage("删除成功!");
		
		return resultBean;
	}
	
	/**
	 * 获取用户详情
	* @Title: getDetailAccount 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月2日 上午11:22:16 
	* @return AccountBean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getDetailUser", method = RequestMethod.GET)
	public @ResponseBody AccountBean getDetailUser(
			@RequestParam(value="id",required=true) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		AccountBean accountBean = new AccountBean();
		
		User user = userService.getUserById(id);
		
		if(null != user)
		{
			BeanUtil.copyBeanProperties(accountBean, user);
			accountBean.setRoles(null);
			accountBean.setCity(user.getCityCode());
			accountBean.setProvince(user.getProvinceCode());
		}
		
		
		return accountBean;
	}
	
	/**
	 * 获取用户列表
	* @Title: getUserList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param page
	* @param @param rows
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @author banna
	* @date 2017年3月2日 下午3:18:02 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getUserList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getUserList(
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Map<String,Object> rtMap = new HashMap<String, Object>(); 
		Pageable pageable = new PageRequest(page-1, rows);
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add(Constants.IS_NOT_DELETED);//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		rtMap = userService.getScrollDataByJpql(User.class,buffer.toString(), params.toArray(),null , pageable);
		return rtMap;
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public @ResponseBody ResultBean  updatePassword(
			@RequestParam(value="password",required=true) String newPassword,
			ModelMap model,HttpSession httpSession) throws Exception{
		ResultBean resultBean = new ResultBean();
		try 
		{
			String userCode = LoginUtils.getAuthenticatedUserCode(httpSession);
			userService.savePassword(MyMD5Util.getEncryptedPwd(newPassword), userCode);
			resultBean.setStatus("success");
			resultBean.setMessage("密码修改成功!");
		}
		catch (Exception e) 
		{
			resultBean.setStatus("failure");
			resultBean.setMessage(e.getMessage());
		}
		finally
		{
			return resultBean;
		}
	}
	
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/getUserRelaRoleList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getUserRelaRoleList(
			@RequestParam(value="id",required=false) String id,
			ModelMap model,HttpSession httpSession) throws Exception
	{
	    Map<String,Object> returnMap = new HashMap<String,Object>();
		List<UserRelaRoleBean> roleBeans = new ArrayList<UserRelaRoleBean>();
	    List<Role> roles = new ArrayList<Role>();
		try 
		{
			User user = userService.getUserById(id);
			roles = user.getRoles();
			if(null != roles && roles.size() > 0)
			{
				for(Role role : roles)
				{
					UserRelaRoleBean roleBean = new UserRelaRoleBean();
					roleBean.setRoleId(role.getId());
					roleBean.setRoleCode(role.getCode());
					roleBean.setRoleName(role.getName());
					roleBean.setParentRolename(role.getParentRolename());
					roleBean.setParentRole(role.getParentRole());
					roleBean.setParentUid(user.getParentUid());
					roleBeans.add(roleBean);
				}
			}
			returnMap.put("rows", roleBeans);
			returnMap.put("total", roleBeans.size());
		}catch (Exception e)
		{
			returnMap.put("success", false);
			returnMap.put("message",e.getMessage());
		}
		finally
		{	
			return returnMap;
		}
	}
	
	
	@RequestMapping(value = "/getRoleRelaUserList", method = RequestMethod.POST)
	public @ResponseBody List<DictBean> getRoleRelaUserList(
			@RequestParam(value="parentRoleId",required=true) String parentRoleId,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		List<DictBean> dictList = new ArrayList<DictBean>();
		if(!StringUtils.isEmpty(parentRoleId))
		{
			List<AccountBean> userList = userService.findAccountsByRoleId(parentRoleId);
			if(null !=userList && userList.size() > 0)
			{
				for(AccountBean accountBean : userList)
				{
					DictBean dictBean = new DictBean();
					dictBean.setId(accountBean.getId());
					dictBean.setName(accountBean.getName());
					dictList.add(dictBean);
				}
			}
		}
		
		return dictList;
	}
	
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/saveUserRleaRole", method = RequestMethod.POST)
	public @ResponseBody ResultBean  saveUserRleaRole(
			@RequestParam(value="role",required=true) String role,
			@RequestParam(value="userId",required=true) String userId,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		ResultBean resultBean = new ResultBean();
		try
		{
			//把json字符串转换成对象
			JSONObject jsonObj = JSONObject.parseObject(role);
			userService.saveUserRelaRole(userId,(UserRelaRoleBean)JSONObject.toJavaObject(jsonObj,UserRelaRoleBean.class));
			resultBean.setStatus("success");
			resultBean.setMessage("保存成功!");
		}
		catch(Exception e)
		{
			resultBean.setStatus("failure");
			resultBean.setMessage(e.getMessage());
		}
		finally
		{
			return resultBean;
		}
	}
	
	@RequestMapping(value = "/checkValue", method = RequestMethod.GET)
	public @ResponseBody boolean  checkValue(
			@RequestParam(value="code",required=false) String code,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		User user = userService.getUserByCode(code);
		if(null == user){
			return false;
		}else{
			return true;
		}
	}
	
}
