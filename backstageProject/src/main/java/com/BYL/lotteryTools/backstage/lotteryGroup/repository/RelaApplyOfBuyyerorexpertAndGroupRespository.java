package com.BYL.lotteryTools.backstage.lotteryGroup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaApplyOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface RelaApplyOfBuyyerorexpertAndGroupRespository extends GenericRepository<RelaApplyOfLbuyerorexpertAndGroup, String> 
{
	@Query("select u from RelaApplyOfLbuyerorexpertAndGroup u where u.isDeleted = '1' and u.lotterybuyerOrExpert.id = ?1 and u.lotteryGroup.id = ?2 order by CREATE_TIME desc")
	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByUserIdAndGroupId(String userId,String groupId);
	
	//获取群主需要审核的加群申请信息列表
	@Query("select u from RelaApplyOfLbuyerorexpertAndGroup u where u.isDeleted = '1' and u.approvalUser = ?1 order by MODIFY_TIME desc")
	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByApprovalUser(String approvalUser);
	
	//获取当前用户申请的加群审批列表
	@Query("select u from RelaApplyOfLbuyerorexpertAndGroup u where u.isDeleted = '1' and u.creator = ?1 order by MODIFY_TIME desc")
	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByCreator(String creator);
	
	@Query("select u from RelaApplyOfLbuyerorexpertAndGroup u where u.isDeleted = '1' and u.lotteryGroup.id = ?1 order by CREATE_TIME desc")
	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByGroupId(String groupId);
	
	@Query("select u from RelaApplyOfLbuyerorexpertAndGroup u where u.isDeleted = '1' and u.status is null and  u.creator = ?1 and  u.lotteryGroup.id = ?2  order by CREATE_TIME desc")
	public List<RelaApplyOfLbuyerorexpertAndGroup> getRelaApplyOfLbuyerorexpertAndGroupByCreatorAndGroupId(String creator,String groupId);
}
