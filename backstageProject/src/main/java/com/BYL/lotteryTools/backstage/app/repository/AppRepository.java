package com.BYL.lotteryTools.backstage.app.repository;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.app.entity.App;
import com.BYL.lotteryTools.common.repository.GenericRepository;

public interface AppRepository extends GenericRepository<App, String>{

	/**
	 * 
	* @Title: getAppById
	* @Description: 根据app的id查找app实体
	* @Author : banna
	* @param @param id
	* @param @return    设定文件
	* @return App    返回类型
	* @throws
	 */
	@Query("select u from App u where u.isDeleted='1' and u.id =?1")
	public App getAppById(String id);
	
	/**
	 * 
	 * @Title: getAppByAppName
	 * @Description: 根据app名称获取应用数据
	 * @author:banna
	 * @return: App
	 */
	@Query("select u from App u where u.isDeleted='1' and u.appName =?1")
	public App getAppByAppName(String appName);
}
