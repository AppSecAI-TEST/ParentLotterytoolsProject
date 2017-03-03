package com.BYL.lotteryTools.backstage.authority.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.authority.entity.Authority;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface AuthorityRepository extends GenericRepository<Authority, String> 
{
	@Query("select u from Authority u where u.isDeleted ='1' and u.id =?1")
	public Authority getAuthorityById(String id);
}
