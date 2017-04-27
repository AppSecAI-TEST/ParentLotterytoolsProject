package com.BYL.lotteryTools.backstage.outer.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.outer.entity.SrcthreedataDTO;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface SrcthreedataDTORepository extends GenericRepository<SrcthreedataDTO, String> {

	@Query("select u from SrcthreedataDTO u where  u.issueNumber =?1")
	public SrcthreedataDTO getSrcthreedataDTOByIssueNumber(String issueNumber);
	
}
