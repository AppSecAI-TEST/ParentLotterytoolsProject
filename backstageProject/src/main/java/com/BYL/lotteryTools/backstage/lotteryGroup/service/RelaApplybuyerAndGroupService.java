package com.BYL.lotteryTools.backstage.lotteryGroup.service;

import java.util.List;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.RelaApplyOfLbuyerorexpertAndGroupDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaApplyOfLbuyerorexpertAndGroup;

public interface RelaApplybuyerAndGroupService {

	public void save(RelaApplyOfLbuyerorexpertAndGroup entity);
	
	public void update(RelaApplyOfLbuyerorexpertAndGroup entity);
	
	public RelaApplyOfLbuyerorexpertAndGroup getRelaApplyOfLbuyerorexpertAndGroupByUserIdAndGroupId(String userId,String groupId);
	
	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByApprovalUser(String approvalUser);
	
	//获取当前用户申请的加群审批列表
	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByCreator(String creator);
	
	public List<RelaApplyOfLbuyerorexpertAndGroupDTO> toDTOS(List<RelaApplyOfLbuyerorexpertAndGroup> entities);
	
	public RelaApplyOfLbuyerorexpertAndGroupDTO toDTO(RelaApplyOfLbuyerorexpertAndGroup entity);
}
