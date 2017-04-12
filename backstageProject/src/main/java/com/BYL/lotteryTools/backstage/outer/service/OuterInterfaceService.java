package com.BYL.lotteryTools.backstage.outer.service;

import java.util.List;

import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;

public interface OuterInterfaceService 
{
	public List<SrcfivedataDTO> getLotteryList(String tbName,String maxIssueId,String minIssueId);
}
