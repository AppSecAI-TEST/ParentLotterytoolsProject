package com.BYL.lotteryTools.backstage.role.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.role.entity.Role;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface RoleRepository extends GenericRepository<Role, String> {
	
	@Query("select u from Role u where u.isDeleted='1' and u.id =?1 ")
	public Role getRoleById(String code);
}
