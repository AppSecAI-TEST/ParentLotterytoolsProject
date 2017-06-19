package com.BYL.lotteryTools.backstage.app.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.app.dto.AppDTO;
import com.BYL.lotteryTools.backstage.app.entity.App;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface AppService {

	public void save(App entity);
	
	public void update(App entity);
	
	
	public App getAppById(String id);
	
	
	public  List<AppDTO> toRDTOS(List<App> entities);
	
	
	public  AppDTO toDTO(App entity);
	
	public QueryResult<App> getAppList(Class<App> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	/**
	 * 
	 * @Title: getAppByAppName
	 * @Description: 根据应用名称获取应用数据详情
	 * @author:banna
	 * @return: App
	 */
	public App getAppByAppName(String appName);
	
}
