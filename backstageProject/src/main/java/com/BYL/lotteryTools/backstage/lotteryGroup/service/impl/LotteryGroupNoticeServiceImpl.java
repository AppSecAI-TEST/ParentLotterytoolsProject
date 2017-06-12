package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.LotteryGroupNoticeDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroupNotice;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.LotteryGroupNoticeRepository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupNoticeService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lotteryGroupNoticeService")
@Transactional(propagation=Propagation.REQUIRED)
public class LotteryGroupNoticeServiceImpl implements LotteryGroupNoticeService {

	@Autowired
	private LotteryGroupNoticeRepository lotteryGroupNoticeRepository;
	
	@Autowired
	private LotteryGroupService lotteryGroupService;
	
	@Autowired
	private UploadfileService uploadfileService;

	public LotteryGroupNotice getLotteryGroupNoticeByID(String id) {
		return lotteryGroupNoticeRepository.getLotteryGroupNoticeByID(id);
	}

	public List<LotteryGroupNotice> getLotteryGroupNoticeByGroupId(
			String status,String groupId) {
		return lotteryGroupNoticeRepository.getLotteryGroupNoticeByStatusAndGroupId(status,groupId);
	}

	public void save(LotteryGroupNotice entity) {
		lotteryGroupNoticeRepository.save(entity);		
	}

	public void update(LotteryGroupNotice entity) {
		lotteryGroupNoticeRepository.save(entity);	
		
	}

	public LotteryGroupNoticeDTO toDTO(LotteryGroupNotice entity) {
		
		LotteryGroupNoticeDTO dto = new LotteryGroupNoticeDTO();
		
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getLotteryGroup())
				{
					dto.setGroupId(entity.getLotteryGroup().getId());
					dto.setGroupName(entity.getLotteryGroup().getName());
				}
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTime(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
				}
				
				if(null == entity.getStatus())
				{
					dto.setStatus("999");
				}
				
				if(null != entity.getLotteryGroup())
				{
					Uploadfile touxiangImg = uploadfileService.getUploadfileByNewsUuid(entity.getLotteryGroup().getTouXiang());
					if(null != touxiangImg)
					{
						dto.setGroupImgUrl(touxiangImg.getUploadfilepath()+touxiangImg.getUploadRealName());
					}
					
					dto.setGroupOwner(entity.getLotteryGroup().getLotteryBuyerOrExpert().getName());
					
				}
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<LotteryGroupNoticeDTO> toDTOs(List<LotteryGroupNotice> entites) {
		
		List<LotteryGroupNoticeDTO> dtos = new ArrayList<LotteryGroupNoticeDTO>();
		
		if(null != entites)
		{
			for (LotteryGroupNotice entity : entites) {
				LotteryGroupNoticeDTO dto = this.toDTO(entity);
				dtos.add(dto);
			}
		}
		
		return dtos;
	}

	public QueryResult<LotteryGroupNotice> getLotteryGroupNoticeList(
			Class<LotteryGroupNotice> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {
		QueryResult<LotteryGroupNotice> queryResult = lotteryGroupNoticeRepository.
				getScrollDataByJpql(LotteryGroupNotice.class, whereJpql, queryParams, orderby, pageable);
		return queryResult;
	}
}
