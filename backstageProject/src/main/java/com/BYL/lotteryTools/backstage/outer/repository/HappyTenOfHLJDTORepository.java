package com.BYL.lotteryTools.backstage.outer.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.outer.entity.HappyTenOfHLJDTO;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface HappyTenOfHLJDTORepository extends GenericRepository<HappyTenOfHLJDTO, String> {

	@Query("select u from HappyTenOfHLJDTO u where  u.issueNumber =?1")
	public HappyTenOfHLJDTO getHappyTenOfHLJDTOByIssueNumber(String issueNumber);
	
}
