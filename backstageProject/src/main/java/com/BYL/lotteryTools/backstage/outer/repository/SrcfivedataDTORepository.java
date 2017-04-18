package com.BYL.lotteryTools.backstage.outer.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface SrcfivedataDTORepository extends GenericRepository<SrcfivedataDTO, String> {

	@Query("select u from SrcfivedataDTO u where  u.issueNumber =?1")
	public SrcfivedataDTO getSrcfivedataDTOByIssueNumber(String issueNumber);
	
}
