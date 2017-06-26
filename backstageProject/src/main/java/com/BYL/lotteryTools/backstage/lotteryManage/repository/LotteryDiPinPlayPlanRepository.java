package com.BYL.lotteryTools.backstage.lotteryManage.repository;


import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryDiPinPlay;
import com.BYL.lotteryTools.common.repository.GenericRepository;


/**
 * 低频彩种维护资源库
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年6月26日 下午12:12:16
 */
public interface LotteryDiPinPlayPlanRepository extends GenericRepository<LotteryDiPinPlay, String> {

	@Query("select u from LotteryDiPinPlay u where  u.id =?1")
	public LotteryDiPinPlay getLotteryDiPinPlayById(String id);
	
}
