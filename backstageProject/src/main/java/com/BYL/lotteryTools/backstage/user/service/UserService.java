package com.BYL.lotteryTools.backstage.user.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.user.dto.AccountBean;
import com.BYL.lotteryTools.backstage.user.dto.UserRelaRoleBean;
import com.BYL.lotteryTools.backstage.user.entity.User;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface UserService {

	public User getUserByCode(String code);

	public void  save(User entity);
	
	public void update(User entity);
	
	public User getUserById(String id);
	
	public QueryResult<User> getUserList(Class<User> entityClass, String whereJpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public Map<String,Object> getScrollDataByJpql(Class<User> entityClass,String whereJpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public void savePassword(String newPassword,String userCode);
	
	public List<AccountBean> findAccountsByRoleId(String roleId);
	
	public void saveUserRelaRole(String userId,UserRelaRoleBean roleBean);
}
