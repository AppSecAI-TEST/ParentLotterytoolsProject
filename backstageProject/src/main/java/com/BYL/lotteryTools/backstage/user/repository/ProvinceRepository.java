package com.BYL.lotteryTools.backstage.user.repository;


import java.util.List;

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
	
	/**
	 * 根据省份名称获取省份信息
	* @Title: getProvinceByPname 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param pname
	* @param @return    设定文件 
	* @author banna
	* @date 2017年5月15日 上午11:26:03 
	* @return List<Province>    返回类型 
	* @throws
	 */
	@Query("select u from Province u where  u.pname like ?1")
	public List<Province> getProvinceByPname(String pname);
	
}
