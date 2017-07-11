package com.BYL.lotteryTools.backstage.outer.service;

import java.util.List;
import java.util.Map;

import com.BYL.lotteryTools.backstage.outer.dto.LotteryPlayOfProvince;
import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;
import com.BYL.lotteryTools.backstage.outer.entity.SrcthreedataDTO;
import com.BYL.lotteryTools.backstage.user.entity.Province;

public interface OuterInterfaceService 
{
	public List<SrcfivedataDTO> getLotteryList(String tbName,String maxIssueId,String minIssueId);
	
	public List<Province> getLotteryPlayListOfProvince();
	
	public List<LotteryPlayOfProvince> getLotteryPlayOfProvince(String province);
	
	public SrcfivedataDTO getMaxLottery(String tbName,String maxIssueId) ;
	
	public SrcthreedataDTO getMaxThreeLottery(String tbName,String maxIssueId) ;
	
	public SrcfivedataDTO getSrcfivedataDTOByIssueNumber(String issueNumber);
	
	public List<SrcthreedataDTO> getLotteryListOfThree(String tbName,String maxIssueId,String minIssueId);

	public Map<String,Object> getMissAnalysisData(String type,String selectnum,String groupnum,String tableName,String orderby,String endNumber,String ascOrDesc);
	
	public Map<String,Object> getFast3MissAnalysisData(String type,String selectnum,String groupnum,String tableName,String orderby,String endNumber,String ascOrDesc);

	public String getKjNumber(String lotteryType,String province,String lotteryNumber,String issueNumber);
}
