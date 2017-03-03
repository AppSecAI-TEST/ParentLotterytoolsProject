package com.BYL.lotteryTools.backstage.user.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.user.entity.User;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface UserRepository extends GenericRepository<User, String>{

	@Query("select u from User u where u.isDeleted='1' and u.code =?1 ")
	public User getUserByCode(String code);
	
	@Query("select u from User u where u.isDeleted='1' and u.id =?1 ")
	public User getUserById(String id);
}
