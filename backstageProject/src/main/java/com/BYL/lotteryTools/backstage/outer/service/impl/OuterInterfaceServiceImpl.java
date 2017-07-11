package com.BYL.lotteryTools.backstage.outer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.repository.LotteryPlayRepository;
import com.BYL.lotteryTools.backstage.outer.controller.OuterInterfaceController;
import com.BYL.lotteryTools.backstage.outer.dto.LotteryPlayOfProvince;
import com.BYL.lotteryTools.backstage.outer.entity.Fast3Analysis;
import com.BYL.lotteryTools.backstage.outer.entity.Fast3WithCycleAnalysis;
import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;
import com.BYL.lotteryTools.backstage.outer.entity.SrcthreedataDTO;
import com.BYL.lotteryTools.backstage.outer.repository.Fast3AnalysisRepository;
import com.BYL.lotteryTools.backstage.outer.repository.Fast3WithCycleAnalysisRepository;
import com.BYL.lotteryTools.backstage.outer.repository.SrcfivedataDTORepository;
import com.BYL.lotteryTools.backstage.outer.repository.SrcthreedataDTORepository;
import com.BYL.lotteryTools.backstage.outer.service.OuterInterfaceService;
import com.BYL.lotteryTools.backstage.user.entity.Province;
import com.BYL.lotteryTools.backstage.user.service.ProvinceService;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("outerInterfaceService")
@Transactional(propagation=Propagation.REQUIRED)
public class OuterInterfaceServiceImpl implements OuterInterfaceService 
{
	@Autowired
	private SrcfivedataDTORepository srcfivedataDTORepository;
	
	@Autowired
	private SrcthreedataDTORepository srcthreedataDTORepository;
	
	@Autowired
	private LotteryPlayRepository lotteryPlayRepository;
	
	@Autowired
	private Fast3AnalysisRepository fast3AnalysisRepository;
	
	@Autowired
	private Fast3WithCycleAnalysisRepository fast3WithCycleAnalysisRepository;
	
	@Autowired
	private ProvinceService provinceService;
	
	public SrcfivedataDTO getSrcfivedataDTOByIssueNumber(String issueNumber)
	{
		SrcfivedataDTO dto = srcfivedataDTORepository.getSrcfivedataDTOByIssueNumber(issueNumber);
		
		return dto;
	}

	public List<SrcfivedataDTO> getLotteryList(String tbName,String maxIssueId, String minIssueId) 
	{
		List<SrcfivedataDTO> list = new ArrayList<SrcfivedataDTO>();
		int limit = 160;
		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ID,ISSUE_NUMBER,NO1,NO2,NO3,NO4,NO5 from analysis."+tbName+" ");
		if(null != maxIssueId &&!"".equals(maxIssueId))
		{
			limit = 40;
			sql.append(" where ISSUE_NUMBER>"+maxIssueId+" order by ISSUE_NUMBER asc");
		}
		else
			if(null != minIssueId &&!"".equals(minIssueId))
			{
				limit = 40;
				sql.append(" where ISSUE_NUMBER<"+minIssueId+" order by ISSUE_NUMBER desc");
			}
			else
			{
				sql.append(" order by ISSUE_NUMBER desc ");
			}
		
		
		
		
		Pageable pageable = new PageRequest(0,limit);
		QueryResult<SrcfivedataDTO> queryResult = srcfivedataDTORepository.
				getScrollDataByGroupBySql(SrcfivedataDTO.class, sql.toString(), null,pageable );
		list = queryResult.getResultList();
		
		return list;
	}
	
	public SrcfivedataDTO getMaxLottery(String tbName,String maxIssueId) 
	{
		List<SrcfivedataDTO> list = new ArrayList<SrcfivedataDTO>();
		SrcfivedataDTO dto = null;
		int limit = 1;
		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ID,ISSUE_NUMBER,NO1,NO2,NO3,NO4,NO5 from analysis."+tbName+" ");
		
		if(null != maxIssueId &&!"".equals(maxIssueId))
		{
			sql.append(" where ISSUE_NUMBER>"+maxIssueId+"  order by ISSUE_NUMBER asc  ");
		}
		else
		{
			sql.append(" order by ISSUE_NUMBER desc ");
		}
		
		
		
		Pageable pageable = new PageRequest(0,limit);
		QueryResult<SrcfivedataDTO> queryResult = srcfivedataDTORepository.
				getScrollDataByGroupBySql(SrcfivedataDTO.class, sql.toString(), null,pageable );
		list = queryResult.getResultList();
		if(null != list &&list.size()>0)
		{
			dto = list.get(0);
		}
		
		return dto;
	}
	
