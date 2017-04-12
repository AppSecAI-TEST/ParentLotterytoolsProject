package com.BYL.lotteryTools.backstage.outer.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.repository.LotteryPlayRepository;
import com.BYL.lotteryTools.backstage.outer.dto.LotteryPlayOfProvince;
import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;
import com.BYL.lotteryTools.backstage.outer.repository.SrcfivedataDTORepository;
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
	private LotteryPlayRepository lotteryPlayRepository;
	
	@Autowired
	private ProvinceService provinceService;

	public List<SrcfivedataDTO> getLotteryList(String tbName,String maxIssueId, String minIssueId) 
	{
		List<SrcfivedataDTO> list = new ArrayList<SrcfivedataDTO>();
		int limit = 300;
		
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ID,ISSUE_NUMBER,NO1,NO2,NO3,NO4,NO5 from "+tbName+" ");
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
			
			list.add(lotteryPlayOfProvince);
		}
		
		return list;
	}
	
	
	
}
