package com.BYL.lotteryTools.backstage.lotteryGroup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface LotteryGroupRespository extends GenericRepository<LotteryGroup, String> 
{
	@Query("select u from LotteryGroup u where u.isDeleted = 1 and u.id = ?1")
	public LotteryGroup getLotteryGroupById(String id);
	
	@Query("select u from LotteryGroup u where u.isDeleted = 1 and u.groupRobotID = ?1")
	public List<LotteryGroup> getLotteryGroupByGroupRobotID(String groupRobotID);
	
	@Query("select u from LotteryGroup u where u.isDeleted = 1 and u.province = ?1 and u.lotteryType = ?2")
	public List<LotteryGroup> getLotteryGroupByProvinceAndLotteryType(String province,String lotteryType);
	
	@Query("select u from LotteryGroup u where u.isDeleted = 1 and u.groupNumber = ?1 ")
	public LotteryGroup getLotteryGroupByGroupNumber(String groupNumber);
	
	@Query("select u from LotteryGroup u where u.isDeleted = 1 and u.province = ?1 and u.lotteryType = ?2 and u.city = ?3 and u.detailLotteryType = ?4 order by u.createTime desc")
	public List<LotteryGroup> getLotteryGroupByProvinceAndLotteryTypeAndCityAndDetailLotteryType
						(String province,String lotteryType,String city,String detailLotteryType);
	
	@Query("select u from LotteryGroup u where u.isDeleted = 1  and u.lotteryType = ?1")
	public List<LotteryGroup> getLotteryGroupByLotteryType(String lotteryType);
}
