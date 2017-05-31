package com.BYL.lotteryTools.backstage.user.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.BYL.lotteryTools.backstage.authority.dto.AuthorityBean;
import com.BYL.lotteryTools.backstage.authority.dto.AuthorityDTO;
import com.BYL.lotteryTools.backstage.authority.entity.Authority;
import com.BYL.lotteryTools.backstage.authority.service.AuthService;
import com.BYL.lotteryTools.backstage.role.entity.Role;
import com.BYL.lotteryTools.backstage.user.dto.MenuBean;
import com.BYL.lotteryTools.backstage.user.entity.City;
import com.BYL.lotteryTools.backstage.user.entity.Province;
import com.BYL.lotteryTools.backstage.user.entity.Region;
import com.BYL.lotteryTools.backstage.user.entity.User;
import com.BYL.lotteryTools.backstage.user.service.CityService;
import com.BYL.lotteryTools.backstage.user.service.ProvinceService;
import com.BYL.lotteryTools.backstage.user.service.RegionService;
import com.BYL.lotteryTools.backstage.user.service.UserService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.bean.TreeBean;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;
import com.BYL.lotteryTools.common.util.MyMD5Util;
import com.BYL.lotteryTools.common.util.QueryResult;


/** 
  * @ClassName: MenuController 
  * @Description: 目录相关控制层
  * @author songj@sdfcp.com
  * @date 2015年9月23日 下午5:21:54 
  *  
  */
