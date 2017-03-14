package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface LotterybuyerOrExpertRepository extends
		GenericRepository<LotterybuyerOrExpert, String> 
{
	@Query("select u from LotterybuyerOrExpert u where u.isDeleted = 1 and u.id = ?1")
	public LotterybuyerOrExpert getLotterybuyerOrExpertById(String id);
}
