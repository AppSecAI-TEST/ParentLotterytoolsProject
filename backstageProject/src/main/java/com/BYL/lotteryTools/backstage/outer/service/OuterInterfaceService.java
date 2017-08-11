package com.BYL.lotteryTools.backstage.outer.service;

import java.util.List;
import java.util.Map;

import com.BYL.lotteryTools.backstage.outer.dto.LotteryPlayOfProvince;
import com.BYL.lotteryTools.backstage.outer.entity.HappyTenOfHLJDTO;
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

	public String getKjNumber(String lotteryType,String province,String lotteryNumber,String issueNumber,String lotteryPlayId);
	
	/**
	 * 获取最新一期开奖号码
	* @Title: getNewtesKjNum 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param lotteryType
	* @param @param lotteryNumber
	* @param @param provinceCode
	* @param @return    设定文件 
	* @author banna
	* @date 2017年7月12日 下午1:14:46 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public Map<String,Object> getNewtesKjNum(String lotteryType,String lotteryNumber,String provinceCode);
	
	public Map<String,Object> getMaxIssueOfMissAnalysisData(String tbName);
	
	//快乐十分相关数据接口
	public List<HappyTenOfHLJDTO> getHLJHappy10Data(String tbName,String maxIssueId, String minIssueId);
	//快乐十分相关数据接口
	public HappyTenOfHLJDTO getMaxHappy10Lottery(String tbName,String maxIssueId);
}
