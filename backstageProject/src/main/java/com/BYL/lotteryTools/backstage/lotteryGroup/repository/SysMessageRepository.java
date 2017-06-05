package com.BYL.lotteryTools.backstage.lotteryGroup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.SysMessage;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface SysMessageRepository extends GenericRepository<SysMessage, String> 
{
	@Query("select u from SysMessage u where  u.isDeleted = 1 and   u.target = ?1  order by u.createTime desc")
	public List<SysMessage> getSysMessageByTarget(String target);
}
