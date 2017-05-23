package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.RelaApplyOfLbuyerorexpertAndGroupDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaApplyOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.RelaApplyOfBuyyerorexpertAndGroupRespository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaApplybuyerAndGroupService;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;

@Service("relaApplybuyerAndGroupService")
@Transactional(propagation=Propagation.REQUIRED)
public class RelaApplybuyerAndGroupServiceImpl implements RelaApplybuyerAndGroupService{
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	private Logger logger = LoggerFactory.getLogger(RelaApplybuyerAndGroupService.class);

	@Autowired
	private RelaApplyOfBuyyerorexpertAndGroupRespository relaApplyOfBuyyerorexpertAndGroupRespository;
	
	public void save(RelaApplyOfLbuyerorexpertAndGroup entity) {
		relaApplyOfBuyyerorexpertAndGroupRespository.save(entity);
	}

	public void update(RelaApplyOfLbuyerorexpertAndGroup entity) {
		relaApplyOfBuyyerorexpertAndGroupRespository.save(entity);
		
	}

	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByUserIdAndGroupId(
			String userId, String groupId) {
		return relaApplyOfBuyyerorexpertAndGroupRespository.getRelaApplyOfLbuyerorexpertAndGroupByUserIdAndGroupId(userId, groupId);
	}

	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByApprovalUser(
			String approvalUser) {
		return relaApplyOfBuyyerorexpertAndGroupRespository.getRelaApplyOfLbuyerorexpertAndGroupByApprovalUser(approvalUser);
	}

	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByCreator(
			String creator) {
		return relaApplyOfBuyyerorexpertAndGroupRespository.getRelaApplyOfLbuyerorexpertAndGroupByCreator(creator);
	}

	public List<RelaApplyOfLbuyerorexpertAndGroupDTO> toDTOS(
			List<RelaApplyOfLbuyerorexpertAndGroup> entities) {
		
		List<RelaApplyOfLbuyerorexpertAndGroupDTO> dtos = new ArrayList<RelaApplyOfLbuyerorexpertAndGroupDTO>();
		if(null != entities)
		{
			for (RelaApplyOfLbuyerorexpertAndGroup entity : entities) 
			{
				RelaApplyOfLbuyerorexpertAndGroupDTO dto = this.toDTO(entity);
				dtos.add(dto);
			}
		}
		
		return dtos;
	}

	public RelaApplyOfLbuyerorexpertAndGroupDTO toDTO(
			RelaApplyOfLbuyerorexpertAndGroup entity) {
		
		RelaApplyOfLbuyerorexpertAndGroupDTO dto = new RelaApplyOfLbuyerorexpertAndGroupDTO();
		
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getLotterybuyerOrExpert())//获取申请人
				{
					dto.setApplyUserId(entity.getLotterybuyerOrExpert().getId());
				}
				
				if(null != entity.getLotteryGroup())//获取要加入的群id
				{
					dto.setGroupId(entity.getLotteryGroup().getId());
					dto.setGroupName(entity.getLotteryGroup().getName());
				}
				
				if(null != entity.getStatus())
				{
					dto.setStatusName("1".equals(entity.getStatus())?"通过":"不通过");
				}
				else
				{
					dto.setStatus("2");//审核中
					dto.setStatusName("审核中");
				}
				
				if(null != entity.getLotterybuyerOrExpert())
				{
					//当前返回给群主看的申请加群的用户信息只返回用户的昵称
					dto.setCreatorName(entity.getLotterybuyerOrExpert().getName());
				}
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTime(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
				}
				
				
				
			} catch (Exception e) {
				logger.error("error", e);
			}
		}
		
		return dto;
	}

	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByGroupId(
			String groupId) {
		return relaApplyOfBuyyerorexpertAndGroupRespository.getRelaApplyOfLbuyerorexpertAndGroupByGroupId(groupId);
	}

	public void delete(RelaApplyOfLbuyerorexpertAndGroup entity) 
	{
		relaApplyOfBuyyerorexpertAndGroupRespository.delete(entity);
	}

	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByCreatorAndStatus(
			String creator,String groupId) {
		return relaApplyOfBuyyerorexpertAndGroupRespository.getRelaApplyOfLbuyerorexpertAndGroupByCreatorAndGroupId(creator,groupId);
	}

}
