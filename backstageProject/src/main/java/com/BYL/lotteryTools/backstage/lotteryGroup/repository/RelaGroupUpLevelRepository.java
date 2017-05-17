package com.BYL.lotteryTools.backstage.lotteryGroup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaGroupUpLevelRecord;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface RelaGroupUpLevelRepository extends GenericRepository<RelaGroupUpLevelRecord, String> {

	@Query("select u from RelaGroupUpLevelRecord u where u.isDeleted = '1'  and u.lotteryGroup.id = ?1 ")
	public List<RelaGroupUpLevelRecord> getRelaGroupUpLevelRecordByGroupId(String groupId);
}
