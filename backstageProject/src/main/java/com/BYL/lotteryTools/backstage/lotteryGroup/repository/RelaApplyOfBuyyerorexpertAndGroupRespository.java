package com.BYL.lotteryTools.backstage.lotteryGroup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaApplyOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface RelaApplyOfBuyyerorexpertAndGroupRespository extends GenericRepository<RelaApplyOfLbuyerorexpertAndGroup, String> 
{
	@Query("select u from RelaApplyOfLbuyerorexpertAndGroup u where u.isDeleted = '1' and u.lotterybuyerOrExpert.id = ?1 and u.lotteryGroup.id = ?2")
	public RelaApplyOfLbuyerorexpertAndGroup getRelaApplyOfLbuyerorexpertAndGroupByUserIdAndGroupId(String userId,String groupId);
	
	//获取群主需要审核的加群申请信息列表
	@Query("select u from RelaApplyOfLbuyerorexpertAndGroup u where u.isDeleted = '1' and u.approvalUser = ?1 ")
	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByApprovalUser(String approvalUser);
	
	//获取当前用户申请的加群审批列表
	@Query("select u from RelaApplyOfLbuyerorexpertAndGroup u where u.isDeleted = '1' and u.creator = ?1 ")
	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByCreator(String creator);
	
	
}
