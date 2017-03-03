package com.BYL.lotteryTools.backstage.authority.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.authority.dto.AuthorityDTO;
import com.BYL.lotteryTools.backstage.authority.entity.Authority;
import com.BYL.lotteryTools.backstage.authority.repository.AuthorityRepository;
import com.BYL.lotteryTools.backstage.authority.service.AuthService;
import com.BYL.lotteryTools.backstage.role.entity.Role;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("authService")
@Transactional(propagation=Propagation.REQUIRED)
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthorityRepository authorityRepository;
	
	public Authority getAuthorityById(String id) {
		return authorityRepository.getAuthorityById(id);
	}

	public void save(Authority entity) {
		authorityRepository.save(entity);		
	}

	public void update(Authority entity) {
		authorityRepository.save(entity);		
		
	}

	public List<Authority> findAll() {
		return authorityRepository.findAll();
	}

	public List<Authority> getAuthorityList()
	{
		List<Authority> authorities = new ArrayList<Authority>();

		authorities = authorityRepository.findAll();
		
			
		return authorities;
	}

	public QueryResult<Authority> getAuthList(Class<Authority> entityClass,
			String whereJpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby, Pageable pageable) {
		QueryResult<Authority> authlist = authorityRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,
				orderby, pageable);
		return authlist;
	}

	public AuthorityDTO toDTO(Authority entity) {
		AuthorityDTO dto = new AuthorityDTO();
		try {
			BeanUtil.copyBeanProperties(dto, entity);
			
			if(null != entity.getRoles() && entity.getRoles().size()>0)
			{
				String connectRole = "1";
				dto.setConnectRole(connectRole);
				StringBuffer bindRoles = new StringBuffer();
				String roleName = "";
				for (Role role : entity.getRoles()) {
					
					roleName = role.getName();
					if(bindRoles.length()==0)
					{
						bindRoles.append(roleName);
					}
					else
					{
						bindRoles.append("、").append(roleName);
					}
					
				}
				dto.setBindRoles(bindRoles.toString());
			}
			else
			{
				String connectRole = "0";
				dto.setConnectRole(connectRole);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
					
		return dto;
	}

	public List<AuthorityDTO> toDTOS(List<Authority> entities) {
		List<AuthorityDTO> dtos = new ArrayList<AuthorityDTO>();
		AuthorityDTO dto;
		for (Authority entity : entities) 
		{
			dto = toDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

}
