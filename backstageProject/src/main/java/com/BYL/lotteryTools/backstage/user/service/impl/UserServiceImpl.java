package com.BYL.lotteryTools.backstage.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.role.entity.Role;
import com.BYL.lotteryTools.backstage.role.repository.RoleRepository;
import com.BYL.lotteryTools.backstage.user.dto.AccountBean;
import com.BYL.lotteryTools.backstage.user.dto.UserRelaRoleBean;
import com.BYL.lotteryTools.backstage.user.entity.User;
import com.BYL.lotteryTools.backstage.user.entity.UserRelaRole;
import com.BYL.lotteryTools.backstage.user.repository.UserRelaRoleRepository;
import com.BYL.lotteryTools.backstage.user.repository.UserRepository;
import com.BYL.lotteryTools.backstage.user.service.UserService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("userService")
@Transactional(propagation=Propagation.REQUIRED)
public class UserServiceImpl implements UserService 
{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRelaRoleRepository userRelaRoleRepository;
	
	public User getUserByCode(String code) 
	{
		return userRepository.getUserByCode(code);
	}

	public void save(User entity) {
		userRepository.save(entity);
	}

	public void update(User entity) {
		userRepository.save(entity);		
	}

	public User getUserById(String id) {
		return userRepository.getUserById(id);
	}
	
	public QueryResult<User> getUserList(Class<User> entityClass, String whereJpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby, Pageable pageable) {
			QueryResult<User> stationObj = userRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,orderby, pageable);
			return stationObj;
	}
	
	public Map<String,Object> getScrollDataByJpql(Class<User> entityClass,String whereJpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby, Pageable pageable) {
		Map<String,Object> returnData = new HashMap<String,Object>();
		List<AccountBean> accountList = new ArrayList<AccountBean>();
		QueryResult<User> userObj = userRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,orderby, pageable);
		List<User> userlist = userObj.getResultList();
		Long totalrow = userObj.getTotalRecord();
		for(User user : userlist){
			AccountBean accountBean = new AccountBean();
			accountBean.setId(user.getId());
			accountBean.setCode(user.getCode());
			accountBean.setName(user.getName());
			accountBean.setStatus(user.getStatus());
			accountBean.setLotteryType(user.getLotteryType());//date：2016-4-18日添加彩种返回值
			accountBean.setTelephone(user.getTelephone());
			accountBean.setCreater(user.getCreator());
			accountBean.setCreaterTime(DateUtil.formatDate(user.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
			List<UserRelaRoleBean> roleBeanList = new ArrayList<UserRelaRoleBean>();
			for(Role role : user.getRoles()){
				UserRelaRoleBean userRelaRoleBean = new UserRelaRoleBean();
				userRelaRoleBean.setRoleId(role.getId());
				userRelaRoleBean.setRoleCode(role.getCode());
				userRelaRoleBean.setRoleName(role.getName());
				roleBeanList.add(userRelaRoleBean);
			}
			accountBean.setRoles(roleBeanList);
			accountList.add(accountBean);
		}
		returnData.put("rows", accountList);
		returnData.put("total", totalrow);
		return returnData;
	}
	
	public void savePassword(String newPassword,String userCode){
		User user = this.getUserByCode(userCode);
		user.setPassword(newPassword);
		userRepository.save(user);
	}
	
	public List<AccountBean> findAccountsByRoleId(String roleId) {
		Role role = roleRepository.getRoleById(roleId);
		List<User> users = role.getUsers();
		if(users!=null && users.size()>0){
			List<AccountBean> accountList = new ArrayList<AccountBean>();
			for(User user : users){
				AccountBean accountBean = new AccountBean();
				try {
					BeanUtil.copyBeanProperties(accountBean, user);
				} catch (Exception e) {
					e.printStackTrace();
				}
				accountList.add(accountBean);
			}
			return accountList;
		}
		return null;
	}

	public void saveUserRelaRole(String userId,UserRelaRoleBean roleBean){
		//删除以前的关系
		List<UserRelaRole> userRelaRoleList = userRelaRoleRepository.findUserRelaRoleByUserId(userId);
		for(UserRelaRole userRelaRole : userRelaRoleList){
			userRelaRoleRepository.delete(userRelaRole);
		}
		//加入新关系
		UserRelaRole userRelaRole = new UserRelaRole();
		userRelaRole.setRoleId(roleBean.getRoleId());
		userRelaRole.setUserId(userId);
		userRelaRoleRepository.save(userRelaRole);
		//向user表中加入parentUid
		User user = this.getUserById(userId);
		user.setParentUid(roleBean.getParentUid());
		userRepository.save(user);
	}
}