	public SrcthreedataDTO getMaxThreeLottery(String tbName,String maxIssueId) 
	{
		List<SrcthreedataDTO> list = new ArrayList<SrcthreedataDTO>();
		SrcthreedataDTO dto = null;
		int limit = 1;
		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ID,ISSUE_NUMBER,NO1,NO2,NO3 from analysis."+tbName+" ");
		
		if(null != maxIssueId &&!"".equals(maxIssueId))
		{
			sql.append(" where ISSUE_NUMBER>"+maxIssueId+"  order by ISSUE_NUMBER asc  ");
		}
		else
		{
			sql.append(" order by ISSUE_NUMBER desc ");
		}
		
		
		
		Pageable pageable = new PageRequest(0,limit);
		QueryResult<SrcthreedataDTO> queryResult = srcthreedataDTORepository.
				getScrollDataByGroupBySql(SrcthreedataDTO.class, sql.toString(), null,pageable );
		list = queryResult.getResultList();
		if(null != list &&list.size()>0)
		{
			dto = list.get(0);
		}
		
		return dto;
	}

	public List<Province> getLotteryPlayListOfProvince()
	{
		
		List<LotteryPlay> lotteryPlays = lotteryPlayRepository.getLotteryPlayList();
		Set<String> provinceset = new HashSet<String>();
		List<Province> provinceList = new ArrayList<Province>();
		
		for (LotteryPlay lotteryPlay : lotteryPlays)
		{
			provinceset.add(lotteryPlay.getProvince());
		}
		
		Iterator<String> it = provinceset.iterator();
		
		while(it.hasNext())
		{
			Province province = provinceService.getProvinceByPcode(it.next());
			provinceList.add(province);
		}
		
		return provinceList;
	}

	public List<LotteryPlayOfProvince> getLotteryPlayOfProvince(String province) 
	{
		List<LotteryPlay> lotteryPlays = lotteryPlayRepository.getLotteryPlayByProvince(province);
		
		List<LotteryPlayOfProvince> list = new ArrayList<LotteryPlayOfProvince>();
		
		for (LotteryPlay lotteryPlay : lotteryPlays) 
		{
			LotteryPlayOfProvince lotteryPlayOfProvince = new LotteryPlayOfProvince();
			
			lotteryPlayOfProvince.setLotteryPlayId(lotteryPlay.getId());
			lotteryPlayOfProvince.setLotteryPlayName(lotteryPlay.getName());
			lotteryPlayOfProvince.setLotteryType(lotteryPlay.getLotteryType());
			lotteryPlayOfProvince.setNumberLength(lotteryPlay.getLotteryPlayBulufangan().getEndNumber());
			lotteryPlayOfProvince.setProvinceCode(lotteryPlay.getProvince());
			lotteryPlayOfProvince.setLotteryTypeName(lotteryPlay.getLotteryType().equals("1")?"体彩":"福彩");
			lotteryPlayOfProvince.setLineCount(lotteryPlay.getLineCount());
			lotteryPlayOfProvince.setLotteryNumber(lotteryPlay.getLotteryNumber());
			
			list.add(lotteryPlayOfProvince);
		}
		
		return list;
	}

	public List<SrcthreedataDTO> getLotteryListOfThree(String tbName,String maxIssueId, String minIssueId) 
	{
		List<SrcthreedataDTO> list = new ArrayList<SrcthreedataDTO>();
		int limit = 300;
		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ID,ISSUE_NUMBER,NO1,NO2,NO3 from analysis."+tbName+" ");
		if(null != maxIssueId &&!"".equals(maxIssueId))
		{
			limit = 40;
			sql.append(" where ISSUE_NUMBER>"+maxIssueId+" order by ISSUE_NUMBER asc");
		}
		else
			if(null != minIssueId &&!"".equals(minIssueId))
			{
				limit = 40;
				sql.append(" where ISSUE_NUMBER<"+minIssueId+" order by ISSUE_NUMBER desc");
			}
			else
			{
				sql.append(" order by ISSUE_NUMBER desc ");
			}
		
		
		
		
		Pageable pageable = new PageRequest(0,limit);
		QueryResult<SrcthreedataDTO> queryResult = srcthreedataDTORepository.
				getScrollDataByGroupBySql(SrcthreedataDTO.class, sql.toString(), null,pageable );
		list = queryResult.getResultList();
		
		return list;
	}

