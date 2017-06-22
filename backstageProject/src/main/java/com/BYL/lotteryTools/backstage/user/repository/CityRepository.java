package com.BYL.lotteryTools.backstage.user.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.user.entity.City;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface CityRepository extends GenericRepository<City, String> {

	/**
	 * 
	* @Description: 根据ccode查询城市信息
	* @author bann@sdfcp.com
	* @date 2015年11月5日 上午9:32:22
	 */
	@Query("select u from City u where  u.ccode =?1")
	public City getCityByCcode(String ccode);
	
	@Query("select u from City u where  u.cname like ?1")
	public List<City> getCityByCname(String cname);
	
	@Query("select u from City u where  u.ccode =?1 and u.provinceCode=?2")
	public City getCityByCcodeAndProvinceCode(String ccode,String provinceCode);
}
