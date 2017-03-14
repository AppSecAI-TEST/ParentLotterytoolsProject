package com.BYL.lotteryTools.backstage.lotteryStation.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface LotteryStationRepository extends GenericRepository<LotteryStation, String>
{
	@Query("select u from LotteryStation u where  u.isDeleted = 1 and u.id=?1")
	public LotteryStation getLotteryStationById(String id);
}
