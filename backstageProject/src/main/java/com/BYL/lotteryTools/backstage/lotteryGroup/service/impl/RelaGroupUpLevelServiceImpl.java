package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaGroupUpLevelRecord;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.RelaGroupUpLevelRepository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaGroupUpLevelService;

@Service("relaGroupLevelService")
@Transactional(propagation=Propagation.REQUIRED)
public class RelaGroupUpLevelServiceImpl implements RelaGroupUpLevelService {

	@Autowired
	private RelaGroupUpLevelRepository relaGroupUpLevelRepository;
	
	public void save(RelaGroupUpLevelRecord entity)
	{
		relaGroupUpLevelRepository.save(entity);
	}
	
	public void update(RelaGroupUpLevelRecord entity)
	{
		relaGroupUpLevelRepository.save(entity);
	}

	public List<RelaGroupUpLevelRecord> getRelaGroupUpLevelRecordByGroupId(
			String groupId) {
		return relaGroupUpLevelRepository.getRelaGroupUpLevelRecordByGroupId(groupId);
	}

	public void delete(RelaGroupUpLevelRecord entity) {
		relaGroupUpLevelRepository.delete(entity);		
	}

	
}
