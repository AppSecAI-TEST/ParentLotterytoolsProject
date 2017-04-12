package com.BYL.lotteryTools.backstage.outer.service;

import java.util.List;

import com.BYL.lotteryTools.backstage.outer.dto.LotteryPlayOfProvince;
import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;
import com.BYL.lotteryTools.backstage.user.entity.Province;

public interface OuterInterfaceService 
{
	public List<SrcfivedataDTO> getLotteryList(String tbName,String maxIssueId,String minIssueId);
	
	public List<Province> getLotteryPlayListOfProvince();
	
	public List<LotteryPlayOfProvince> getLotteryPlayOfProvince(String province);
}
