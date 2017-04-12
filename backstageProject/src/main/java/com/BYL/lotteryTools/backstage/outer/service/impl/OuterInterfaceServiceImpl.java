package com.BYL.lotteryTools.backstage.outer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.outer.entity.SrcfivedataDTO;
import com.BYL.lotteryTools.backstage.outer.repository.SrcfivedataDTORepository;
import com.BYL.lotteryTools.backstage.outer.service.OuterInterfaceService;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("outerInterfaceService")
@Transactional(propagation=Propagation.REQUIRED)
public class OuterInterfaceServiceImpl implements OuterInterfaceService 
{
	@Autowired
	private SrcfivedataDTORepository srcfivedataDTORepository;

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
	
	
	
}
