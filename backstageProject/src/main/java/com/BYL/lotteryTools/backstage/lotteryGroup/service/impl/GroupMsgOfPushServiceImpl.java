package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.GroupMsgOfPushDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.GroupMsgOfPush;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.GroupMsgOfPushRepository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.GroupMsgOfPushService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("groupMsgOfPushService")
@Transactional(propagation=Propagation.REQUIRED)
public class GroupMsgOfPushServiceImpl implements GroupMsgOfPushService {

	@Autowired
	private GroupMsgOfPushRepository groupMsgOfPushRepository;

	public void save(GroupMsgOfPush entity) {
		groupMsgOfPushRepository.save(entity);
	}

	public void update(GroupMsgOfPush entity) {
		groupMsgOfPushRepository.save(entity);
		
	}

	public GroupMsgOfPushDTO toDTO(GroupMsgOfPush entity) {
		
		GroupMsgOfPushDTO dto = new GroupMsgOfPushDTO();
		
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTimeStr(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		return dto;
	}

	public List<GroupMsgOfPushDTO> toDTO(List<GroupMsgOfPush> entities) {
		
		List<GroupMsgOfPushDTO> dtos = new ArrayList<GroupMsgOfPushDTO>();
		
		if(null != entities)
		{
			GroupMsgOfPushDTO dto = new GroupMsgOfPushDTO();
			for (GroupMsgOfPush entity : entities) {
				dto = this.toDTO(entity);
				dtos.add(dto);
			}
		}
		
		return dtos;
	}

	public QueryResult<GroupMsgOfPush> getGroupMsgOfPushList(
			GroupMsgOfPushDTO dto,int page,int rows) {
		
		Pageable pageable = new PageRequest(page-1,rows);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		if(null != dto.getType())
		{
			params.add(dto.getType());
			buffer.append(" and type = ?").append(params.size());
		}
		
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("createTime", "desc");
		
		QueryResult<GroupMsgOfPush> queryResult  = groupMsgOfPushRepository.
				getScrollDataByJpql(GroupMsgOfPush.class, buffer.toString(), params.toArray(),orderBy, pageable);
		
		return queryResult;
	}
	
	
	
}
