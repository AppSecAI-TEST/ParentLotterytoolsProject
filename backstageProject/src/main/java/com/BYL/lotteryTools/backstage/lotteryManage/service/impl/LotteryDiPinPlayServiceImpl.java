package com.BYL.lotteryTools.backstage.lotteryManage.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryDiPinPlayDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryDiPinPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.repository.LotteryDiPinPlayPlanRepository;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryDiPinPlayService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lotteryDiPinPlayService")
@Transactional(propagation=Propagation.REQUIRED)
public class LotteryDiPinPlayServiceImpl implements LotteryDiPinPlayService {

	@Autowired
	private LotteryDiPinPlayPlanRepository lotteryDiPinPlayPlanRepository;

	public void save(LotteryDiPinPlay entity) {
		lotteryDiPinPlayPlanRepository.save(entity);		
	}

	public void update(LotteryDiPinPlay entity) {
		lotteryDiPinPlayPlanRepository.save(entity);		
	}

	public LotteryDiPinPlay getLotteryDiPinPlayById(String id) {
		return lotteryDiPinPlayPlanRepository.getLotteryDiPinPlayById(id);
	}

	public QueryResult<LotteryDiPinPlay> getLotteryDiPinPlayList(
			LotteryDiPinPlayDTO dto, int rows, int page) {
		
		Pageable pageable = new PageRequest(page-1,rows);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		
	 	
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("createTime", "desc");
		
		QueryResult<LotteryDiPinPlay> queryResult = lotteryDiPinPlayPlanRepository.
				getScrollDataByJpql(LotteryDiPinPlay.class,buffer.toString(), params.toArray(),orderBy, pageable);
		return queryResult;
	}

	public LotteryDiPinPlayDTO toDTO(LotteryDiPinPlay entity) {
		
		LotteryDiPinPlayDTO dto = new LotteryDiPinPlayDTO();
		
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTimeStr(DateUtil.formatDate
							(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT	));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<LotteryDiPinPlayDTO> toDTOs(List<LotteryDiPinPlay> entities) {
		
		List<LotteryDiPinPlayDTO> dtos = new ArrayList<LotteryDiPinPlayDTO>();
		
		if(null != entities)
		{
			for (LotteryDiPinPlay entity : entities) {
				LotteryDiPinPlayDTO dto = this.toDTO(entity);
				dtos.add(dto);
			}
		}
		
		
		return dtos;
	}
	
	
	
	
	
}
