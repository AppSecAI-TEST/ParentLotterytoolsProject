package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.SysMessage;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.SysMessageRepository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.SysMessageService;

@Service("sysMessageService")
@Transactional(propagation=Propagation.REQUIRED)
public class SysMessageServiceImpl implements SysMessageService {

	@Autowired
	private SysMessageRepository sysMessageRepository;
	
	public List<SysMessage> getSysMessageByTarget(String target)
	{
		return sysMessageRepository.getSysMessageByTarget(target);
	}

	public void save(SysMessage entity) {
		sysMessageRepository.save(entity);
	}

	public void update(SysMessage entity) {
		sysMessageRepository.save(entity);
		
	}
}
