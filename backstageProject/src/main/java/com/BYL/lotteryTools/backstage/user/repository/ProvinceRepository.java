package com.BYL.lotteryTools.backstage.user.repository;


import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.user.entity.Province;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface ProvinceRepository extends GenericRepository<Province, String> {
	
	/**
	 * 
	* @Description:根据pcode查询省份信息
	* @author bann@sdfcp.com
	* @date 2015年11月5日 上午9:30:02
	 */
	@Query("select u from Province u where  u.pcode =?1")
	public Province getProvinceByPcode(String pcode);
}
