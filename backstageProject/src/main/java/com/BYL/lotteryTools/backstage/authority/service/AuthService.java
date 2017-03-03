package com.BYL.lotteryTools.backstage.authority.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.authority.dto.AuthorityDTO;
import com.BYL.lotteryTools.backstage.authority.entity.Authority;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface AuthService {

	public Authority getAuthorityById(String id);
	
	public void save(Authority entity);
	
	public void update(Authority entity);
	
	/** 
	  * @Description: 获取权限表所有数据
	  * @author songj@sdfcp.com
	  * @date 2015年9月25日 上午8:58:51 
	  * @return 
	  */
	public List<Authority> findAll();
	
	/***
	 * 
	* @Description: TODO(获取权限数据列表) 
	* @author bann@sdfcp.com
	* @date 2015年10月10日 下午3:04:33
	 */
	public List<Authority> getAuthorityList();

	/**
	 * 
	* @Description: TODO(带分页条件模糊查询权限数据) 
	* @author bann@sdfcp.com
	* @date 2015年10月14日 上午8:55:41
	 */
	public QueryResult<Authority> getAuthList(Class<Authority> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public  AuthorityDTO toDTO(Authority entity);
	
	public  List<AuthorityDTO> toDTOS(List<Authority> entities);
}
