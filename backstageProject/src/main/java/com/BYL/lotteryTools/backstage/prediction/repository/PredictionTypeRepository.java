package com.BYL.lotteryTools.backstage.prediction.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.prediction.entity.PredictionType;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface PredictionTypeRepository extends GenericRepository<PredictionType, String> {

	@Query("select u from PredictionType u where u.isDeleted=1 and   u.id =?1")
	public PredictionType getPredictionTypeById(String id);
}
