package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaBindOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.RelaBindOfBuyyerorexpertAndGroupRespository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaBindbuyerAndGroupService;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("relaBindbuyerAndGroupService")
@Transactional(propagation=Propagation.REQUIRED)
public class RelaBindbuyerAndGroupServiceImpl implements
		RelaBindbuyerAndGroupService {

	@Autowired
	private RelaBindOfBuyyerorexpertAndGroupRespository relaBindOfBuyyerorexpertAndGroupRespository;
	
	public void save(RelaBindOfLbuyerorexpertAndGroup entity)
	{
		relaBindOfBuyyerorexpertAndGroupRespository.save(entity);
	}
	
	public void update(RelaBindOfLbuyerorexpertAndGroup entity)
	{
		relaBindOfBuyyerorexpertAndGroupRespository.save(entity);
	}

	public RelaBindOfLbuyerorexpertAndGroup getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(
			String userId, String groupId) {
		return relaBindOfBuyyerorexpertAndGroupRespository.getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(userId, groupId);
	}
	
	//获取当前用户加入的群
	public List<RelaBindOfLbuyerorexpertAndGroup> getRelaList(String userId) 
	{
		List<RelaBindOfLbuyerorexpertAndGroup> list = null;
		
		Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer("SELECT u.* FROM RELA_BIND_OF_BUYER_AND_LOTTERYGROUP u WHERE u.IS_DELETED='1' "
				+ "AND u.LBOE_ID='"+userId+"' ORDER BY u.IS_TOP DESC ,u.MODIFY_TIME DESC ");
		
		
		QueryResult<RelaBindOfLbuyerorexpertAndGroup> queryResult = relaBindOfBuyyerorexpertAndGroupRespository.
				getScrollDataBySql(RelaBindOfLbuyerorexpertAndGroup.class, sql.toString(), params.toArray(), pageable);
		
		list = queryResult.getResultList();
		
		return list;
	}
	
	
	public QueryResult<RelaBindOfLbuyerorexpertAndGroup> getMemberOfJoinGroup(Pageable pageable,String groupId) 
	{
//		Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer("SELECT u.* "
				+ " FROM RELA_BIND_OF_BUYER_AND_LOTTERYGROUP u WHERE u.IS_DELETED='1' "
				+ " AND u.GROUP_ID='"+groupId+"' ORDER BY u.CREATE_TIME DESC ");
		
		
		QueryResult<RelaBindOfLbuyerorexpertAndGroup> queryResult = relaBindOfBuyyerorexpertAndGroupRespository.
				getScrollDataBySql(RelaBindOfLbuyerorexpertAndGroup.class, sql.toString(), params.toArray(), pageable);
		
//		list = queryResult.getResultList();
		
		return queryResult;
	}

	public void delete(RelaBindOfLbuyerorexpertAndGroup entity)
	{
		relaBindOfBuyyerorexpertAndGroupRespository.delete(entity);
	}
}
