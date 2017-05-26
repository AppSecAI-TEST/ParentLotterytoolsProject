package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.RelaLBEUserAndLtcard;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface RelaLBEUserAndLtcardRepository extends GenericRepository<RelaLBEUserAndLtcard, String> 
{
	@Query("select u from RelaLBEUserAndLtcard u where u.isDeleted = 1 and u.id = ?1")
	public RelaLBEUserAndLtcard getRelaLBEUserAndLtcardById(String id);
	
	@Query("select u from RelaLBEUserAndLtcard u where u.isDeleted = 1 and u.lotterybuyerOrExpert.id =?1 and u.lotteryChatCard.id =?2")
	public RelaLBEUserAndLtcard getRelaLBEUserAndLtcardByUserIdAndCardId(String userId,String cardId);
}
