package com.BYL.lotteryTools.backstage.app.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.app.controller.AppController;
import com.BYL.lotteryTools.backstage.app.dto.AppversionDTO;
import com.BYL.lotteryTools.backstage.app.entity.Appversion;
import com.BYL.lotteryTools.backstage.app.repository.AppversionRepository;
import com.BYL.lotteryTools.backstage.app.service.AppversionService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;


@Service("appversionService")
@Transactional(propagation=Propagation.REQUIRED)
public class AppversionServiceImpl implements AppversionService {

	@Autowired
	private AppversionRepository appversionRepository;

	public void save(Appversion entity) {

		appversionRepository.save(entity);
	}

	public void update(Appversion entity) {

		appversionRepository.save(entity);
	}

	public Appversion getAppversionById(String id) {
		
		return appversionRepository.getAppversionById(id);
	}

	public QueryResult<Appversion> getAppversionList(
			Class<Appversion> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {
		
		QueryResult<Appversion> appversionList = appversionRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,
				orderby, pageable);
		
		return appversionList;
	}

	public List<AppversionDTO> toRDTOS(List<Appversion> entities) {
		List<AppversionDTO> dtos = new ArrayList<AppversionDTO>();
		AppversionDTO dto;
		for (Appversion entity : entities) 
		{
			dto = toDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	public AppversionDTO toDTO(Appversion entity) {
		AppversionDTO dto = new AppversionDTO();
		try {
			BeanUtil.copyBeanProperties(dto, entity);
			
			//处理实体中的特殊转换值
			if(null != entity.getCreateTime())//创建时间
			{
				dto.setCreateTime(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
			}
			
			if(null != entity.getApp())
			{
				dto.setAppId(entity.getApp().getId());
				dto.setAppName(entity.getApp().getAppName());
			}
			
			if(null != entity.getAppVersionStatus()&& !"".equals(entity.getAppVersionStatus()))
			{
				String appVerStatus = entity.getAppVersionStatus();
				String appVersionStatusName ="";
				if(AppController.APP_V_STATUS_SJ.equals(appVerStatus))
				{
					appVersionStatusName = "上架";
				}
				else 
					if(AppController.APP_V_STATUS_DSJ.equals(appVerStatus))
					{
						appVersionStatusName = "待上架";
					}
					else 
						if(AppController.APP_V_STATUS_XJ.equals(appVerStatus))
						{
							appVersionStatusName = "下架";
						}
						else 
							if(AppController.APP_V_STATUS_GX.equals(appVerStatus))
							{
								appVersionStatusName = "更新";
							}
				dto.setAppVersionStatusName(appVersionStatusName);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public Integer findMaxVersionFlowId(String appId, String appVersionStatus) {
		return appversionRepository.findMaxVersionFlowId(appId, appVersionStatus);
	}

	public Integer findMaxVersionFlowId(String appId) {
		return appversionRepository.findMaxVersionFlowId(appId);
	}

	public Appversion getAppversionByAppIdAndVersionFlowId(String appId,
			Integer versionFlowId) {
		return appversionRepository.getAppversionByAppIdAndVersionFlowId(appId, versionFlowId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
