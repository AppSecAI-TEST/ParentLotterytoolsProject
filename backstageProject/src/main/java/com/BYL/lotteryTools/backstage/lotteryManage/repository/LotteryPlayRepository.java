package com.BYL.lotteryTools.backstage.lotteryManage.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.common.repository.GenericRepository;


/**
 * 
 * 补录信息表实现层
 * @author Administrator
 *
 */
public interface LotteryPlayRepository extends GenericRepository<LotteryPlay, String> {

	@Query("select u from LotteryPlay u where  u.id =?1")
	public LotteryPlay getLotteryPlayById(String id);
	
	@Query("select u from LotteryPlay u where u.isDeleted='1' and  u.province =?1 and u.lotteryType =?2")
	public List<LotteryPlay> getLotteryPlayByProvinceAndLotteryType(String province,String lotteryType);
}
