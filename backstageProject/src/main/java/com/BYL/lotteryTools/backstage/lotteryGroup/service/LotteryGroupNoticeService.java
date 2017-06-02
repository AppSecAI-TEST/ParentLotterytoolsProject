package com.BYL.lotteryTools.backstage.lotteryGroup.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.LotteryGroupNoticeDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroupNotice;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface LotteryGroupNoticeService {

	
	public LotteryGroupNotice getLotteryGroupNoticeByID(String id);
	
	public List<LotteryGroupNotice> getLotteryGroupNoticeByGroupId(String status,String groupId);
	
	public void save(LotteryGroupNotice entity);
	
	public void update(LotteryGroupNotice entity);
	
	public LotteryGroupNoticeDTO toDTO(LotteryGroupNotice entity);
	
	public List<LotteryGroupNoticeDTO> toDTOs(List<LotteryGroupNotice> entites);
	
	public QueryResult<LotteryGroupNotice> getLotteryGroupNoticeList(
			Class<LotteryGroupNotice> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable);
}
