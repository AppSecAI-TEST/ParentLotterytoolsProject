package com.BYL.lotteryTools.backstage.role.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.role.dto.RoleDTO;
import com.BYL.lotteryTools.backstage.role.entity.Role;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface RoleService {

	public Role getRoleById(String id);
	
	public void save(Role entity);
	
	public void update(Role entity);
	
	public QueryResult<Role> getRolelist(Class<Role> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable);
	
	public  List<RoleDTO> toDTOS(List<Role> entities);
	
	public  RoleDTO toDTO(Role entity);
}
