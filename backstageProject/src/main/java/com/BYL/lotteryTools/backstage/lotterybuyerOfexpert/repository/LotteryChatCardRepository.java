package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotteryChatCard;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface LotteryChatCardRepository extends GenericRepository<LotteryChatCard, String> 
{
	@Query("select u from LotteryChatCard u where u.isDeleted = 1 and u.id = ?1")
	public LotteryChatCard getLotteryChatCardById(String id);
	
	
}
