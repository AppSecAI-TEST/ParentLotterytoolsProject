package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface LotterybuyerOrExpertRepository extends
		GenericRepository<LotterybuyerOrExpert, String> 
{
	@Query("select u from LotterybuyerOrExpert u where u.isDeleted = 1 and u.id = ?1")
	public LotterybuyerOrExpert getLotterybuyerOrExpertById(String id);
	
	@Query("select u from LotterybuyerOrExpert u where u.isDeleted = 1 and u.telephone = ?1")
	public LotterybuyerOrExpert getLotterybuyerOrExpertByTelephone(String telephone);
	
	@Query("select u from LotterybuyerOrExpert u where u.isDeleted = 1 and u.cailiaoName = ?1")
	public List<LotterybuyerOrExpert> getLotterybuyerOrExpertByCailiaoName(String cailiaoName);
}