	public Map<String, Object> getMissAnalysisData(String type,
			String selectnum, String groupnum, String tableName,
			String orderby, String endNumber, String ascOrDesc) {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		List<Fast3Analysis> list = new ArrayList<Fast3Analysis>();
		int lilunzhouqi = 1;//理论周期
		
//		Fast3Analysis fast3Analysis = null;
		StringBuffer execSql = new StringBuffer();
		if(null != groupnum && !"".equals(groupnum))
		{
		}
		
		if("1".equals(type))
		{//前三直
			execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE FROM analysis."
					 + tableName + " WHERE TYPE = 12 ");
			if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
			{
				execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
			}
			execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
			if("12".equals(endNumber))
			{//12选5前三直理论周期
				lilunzhouqi = OuterInterfaceController.FiveInTwe_QianSanZhi;
			}
			else
				if("11".equals(endNumber))
				{//11选5前三直理论周期
					lilunzhouqi = OuterInterfaceController.FiveInEle_QianSanZhi;
				}
		}
		else
			if("2".equals(type))//组选相关
			{
				if(selectnum.equals("3"))
				{//前三组
					execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE FROM analysis."
							 + tableName + " WHERE TYPE = 10 ");
					if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
					{
						execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
					}
					execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
					if("12".equals(endNumber))
					{//12选5前三直理论周期
						lilunzhouqi = OuterInterfaceController.FiveInTwe_QianSanZuXuan;
					}
					else
						if("11".equals(endNumber))
						{//11选5前三直理论周期
							lilunzhouqi = OuterInterfaceController.FiveInEle_QianSanZuXuan;
						}
				}
				else
				{
					execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,THREECODE_COMPOUND AS CURRENT_MISS,THREECODE_COMPOUND_MAXMISS AS MAX_MISS,TYPE FROM analysis."
							 + tableName + " WHERE TYPE = "+selectnum);
					if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
					{
						execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
					}
					execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
					if("4".equals(selectnum))
					{//前三四码
						if("12".equals(endNumber))
						{//12选5前三直理论周期
							lilunzhouqi = OuterInterfaceController.FiveInTwe_QianSanSiMa;
						}
						else
							if("11".equals(endNumber))
							{//11选5前三直理论周期
								lilunzhouqi = OuterInterfaceController.FiveInEle_QianSanSiMa;
							}
					}
					else
						if("5".equals(selectnum))
						{//前三五码
							if("12".equals(endNumber))
							{//12选5前三直理论周期
								lilunzhouqi = OuterInterfaceController.FiveInTwe_QianSanWuMa;
							}
							else
								if("11".equals(endNumber))
								{//11选5前三直理论周期
									lilunzhouqi = OuterInterfaceController.FiveInEle_QianSanWuMa;
								}
						}
						else
							if("6".equals(selectnum))
							{//前三五码
								if("12".equals(endNumber))
								{//12选5前三直理论周期
									lilunzhouqi = OuterInterfaceController.FiveInTwe_QianSanLiuMa;
								}
								else
									if("11".equals(endNumber))
									{//11选5前三直理论周期
										lilunzhouqi = OuterInterfaceController.FiveInEle_QianSanLiuMa;
									}
							}
				}
			}
			else 
				if("3".equals(type))
				{
					//前二值
					execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE FROM analysis."
							 + tableName + " WHERE TYPE = 11 ");
					if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
					{
						execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
					}
					execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
					if("12".equals(endNumber))
					{//12选5前三直理论周期
						lilunzhouqi = OuterInterfaceController.FiveInTwe_QianErZhi;
					}
					else
						if("11".equals(endNumber))
						{//11选5前三直理论周期
							lilunzhouqi = OuterInterfaceController.FiveInEle_QianErZhi;
						}
				}
				else 
					if("4".equals(type))
					{
						//前二组
						if(selectnum.equals("2"))
						{
							execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE FROM analysis."
									 + tableName + " WHERE TYPE = 9 ");
							if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
							{
								execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
							}
							execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
							if("12".equals(endNumber))
							{//12选5前三直理论周期
								lilunzhouqi = OuterInterfaceController.FiveInTwe_QianErZuXuan;
							}
							else
								if("11".equals(endNumber))
								{//11选5前三直理论周期
									lilunzhouqi = OuterInterfaceController.FiveInEle_QianErZuXuan;
								}
						}
						else
						{//前二三码复式，前二四码复式，前二五码复式，前二六码复式，前二七码复式（目前没有理论周期）
							execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,TWOCODE_COMPOUND AS CURRENT_MISS,TWOCODE_COMPOUND_MAXMISS AS MAX_MISS,TYPE FROM analysis."
									 + tableName + " WHERE TYPE = "+selectnum);
							if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
							{
								execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
							}
							execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
							
							if("3".equals(selectnum))
							{//前二三码复式
								if("12".equals(endNumber))
								{//12选5前二三码复式理论周期
									lilunzhouqi = OuterInterfaceController.FiveInTwe_QianErZuXuanSanma;
								}
								else
									if("11".equals(endNumber))
									{//11选5前二三码复式理论周期
										lilunzhouqi = OuterInterfaceController.FiveInEle_QianErZuXuanSanma;
									}
							}
							else
								if("4".equals(selectnum))
								{//前二四码复式
									if("12".equals(endNumber))
									{//12选5前二四码复式理论周期
										lilunzhouqi = OuterInterfaceController.FiveInTwe_QianErZuXuanSima;
									}
									else
										if("11".equals(endNumber))
										{//11选5前二四码复式理论周期
											lilunzhouqi = OuterInterfaceController.FiveInEle_QianErZuXuanSima;
										}
								}
								else
									if("5".equals(selectnum))
									{//前二五码复式
										if("12".equals(endNumber))
										{//12选5前二五码复式理论周期
											lilunzhouqi = OuterInterfaceController.FiveInTwe_QianErZuXuanWuma;
										}
										else
											if("11".equals(endNumber))
											{//11选5前二五码复式理论周期
												lilunzhouqi = OuterInterfaceController.FiveInEle_QianErZuXuanWuma;
											}
									}
									else
										if("6".equals(selectnum))
										{//前二六码复式
											if("12".equals(endNumber))
											{//12选5前二六码复式理论周期
												lilunzhouqi = OuterInterfaceController.FiveInTwe_QianErZuXuanLiuma;
											}
											else
												if("11".equals(endNumber))
												{//11选5前二六码复式理论周期
													lilunzhouqi = OuterInterfaceController.FiveInEle_QianErZuXuanLiuma;
												}
										}
										else
											if("7".equals(selectnum))
											{//前二七码复式
												if("12".equals(endNumber))
												{//12选5前二七码复式理论周期
													lilunzhouqi = OuterInterfaceController.FiveInTwe_QianErZuXuanQima;
												}
												else
													if("11".equals(endNumber))
													{//11选5前二七码复式理论周期
														lilunzhouqi = OuterInterfaceController.FiveInEle_QianErZuXuanQima;
													}
											}
							
						}
					}
					else 
						if("5".equals(type))//任三相关
						{
							if(selectnum.equals("3"))
							{//任三
								execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE FROM analysis."
										 + tableName + " WHERE TYPE = "+selectnum);
								if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
								{
									execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
								}
								execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
								if("12".equals(endNumber))
								{//12选5前三直理论周期
									lilunzhouqi = OuterInterfaceController.FiveInTwe_RenSan;
								}
								else
									if("11".equals(endNumber))
									{//11选5前三直理论周期
										lilunzhouqi = OuterInterfaceController.FiveInEle_RenSan;
									}
							}
							else
							{
								execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,OPTIONAL_COMPOUND AS CURRENT_MISS,OPTIONAL_COMPOUND_MAXMISS AS MAX_MISS,TYPE FROM analysis."
										 + tableName + " WHERE TYPE = "+selectnum);
								if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
								{
									execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
								}
								execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
								if(selectnum.equals("4"))
								{//任三四码
									if("12".equals(endNumber))
									{//12选5前三直理论周期
										lilunzhouqi = OuterInterfaceController.FiveInTwe_RenSanSi;
									}
									else
										if("11".equals(endNumber))
										{//11选5前三直理论周期
											lilunzhouqi = OuterInterfaceController.FiveInEle_RenSanSi;
										}
								}
							}
						}
						else 
							if("6".equals(type))
							{
								if(selectnum.equals("4"))
								{//任四
									execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE FROM analysis."
											 + tableName + " WHERE TYPE = "+selectnum);
									if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
									{
										execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
									}
									execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
									if("12".equals(endNumber))
									{//12选5前三直理论周期
										lilunzhouqi = OuterInterfaceController.FiveInTwe_RenSi;
									}
									else
										if("11".equals(endNumber))
										{//11选5前三直理论周期
											lilunzhouqi = OuterInterfaceController.FiveInEle_RenSi;
										}
								}
								else
								{//任四五码复式，任四六码复式（暂时没有理论周期）
									execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,OPTIONAL_COMPOUND AS CURRENT_MISS,OPTIONAL_COMPOUND_MAXMISS AS MAX_MISS,TYPE FROM analysis."
											 + tableName + " WHERE TYPE = "+selectnum);
									if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
									{
										execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
									}
									execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
									if("5".equals(selectnum))
									{//任四五码复式
										if("12".equals(endNumber))
										{//12选5任四五码复式理论周期
											lilunzhouqi = OuterInterfaceController.FiveInTwe_RenSiWuma;
										}
										else
											if("11".equals(endNumber))
											{//11选5前二四码复式理论周期
												lilunzhouqi = OuterInterfaceController.FiveInEle_RenSiWuma;
											}
									}
									else
										if("6".equals(selectnum))
										{//任四六码复式
											if("12".equals(endNumber))
											{//12选5任四六码复式理论周期
												lilunzhouqi = OuterInterfaceController.FiveInTwe_RenSiLiuma;
											}
											else
												if("11".equals(endNumber))
												{//11选5前二五码复式理论周期
													lilunzhouqi = OuterInterfaceController.FiveInEle_RenSiLiuma;
												}
										}
								}
							}
							else
							{
								execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE FROM analysis."
										 + tableName + " WHERE TYPE = "+selectnum);
								if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
								{
									execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
								}
								execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
								if(selectnum.equals("5"))
								{//任五
									if("12".equals(endNumber))
									{//12选5前三直理论周期
										lilunzhouqi = OuterInterfaceController.FiveInTwe_RenWu;
									}
									else
										if("11".equals(endNumber))
										{//11选5前三直理论周期
											lilunzhouqi = OuterInterfaceController.FiveInEle_RenWu;
										}
								}
								else
									if(selectnum.equals("6"))
									{//任六
										if("12".equals(endNumber))
										{//12选5前三直理论周期
											lilunzhouqi = OuterInterfaceController.FiveInTwe_RenLiu;
										}
										else
											if("11".equals(endNumber))
											{//11选5前三直理论周期
												lilunzhouqi = OuterInterfaceController.FiveInEle_RenLiu;
											}
									}
									else
										if(selectnum.equals("7"))
										{//任七
											if("12".equals(endNumber))
											{//12选5前三直理论周期
												lilunzhouqi = OuterInterfaceController.FiveInTwe_RenQi;
											}
											else
												if("11".equals(endNumber))
												{//11选5前三直理论周期
													lilunzhouqi = OuterInterfaceController.FiveInEle_RenQi;
												}
										}
										else
											if(selectnum.equals("8"))
											{//任八
												if("12".equals(endNumber))
												{//12选5前三直理论周期
													lilunzhouqi = OuterInterfaceController.FiveInTwe_RenBa;
												}
												else
													if("11".equals(endNumber))
													{//11选5前三直理论周期
														lilunzhouqi = OuterInterfaceController.FiveInEle_RenBa;
													}
											}
							}
		Object[] queryParams = new Object[]{
		};
		list = fast3AnalysisRepository.getEntityListBySql(Fast3Analysis.class, execSql.toString(), queryParams);
		returnMap.put("lilunzhouqi", lilunzhouqi);
		returnMap.put("fast3Analysis", list);
		return returnMap;
	}

	/**
	 * 获取快三遗漏
	* @Title: getFast3MissAnalysisData 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param type
	* @param @param selectnum
	* @param @param groupnum
	* @param @param tableName
	* @param @param orderby
	* @param @param endNumber
	* @param @param ascOrDesc
	* @param @return    设定文件 
	* @author banna
	* @date 2017年7月7日 下午4:58:16 
	* @throws
	 */
	public Map<String, Object> getFast3MissAnalysisData(String type,
			String selectnum, String groupnum, String tableName,
			String orderby, String endNumber, String ascOrDesc) 
	{
		Map<String,Object> returnMap = new HashMap<String, Object>();
		List<Fast3WithCycleAnalysis> list = new ArrayList<Fast3WithCycleAnalysis>();
//		double lilunzhouqi = 1;//理论周期
		
		StringBuffer execSql = new StringBuffer();
		if("234".equals(type))
		{//全部--二同+三同+全不同
			execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE,CYCLE FROM analysis."
					 + tableName + " WHERE TYPE in(2,3,4) ");
			if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
			{
				execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
			}
			execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
		}
		else
			if("91012".equals(type))//形态--大小
			{
				execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE,CYCLE FROM analysis."
						 + tableName + " WHERE TYPE in(9,10,11,12) ");
				if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
				{
					execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
				}
				execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
			}
			else
				if("13456".equals(type))//形态--奇偶
				{
					execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE,CYCLE FROM analysis."
							 + tableName + " WHERE TYPE in(13,14,15,16) ");
					if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
					{
						execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
					}
					execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
				}
				else
					if("67".equals(type))//两码--全部
					{
						execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE,CYCLE FROM analysis."
								 + tableName + " WHERE TYPE in(6,7) ");
						if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
						{
							execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
						}
						execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
					}
					else
						if("1789".equals(type))//和值--012路
						{
							execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE,CYCLE FROM analysis."
									 + tableName + " WHERE TYPE in(17,18,19) ");
							if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
							{
								execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
							}
							execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
						}
					else 
					{
						execSql.append("SELECT ID,ISSUE_NUMBER,GROUP_NUMBER,CURRENT_MISS,MAX_MISS,TYPE,CYCLE FROM analysis."
								 + tableName + " WHERE TYPE = "+type);
						if(null != groupnum && !"".equals(groupnum))//如果组合号码不为空，则要查询当前组合号码的遗漏值
						{
							execSql.append(" AND GROUP_NUMBER = '"+groupnum+"'")  ;
						}
						execSql.append(" order by "+orderby+" "+"  "+ascOrDesc+ " ;")  ;
						
					}
		Object[] queryParams = new Object[]{
		};
		list = fast3WithCycleAnalysisRepository.getEntityListBySql(Fast3WithCycleAnalysis.class, execSql.toString(), queryParams);
