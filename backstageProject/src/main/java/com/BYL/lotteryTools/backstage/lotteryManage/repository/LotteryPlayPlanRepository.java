package com.BYL.lotteryTools.backstage.lotteryManage.repository;


import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlayBulufangan;
import com.BYL.lotteryTools.common.repository.GenericRepository;


/**
 * 
 * 补录模块方案表实现层
 * @author Administrator
 *
 */
public interface LotteryPlayPlanRepository extends GenericRepository<LotteryPlayBulufangan, String> {

	@Query("select u from LotteryPlayBulufangan u where  u.id =?1")
	public LotteryPlayBulufangan getLotteryPlayBulufanganById(String id);
	
}
