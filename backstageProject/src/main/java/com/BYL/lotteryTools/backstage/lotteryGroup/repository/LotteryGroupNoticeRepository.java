package com.BYL.lotteryTools.backstage.lotteryGroup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroupNotice;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface LotteryGroupNoticeRepository extends GenericRepository<LotteryGroupNotice, String> 
{
	@Query("select u from LotteryGroupNotice u where u.isDeleted = 1 and   u.id = ?1")
	public LotteryGroupNotice getLotteryGroupNoticeByID(String id);
	
	@Query("select u from LotteryGroupNotice u where u.isDeleted = 1 and   u.lotteryGroup.id = ?1 order by createTime desc ")
	public List<LotteryGroupNotice> getLotteryGroupNoticeByGroupId(String groupId);
}
