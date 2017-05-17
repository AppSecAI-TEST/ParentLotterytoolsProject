package com.BYL.lotteryTools.backstage.lotteryGroup.service;

import java.util.List;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaGroupUpLevelRecord;

public interface RelaGroupUpLevelService {

	
	public void save(RelaGroupUpLevelRecord entity);
	
	public void update(RelaGroupUpLevelRecord entity);
	
	public List<RelaGroupUpLevelRecord> getRelaGroupUpLevelRecordByGroupId(String groupId);
	
	public void delete(RelaGroupUpLevelRecord entity);
}
