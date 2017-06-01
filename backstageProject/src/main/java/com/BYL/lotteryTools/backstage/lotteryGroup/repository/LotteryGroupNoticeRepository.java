package com.BYL.lotteryTools.backstage.lotteryGroup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroupNotice;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface LotteryGroupNoticeRepository extends GenericRepository<LotteryGroupNotice, String> 
{
	@Query("select u from LotteryGroupNotice u where u.isDeleted = 1 and   u.id = ?1")
	public LotteryGroupNotice getLotteryGroupNoticeByID(String id);
	
	/**
	 * 获取有效的且可以显示的群公告列表数据
	* @Title: getLotteryGroupNoticeByGroupId 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param groupId
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月1日 下午5:42:59 
	* @return List<LotteryGroupNotice>    返回类型 
	* @throws
	 */
	@Query("select u from LotteryGroupNotice u where u.isDeleted = 1 and  u.status = 1 and  u.lotteryGroup.id = ?1 order by createTime desc ")
	public List<LotteryGroupNotice> getLotteryGroupNoticeByGroupId(String groupId);
}
