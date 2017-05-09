package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.LotteryGroupDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.LotteryGroupRespository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lotteryGroupService")
@Transactional(propagation=Propagation.REQUIRED)
public class LotteryGroupServiceImpl implements LotteryGroupService 
{
	@Autowired
	private LotteryGroupRespository lotteryGroupRespository;
	
	@Autowired
	private UploadfileService uploadfileService;
	
	public List<LotteryGroup> findAll()
	{
		return lotteryGroupRespository.findAll();
	}
	
	public void save(LotteryGroup entity)
	{
		lotteryGroupRespository.save(entity);
	}
	
	public void update(LotteryGroup entity)
	{
		lotteryGroupRespository.save(entity);
	}
	
	public QueryResult<LotteryGroup> getLotteryGroupList(
			Class<LotteryGroup> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {
		
		QueryResult<LotteryGroup> queryResult = lotteryGroupRespository.getScrollDataByJpql
				(LotteryGroup.class, whereJpql, queryParams, orderby, pageable);
		
		return queryResult;
	}
	
	public LotteryGroupDTO toDTO(LotteryGroup entity) {
		
		LotteryGroupDTO dto = new LotteryGroupDTO();
		
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTimeStr(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
				}
				
				if(null != entity.getTouXiang()&&!"".equals(entity.getTouXiang()))
				{
					Uploadfile touxiangImg = uploadfileService.getUploadfileByNewsUuid(entity.getTouXiang());
					if(null != touxiangImg)
					{
						dto.setTouXiang(touxiangImg.getNewsUuid());
						dto.setTouXiangImgUrl(touxiangImg.getUploadfilepath()+touxiangImg.getUploadRealName());
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<LotteryGroupDTO> toDTOs(
			List<LotteryGroup> entities) {
		
		List<LotteryGroupDTO> dtos = new ArrayList<LotteryGroupDTO>();
		
		for (LotteryGroup entity : entities) 
		{
			LotteryGroupDTO dto = new LotteryGroupDTO();
			
			dto = toDTO(entity);
			
			dtos.add(dto);
		}
		
		return dtos;
	}

	public List<LotteryGroup> getLotteryGroupByGroupRobotID(String groupRobotID) 
	{
		return lotteryGroupRespository.getLotteryGroupByGroupRobotID(groupRobotID);
	}
	
	public LotteryGroup getLotteryGroupById(String id)
	{
		return lotteryGroupRespository.getLotteryGroupById(id);
	}

	public LotteryGroup getLotteryGroupByGroupNumber(String groupNumber) {
		return lotteryGroupRespository.getLotteryGroupByGroupNumber(groupNumber);
	}
	
	
}
