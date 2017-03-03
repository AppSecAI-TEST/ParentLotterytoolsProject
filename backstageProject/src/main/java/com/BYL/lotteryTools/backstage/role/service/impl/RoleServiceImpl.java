package com.BYL.lotteryTools.backstage.role.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.role.dto.RoleDTO;
import com.BYL.lotteryTools.backstage.role.entity.Role;
import com.BYL.lotteryTools.backstage.role.repository.RoleRepository;
import com.BYL.lotteryTools.backstage.role.service.RoleService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("roleService")
@Transactional(propagation=Propagation.REQUIRED)
public class RoleServiceImpl implements RoleService 
{
	@Autowired
	private RoleRepository roleRepository;

	public Role getRoleById(String id) {
		return roleRepository.getRoleById(id);
	}

	public void save(Role entity) {
		roleRepository.save(entity);
	}

	public void update(Role entity) {
		roleRepository.save(entity);
		
	}

	public QueryResult<Role> getRolelist(Class<Role> entityClass,
			String whereJpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby, Pageable pageable) {
		QueryResult<Role> rolelist = roleRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,
				orderby, pageable);
		return rolelist;
	}

	public List<RoleDTO> toDTOS(List<Role> entities) {
		List<RoleDTO> dtos = new ArrayList<RoleDTO>();
		RoleDTO dto;
		for (Role entity : entities) 
		{
			dto = toDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	public RoleDTO toDTO(Role entity) {
		RoleDTO dto = new RoleDTO();
		try {
			BeanUtil.copyBeanProperties(dto, entity);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
					
		return dto;
	}

}
