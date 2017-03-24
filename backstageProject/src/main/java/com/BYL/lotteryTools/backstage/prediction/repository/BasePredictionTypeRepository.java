package com.BYL.lotteryTools.backstage.prediction.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.prediction.entity.BasePredictionType;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface BasePredictionTypeRepository extends GenericRepository<BasePredictionType, String> {

	@Query("select u from BasePredictionType u where u.isDeleted=1 and   u.id =?1")
	public BasePredictionType getBasePredictionTypeById(String id);
}