//		returnMap.put("lilunzhouqi", lilunzhouqi);
		returnMap.put("fast3Analysis", list);
		return returnMap;
	}

	public String getKjNumber(String lotteryType, String province,
			String lotteryNumber, String issueNumber) {

		StringBuffer kjStr = new StringBuffer();
		String execSql = "";
		LotteryPlay lotteryPlay = lotteryPlayRepository.
				getLotteryPlayByProvinceAndLotteryTypeAndLotteryNumber(province, lotteryType, lotteryNumber);
		if("5".equals(lotteryPlay.getLotteryNumber()))
		{
			execSql = "SELECT ID,ISSUE_NUMBER,NO1,NO2,NO3,NO4,NO5 "
					+ " FROM analysis."+lotteryPlay.getCorrespondingTable() +"   WHERE ISSUE_NUMBER = ? ";
			Object[] queryParams = new Object[]{
					issueNumber
			};
			
			List<SrcfivedataDTO> dtoFives = srcfivedataDTORepository.getEntityListBySql(SrcfivedataDTO.class, execSql, queryParams);
			if(null != dtoFives && dtoFives.size() != 0)
			{
				SrcfivedataDTO dtoFive = dtoFives.get(0);
				if(null != dtoFive && null != dtoFive.getIssueNumber() && !"".equals(dtoFive.getIssueNumber()))
				{
					kjStr.append(dtoFive.getNo1()).append(",")
						 .append(dtoFive.getNo2()).append(",")
						 .append(dtoFive.getNo3()).append(",")
						 .append(dtoFive.getNo4()).append(",")
						 .append(dtoFive.getNo5());
				}
			}
			
			
		}
		else
			if("3".equals(lotteryPlay.getLotteryNumber()))
			{
				execSql = "SELECT ID,ISSUE_NUMBER,NO1,NO2,NO3 "
						+ " FROM analysis."+lotteryPlay.getCorrespondingTable() +"  WHERE ISSUE_NUMBER = ? LIMIT 1 ";
				Object[] queryParams = new Object[]{
						issueNumber
				};
				
				List<SrcthreedataDTO> dtoThrees = srcthreedataDTORepository.getEntityListBySql(SrcthreedataDTO.class, execSql, queryParams);
				if(null != dtoThrees&& dtoThrees.size() != 0)
				{
					SrcthreedataDTO dtoThree = dtoThrees.get(0);
					if(null != dtoThree && null != dtoThree.getIssueNumber() && !"".equals(dtoThree.getIssueNumber()))
					{
						kjStr.append(dtoThree.getNo1()).append(",")
							 .append(dtoThree.getNo2()).append(",")
							 .append(dtoThree.getNo3());
					}
				}
				
				
			}
		
		return kjStr.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
