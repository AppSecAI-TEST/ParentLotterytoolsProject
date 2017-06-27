package com.BYL.lotteryTools.backstage.lotteryStation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface LotteryStationRepository extends GenericRepository<LotteryStation, String>
{
	@Query("select u from LotteryStation u where  u.isDeleted = 1 and u.id=?1")
	public LotteryStation getLotteryStationById(String id);
	
	@Query("select u from LotteryStation u where  u.isDeleted = 1 and u.stationNumber=?1")
	public LotteryStation getLotteryStationByStationNumber(String stationNumber);
	
	@Query("select u from LotteryStation u where  u.isDeleted = 1 and u.lotteryBuyerOrExpert.id=?1 order by u.status desc")
	public List<LotteryStation> getLotteryStationByUserId(String userId);
	
	@Query("select u from LotteryStation u where  u.isDeleted = 1 and u.inviteCode=?1")
	public LotteryStation getLotteryStationByInviteCode(String inviteCode);
}
