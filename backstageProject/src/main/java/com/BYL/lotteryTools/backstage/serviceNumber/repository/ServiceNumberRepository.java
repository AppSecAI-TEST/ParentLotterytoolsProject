package com.BYL.lotteryTools.backstage.serviceNumber.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.serviceNumber.entity.ServiceNumber;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface ServiceNumberRepository extends GenericRepository<ServiceNumber, String> {

	@Query("select u from ServiceNumber u where u.isDeleted = 1 and u.id = ?1")
	public ServiceNumber getServiceNumberById(String id);
}