@Controller
@RequestMapping("/menu")
public class MenuController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MenuController.class);
	
    @Autowired
	private AuthService authService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProvinceService provinceService;
    
    @Autowired
	 private CityService cityService;
    
    @Autowired
    private RegionService regionService;
    
    @Autowired
    private UploadfileService uploadfileService;
    
    /**
	 * 
	* @Description: TODO(根据静态变量名称获取变量名称值，方便对静态变量值的统一管理) 
	* @author bann@sdfcp.com
	* @date 2015年10月30日 下午1:36:41
	 */
	@RequestMapping(value = "/getConstant", method = RequestMethod.GET)
	public @ResponseBody ResultBean getConstant(HttpSession httpSession,
			@RequestParam(value="constantName",required=true) String constantName,
			ModelMap model) throws Exception {
		ResultBean resultBean = new ResultBean ();
		
		if("ORIGIN_AUTH_ID".equals(constantName))//获取权限表的虚拟根节点值
		{
			resultBean.setMessage(Constants.ORIGIN_AUTH_ID);
		}
		
		if("PROVINCE_ALL".equals(constantName))//获取省份全部的code
		{
			resultBean.setMessage(Constants.PROVINCE_ALL);
		}
		
		return resultBean;
		
	}
	
	@RequestMapping(value = "/logout.action", method = RequestMethod.GET)
	public String logout(@RequestParam(value="alertmsg",required=false) String alertmsg,
			ModelMap model)
	{
		String indexPage = "firstPage";
		
		model.addAttribute("alertmsg", alertmsg);
		return indexPage;
	}
	
    /**
	 * demo登录提交后跳转方法
	 * @param userName
	 * @param password
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNewPage", method = RequestMethod.POST)
	public String getNewPage(HttpSession httpSession,
			@RequestParam(value="code",required=false) String code,//登录名是user表中的code
			@RequestParam(value="password",required=false) String password,
			ModelMap model) throws Exception {
		

		String message ="success";
		
		User user = userService.getUserByCode(code);
		if(null == user)
		{
			message = "0";//当前登录名不存在，请确认后登录!
		}
		else if("".equals(password))
		{
			message = "1";//登录密码不可以为空!
		}
		else if(null != user && !MyMD5Util.validPassword(password, user.getPassword()))
		{
			message = "2";//登录密码不正确，请确认后登录!
		}
		else
		{
			//向session中写入登录信息
			LoginUtils.setLoginUserMessage(httpSession, code, password, user.getName(),user.getId());
		}
		
		 //日志输出
		   LOG.info("用户登录：登录信息用户名："+code+"密码："+password+"登录状态："+message);
		   
		
		
		model.addAttribute("message", message);
		return "lotteryTools/index";//"user/test"
	}
	
	
	
	/**
	 * 角色管理菜单
	 * @return
	 */
	@RequestMapping(value = "/roleManage.action", method = RequestMethod.GET)
	public String roleManage()
	{
		String indexPage = "lotteryTools/role/roleManage";
		
		
		return indexPage;
	}
	
	/**
	 * 账号管理菜单
	 * @return
	 */
	@RequestMapping(value = "/useraccount.action", method = RequestMethod.GET)
	public String useraccount()
	{
		String indexPage = "lotteryTools/user/user";
		
		
		return indexPage;
	}
	
	/**
	 * 权限管理菜单
	 * @return
	 */
	@RequestMapping(value = "/authority.action", method = RequestMethod.GET)
	public String authority()
	{
		String indexPage = "lotteryTools/authority/authority";
		
		
		return indexPage;
	}
	
	/**
	 * 区域彩种管理菜单
	 * @return
	 */
	@RequestMapping(value = "/areaLottery.action", method = RequestMethod.GET)
	public String areaLottery()
	{
		String indexPage = "lotteryTools/lotteryManage/lotteryPlayManage";
		
		
		return indexPage;
	}
	
	/**
	 * 基础彩种管理菜单
	 * @return
	 */
	@RequestMapping(value = "/baseLottery.action", method = RequestMethod.GET)
	public String baseLottery()
	{
		String indexPage = "lotteryTools/lotteryManage/lotteryPlayBuluPlanManage";
		
		
		return indexPage;
	}
	
	/**
	 * 源码规则管理菜单
	* @Title: orderRule 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @author banna
	* @date 2017年3月27日 下午4:42:37 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/orderRule.action", method = RequestMethod.GET)
	public String orderRule()
	{
		String indexPage = "lotteryTools/orderRule/orderRuleManage";
		
		
		return indexPage;
	}
	
	/**
	 * 基本预测类型菜单
	* @Title: basePtype 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @author banna
	* @date 2017年3月28日 上午10:51:59 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/basePtype.action", method = RequestMethod.GET)
	public String basePtype()
	{
		String indexPage = "lotteryTools/basePredictionType/basePredictionType";
		
		
		return indexPage;
	}
	
	/**
	 * 预测类型菜单
	* @Title: ptype 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @author banna
	* @date 2017年3月28日 下午2:17:24 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/ptype.action", method = RequestMethod.GET)
	public String ptype()
	{
		String indexPage = "lotteryTools/predictionType/predictionType";
		
		
		return indexPage;
	}
	
	/**
	 * 审批彩票站信息
	* @Title: applyStation 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月27日 下午1:22:42 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/applyStation.action", method = RequestMethod.GET)
	public String applyStation()
	{
		String indexPage = "lotteryTools/lotteryStation/applyLotteryStation";
		
		
		return indexPage;
	}
	
	/**
	 * 彩票站管理
	* @Title: stationManage 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月27日 下午1:23:14 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/stationManage.action", method = RequestMethod.GET)
	public String stationManage()
	{
		String indexPage = "lotteryTools/lotteryStation/lotteryStation";
		
		
		return indexPage;
	}
	
	/**
	 * App用户（专家管理）
	* @Title: lotteryExpertOrUser 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月27日 下午1:23:45 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/lotteryExpertOrUser.action", method = RequestMethod.GET)
	public String lotteryExpertOrUser()
	{
		String indexPage = "lotteryTools/lotteryExpertOrUser/lotteryExpertOrUser";
		
		
		return indexPage;
	}
	
	/**
	 * 彩聊群管理
	* @Title: lotteryGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月27日 下午1:25:45 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/lotteryGroup.action", method = RequestMethod.GET)
	public String lotteryGroup()
	{
		String indexPage = "lotteryTools/lotteryGroup/lotteryGroup";
		
		
		return indexPage;
	}
	
	
	/**
	 * 跳转到错误页
	 * @return
	 */
	@RequestMapping(value = "/error.action", method = RequestMethod.GET)
	public String error()
	{
		String indexPage = "error";
		
		
		return indexPage;
	}
	
	/**
	 * 调整到上传附件页
	* @Title: uploadFile 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param uploadId
	* @param @param session
	* @param @param model
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月16日 下午1:39:01 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/uploadFile.action", method = RequestMethod.GET)
	public String uploadFile(@RequestParam(value="uploadId",required=false) String uploadId,HttpSession session,ModelMap model)
	{
		String indexPage = "lotteryTools/uploadFile";
		
		
		model.addAttribute("uploadId", uploadId);
		return indexPage;
	}
	
	
    
	/**
	 * 
	* @Title: save 
	* @author banna    
	* @date 2015年9月22日 上午10:11:56  
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param model
	* @param @param httpSession
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getMenuData", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getMenuData(ModelMap model,HttpSession httpSession) throws Exception {
		
		/**
		 * 动态获取菜单实现
		 * 1.从session中获取当前登录人员的code，根据code获取当前用户对应的角色
		 * 2.便利角色，根据角色获取当前角色对应的权限数据
		 * 3.整理权限数据，将权限数据去重展示
		 * 4.%“baisic”根不动，前台获取也是根据这边name来获取的%
		 */
		//获取session中的登录数据
		String code = LoginUtils.getAuthenticatedUserCode(httpSession);
		User user = userService.getUserByCode(code);
		//获取当前登录人员的角色list
		List<Role> roles = user.getRoles();
		
		Set<Authority> authorities = new HashSet<Authority> ();
		
		for (Role role : roles) {
			
			List<Authority> authin = new ArrayList<Authority> ();
			authin = role.getAuthorities();
			for (Authority authority : authin) {
				if(Constants.IS_NOT_DELETED.equals(authority.getIsDeleted()))//Constants.IS_NOT_DELETED:未删除的权限数据
				{
					authorities.add(authority);
				}
				
			}
			
		}
		
		Map<String,Object> child = new HashMap<String,Object> ();
		List<MenuBean> menubeans = new ArrayList<MenuBean> ();
		child.put("basic", menubeans);
		
		Iterator<Authority> it = authorities.iterator();
		String parentId ;
		while(it.hasNext())
		{
			Authority authin = it.next();
			//如果父节点是根节点的则是菜单的一级菜单
			if(Constants.ORIGIN_AUTH_ID.equals(authin.getParentAuth()))//Constants.ORIGIN_AUTH_ID:权限树的树根节点写死我“1”
			{
				parentId = authin.getId();
				
				List<MenuBean> menus = new ArrayList<MenuBean> ();//下级菜单
				MenuBean mb = new MenuBean();
				mb.setMenuid(authin.getId());//权限id
				mb.setIcon(authin.getAuthImg());//权限图片
				mb.setMenuname(authin.getAuthName());//权限名称
				mb.setMenus(menus);
				menubeans.add(mb);
				
				menus = getChildMenu(parentId, authorities, menus);//获取下级菜单
				
				
				
			}
		}
		
		
		
		
		return child;
	}
	
	/**
	 * 
	* @Description: TODO(获取下级菜单) 
	* @author bann@sdfcp.com
	* @date 2015年10月29日 下午2:09:16
	 */
	private List<MenuBean> getChildMenu(String parentAuthId,Set<Authority> authorities,List<MenuBean> menus)
	{
		
		Iterator<Authority> itin = authorities.iterator();
		
		while(itin.hasNext())
		{
			Authority authchild = itin.next();
			if(parentAuthId.equals(authchild.getParentAuth()))//Constants.ORIGIN_AUTH_ID:权限树的树根节点写死我“1”
			{
				
				MenuBean mb5 = new MenuBean();
				mb5.setMenuid(authchild.getId());
				mb5.setIcon(authchild.getAuthImg());
				mb5.setMenuname(authchild.getAuthName());
				mb5.setUrl(authchild.getUrl());
				
				List<MenuBean> menuChilds = new ArrayList<MenuBean> ();
				menuChilds = getChildMenu(authchild.getId(), authorities, menuChilds);
				if(null != menuChilds &&menuChilds.size()>0)
				{
					mb5.setMenus(menuChilds);
				}
				
				menus.add(mb5);
			}
		}
		
		return menus;
	}
	
	/**
	 * 
	* @Description: TODO(保存或者修改权限) 
	* @author bann@sdfcp.com
	* @date 2015年10月9日 下午2:38:35
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody ResultBean saveOrUpdate(
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="code",required=false) String code,
			@RequestParam(value="authName",required=false) String authName,
			@RequestParam(value="parentAuth",required=false) String parentAuth,
			@RequestParam(value="url",required=false) String url,
			@RequestParam(value="authImg",required=false) String authImg,
			@RequestParam(value="status",required=false) String status,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		ResultBean returnMap = new ResultBean();
		
		Authority authority;
		
		authority = authService.getAuthorityById(id);
		
		if(null != authority)//已存在，进行权限数据的修改操作
		{
			authority.setId(id);
			authority.setCode(code);
			authority.setAuthName(authName);
			authority.setParentAuth(parentAuth);
			authority.setUrl(url);
			authority.setAuthImg(authImg);
			String originStatus = authority.getStatus();//记录修改前的权限状态
			authority.setStatus(status);
			authority.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			authority.setModifyTime(new Timestamp(System.currentTimeMillis()));
			//修改权限数据
			authService.save(authority);
			
			if(!originStatus.equals(status))//若待修改数据的启用状态发生变化，则要判断是否有子级权限，若有则要批量修改启用状态
			{
				List<Authority> authList = new ArrayList<Authority> ();
				authList = getChildauthByRecursive(authList, id, model, httpSession);
				
				if(authList.size()>0)//拥有子级权限，对子级权限的启用状态进行修改
				{
					for (Authority authority2 : authList) 
					{
						authority2.setStatus(status);
						authority2.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
						authority2.setModifyTime(new Timestamp(System.currentTimeMillis()));
						authService.save(authority2);
					}
				}
			}
			
			
			returnMap.setMessage("修改权限成功!");
			returnMap.setStatus("success");
			
		  //日志输出
		   LOG.info("修改权限--权限id="+id+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
			   
		}
		else
		{
			authority = new Authority();
			authority.setCode(code);
			authority.setAuthName(authName);
			authority.setParentAuth(parentAuth);
			authority.setUrl(url);
			authority.setAuthImg(authImg);
			authority.setStatus(status);
			authority.setIsSystem("0");//页面中操作添加的权限都是非系统数据
			authority.setCreator(LoginUtils.getAuthenticatedUserCode(httpSession));
			authority.setCreateTime(new Timestamp(System.currentTimeMillis()));
			authority.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			authority.setModifyTime(new Timestamp(System.currentTimeMillis()));
			authority.setIsDeleted("1");
			//保存权限数据
			authService.save(authority);
			
			returnMap.setMessage("保存权限成功!");
			returnMap.setStatus("success");
			
			 //日志输出
			LOG.info("保存权限--权限code="+code+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
				 
		}
		
		
		
		return returnMap;
	}
	
	/**
	 * 
	* @Description: TODO(使用递归方法获取所有子级权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月16日 下午2:19:41
	 */
	private List<Authority> getChildauthByRecursive(List<Authority> authList,String parentAuth,ModelMap model,HttpSession httpSession)
	{
		List<Authority> authListget = getChildAuthList(parentAuth, model, httpSession);
		
		if(authListget.size()>0)
		{
			for (Authority authority : authListget) {
				
				authList.add(authority);
				
				authListget = getChildauthByRecursive(authList, authority.getId(), model, httpSession);
			}
		}
		
		return authList;
	}
	
	
	/**
	 * 
	* @Description: TODO(根据code获取权限的详细信息（根据唯一条件获取数据）) 
	* @author bann@sdfcp.com
	* @date 2015年10月10日 上午10:16:35
	 */
	@RequestMapping(value = "/getDetailAuth", method = RequestMethod.GET)
	public @ResponseBody AuthorityDTO getDetailAuth(
			@RequestParam(value="code",required=false) String code,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Authority authority = new Authority();
		
		authority = authService.getAuthorityById(code);
		
		AuthorityDTO authorityDTO = authService.toDTO(authority);
		
		return authorityDTO;
	}
	
	
	/**
	 * 
	* @Description: TODO(获取权限列表数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月10日 下午3:14:46
	 */
	@RequestMapping(value = "/getParentAuth", method = RequestMethod.POST)
	public @ResponseBody List<AuthorityBean> getParentAuth(
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="code",required=false) String code,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		//放置分页参数
		Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		if(null != code && !"".equals(code))
		{
			params.add(code);//不查询自身数据，前台标记唯一一条数据使用的是id
			buffer.append(" and  id != ?").append(params.size());
		}
		
		if(null != status && !"".equals(status))
		{
			params.add(status);//只查询有效的数据
			buffer.append(" and  status = ?").append(params.size());
		}
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("id", "asc");
		
		QueryResult<Authority> authlist = authService.getAuthList(Authority.class, buffer.toString(), params.toArray(),
				orderBy, pageable);
		
		List<Authority> authes = authlist.getResultList();
		
		List<AuthorityBean> authBeanlist = new ArrayList<AuthorityBean>();
		
		
		for (Authority authority : authes) 
		{
			AuthorityBean authbeanin = new AuthorityBean();
			
			authbeanin.setCode(authority.getId());
			authbeanin.setAuthName(authority.getAuthName());
			
			authBeanlist.add(authbeanin);
		}
		
		
		return authBeanlist;
	}
	
	/**
	 * 
	* @Description: TODO(查询权限数据带分页) 
	* @author bann@sdfcp.com
	* @date 2015年10月14日 上午8:58:45
	 */
	@RequestMapping(value = "/getAuthList", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getAuthList(
			@RequestParam(value="page",required=false) int page,
			@RequestParam(value="rows",required=false) int rows,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="parentCode",required=false) String parentCode,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		Map<String,Object> returnData = new HashMap<String,Object> ();
		
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
			params.add(status);//只查询有效的数据
			buffer.append(" and status = ?").append(params.size());
		}
		
		if(null != parentCode && !"".equals(parentCode))
		{
			params.add(parentCode);//查询父级code为当前值的数据
			buffer.append(" and parentAuth = ?").append(params.size());
		}
		
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("code", "desc");
		
		QueryResult<Authority> authlist = authService.getAuthList(Authority.class, buffer.toString(), params.toArray(),
				orderBy, pageable);
		
		//处理返回数据
		List<Authority> authorities = authlist.getResultList();
		Long totalrow = authlist.getTotalRecord();
		
		List<AuthorityDTO> authorityDTOs = authService.toDTOS(authorities);
		
		returnData.put("rows", authorityDTOs);
		returnData.put("total", totalrow);
		
		return returnData;
	}
	
	/**
	 * 
	* @Description: TODO(删除权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月12日 下午4:02:56
	 */
	@RequestMapping(value = "/deleteAuth", method = RequestMethod.POST)
	public @ResponseBody ResultBean deleteAuth(
			@RequestParam(value="codes",required=false) String[] codes,
			ModelMap model,HttpSession httpSession) throws Exception
	{
		ResultBean resultBean = new ResultBean();
		
		Authority authority ;
		for (String code : codes) 
		{
			authority = new Authority();
			authority =  authService.getAuthorityById(code);//code传递进去的参数实际是id
			authority.setIsDeleted("0");;//设置当前数据为已删除状态
			authority.setModify(LoginUtils.getAuthenticatedUserCode(httpSession));
			authority.setModifyTime(new Timestamp(System.currentTimeMillis()));
			authService.save(authority);//保存更改状态的权限实体
			
			 //日志输出
			LOG.info("删除权限--权限id="+code+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
				
		}
		
		
		resultBean.setStatus("success");
		resultBean.setMessage("删除成功!");
		
		return resultBean;
	}
	
	
	/**
	 * 
	* @Description: 获取登陆人信息
	* @author bann@sdfcp.com
	* @date 2015年12月3日 上午9:34:47
	 */
	@RequestMapping(value = "/getLoginmsg", method = RequestMethod.POST)
	public @ResponseBody ResultBean getLoginmsg(
			ModelMap model,HttpSession httpSession) throws Exception
	{
		ResultBean resultBean = new ResultBean();
		
		String name = LoginUtils.getAuthenticatedUserName(httpSession);
		
		resultBean.setMessage(name);
		
		return resultBean;
	}
	
	
	/**
	 * 
	* @Description: TODO(获取权限页面的权限树) 
	* @author bann@sdfcp.com
	* @date 2015年10月14日 下午2:59:13
	 */
	@RequestMapping(value = "/getTreedata", method = RequestMethod.POST)
	public @ResponseBody List<TreeBean> getTreedata(ModelMap model,HttpSession httpSession) throws Exception {
		
		
		
		//放置分页参数
		Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		params.add("1");//树种只显示启用状态的权限数据
		buffer.append(" and  status = ?").append(params.size());
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("code", "desc");
		
		QueryResult<Authority> authlist = authService.getAuthList(Authority.class, buffer.toString(), params.toArray(),
				orderBy, pageable);
		
		List<Authority> authes = authlist.getResultList();
		
		List<TreeBean> treeBeanList = new ArrayList<TreeBean> ();
		
		for (Authority authority : authes) {
			
			TreeBean treeBeanIn = new TreeBean();
			treeBeanIn.setId(authority.getId());
//			treeBeanIn.setParent(true);
			treeBeanIn.setName(authority.getAuthName());
			treeBeanIn.setOpen(true);
			treeBeanIn.setpId(authority.getParentAuth());
			treeBeanList.add(treeBeanIn);
		}
		
		return treeBeanList;
	}
	
	/**
	 * 
	* @Description: TODO(获取子级权限列表) 
	* @author bann@sdfcp.com
	* @date 2015年10月16日 下午2:14:55
	 */
	@RequestMapping(value = "/getChildAuthList", method = RequestMethod.POST)
	public @ResponseBody List<Authority> getChildAuthList(@RequestParam(value="parentAuth",required=false) String parentAuth,
			ModelMap model,HttpSession httpSession)
	{
		List<Authority> authList = new ArrayList<Authority> ();
		
		//放置分页参数
		Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		//连接父级权限id查询条件，用来查询当前id下是否有子级权限
		if(null != parentAuth && !"".equals(parentAuth))
		{
			params.add(parentAuth);
			buffer.append(" and parentAuth = ?").append(params.size());
		}
		
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		
		QueryResult<Authority> authlist = authService.getAuthList(Authority.class, buffer.toString(), params.toArray(),
				orderBy, pageable);
		
		authList = authlist.getResultList();
		
		return authList;
	}
	
	/**
	 * 
	* @Description: TODO(权限输入值校验，用来校验code唯一性和authname唯一性) 
	* @author bann@sdfcp.com
	* @date 2015年10月15日 上午10:55:07
	 */
	@RequestMapping(value = "/checkValue", method = RequestMethod.POST)
	public @ResponseBody ResultBean  checkValue(
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="code",required=false) String code,
			@RequestParam(value="authname",required=false) String authname,
			@RequestParam(value="parentAuth",required=false) String parentAuth,
			@RequestParam(value="status",required=false) String status,
			ModelMap model,HttpSession httpSession) throws Exception {
		
		ResultBean resultBean = new ResultBean ();
		
		//放置分页参数
		Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		if(null != code && !"".equals(code))
		{
			params.add(code);
			buffer.append(" and code = ?").append(params.size());
		}
		
		if(null != authname && !"".equals(authname))
		{
			params.add(authname);
			buffer.append(" and authName = ?").append(params.size());
		}
		
		if(null != id && !"".equals(id))
		{//校验修改中的值的唯一性
			params.add(id);
			buffer.append(" and id != ?").append(params.size());
		}
		
		//连接父级权限id查询条件，用来查询当前id下是否有子级权限
		if(null != parentAuth && !"".equals(parentAuth))
		{
			params.add(parentAuth);
			buffer.append(" and parentAuth = ?").append(params.size());
		}
		
		//连接状态条件
		if(null != status && !"".equals(status))
		{
			params.add(status);
			buffer.append(" and status = ?").append(params.size());
		}
		
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		
		QueryResult<Authority> authlist = authService.getAuthList(Authority.class, buffer.toString(), params.toArray(),
				orderBy, pageable);
		
		if(authlist.getResultList().size()>0)
		{
			resultBean.setExist(true);//若查询的数据条数大于0，则当前输入值已存在，不符合唯一性校验
		}
		else
		{
			resultBean.setExist(false);
		}
		
		return resultBean;
		
	}
	
	
	@RequestMapping(value = "/getProvinceList", method = RequestMethod.POST)
	public @ResponseBody List<Province> getProvinceList(
			@RequestParam(value="isHasall",required=false) boolean isHasall,
			ModelMap model,HttpSession httpSession) throws Exception
	{
	 	
	 	List<Province> provinceList = provinceService.findAll();
	 	
	 	//11/4:现阶段业务定义为省份区域为必选，不可选择全部，而市级区域可以选择全部
	 	if(isHasall)
	 	{
	 		Province provineall = new Province();
		 	provineall.setPcode(Constants.PROVINCE_ALL);
		 	provineall.setPname(Constants.PROVINCE_ALL_NAME);	
		 	provinceList.add(provineall);
	 	}
	 	
	 	
	 	return provinceList;
	}
	
	
	
	
	 @RequestMapping(value = "/getCityList", method = RequestMethod.POST)
		public @ResponseBody List<City> getCityList(
				@RequestParam(value="pcode",required=false) String pcode,
				@RequestParam(value="isHasall",required=false) boolean isHasall,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	List<City> cities = null;
		 	if(null != pcode && !"".equals(pcode))
		 	{
		 		cities = cityService.findCitiesOfProvice(pcode);
		 	}
		 	if(!isHasall){
			 	return cities;
		 	}else{
		 		City cityall = new City();
			 	cityall.setCcode(Constants.CITY_ALL);
			 	cityall.setCname(Constants.CITY_ALL_NAME);
			 	cities.add(cityall);
		 	}
		 	return cities;
		}
	
	
	 @RequestMapping(value = "/getRegionList", method = RequestMethod.POST)
		public @ResponseBody List<Region> getRegionList(
				@RequestParam(value="isHasall",required=false) boolean isHasall,
				@RequestParam(value="ccode",required=false) String ccode,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 List<Region> regions = null;
		 	if(null != ccode && !"".equals(ccode))
		 	{
		 		regions = regionService.findRegionsOfCity(ccode);
		 	}
		 
		 	if(isHasall){
			 	Region regionAll = new Region();
			 	regionAll.setAcode(Constants.REGION_ALL);
			 	regionAll.setAname(Constants.REGION_ALL_NAME);
			 	regions.add(regionAll);
		 	}
		 	return regions;
		}
	
	
	 /**
	  * 根据newsUuid获取附件信息
	 * @Title: getUploadFileData 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param newsUuid
	 * @param @param model
	 * @param @param httpSession
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @author banna
	 * @date 2017年5月16日 下午1:44:25 
	 * @return Uploadfile    返回类型 
	 * @throws
	  */
	 @RequestMapping(value = "/getUploadFileData")
		public @ResponseBody Uploadfile getUploadFileData(
				@RequestParam(value="newsUuid",required=false) String newsUuid,
				ModelMap model,HttpSession httpSession) throws Exception
		{
		 	Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(newsUuid);
		 	
		 	return uploadfile;
		}
	
	
	
	
	
	

	
}
