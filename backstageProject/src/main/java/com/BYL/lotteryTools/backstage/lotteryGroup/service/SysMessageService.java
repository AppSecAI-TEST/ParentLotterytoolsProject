package com.BYL.lotteryTools.backstage.lotteryGroup.service;

import java.util.List;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.SysMessage;

public interface SysMessageService {
	public List<SysMessage> getSysMessageByTarget(String target);
	
	public void save(SysMessage entity);
	
	public void update(SysMessage entity);
}
