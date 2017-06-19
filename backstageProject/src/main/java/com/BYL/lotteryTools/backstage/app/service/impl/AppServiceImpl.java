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
import com.BYL.lotteryTools.backstage.app.dto.AppDTO;
import com.BYL.lotteryTools.backstage.app.entity.App;
import com.BYL.lotteryTools.backstage.app.repository.AppRepository;
import com.BYL.lotteryTools.backstage.app.service.AppService;
import com.BYL.lotteryTools.backstage.user.entity.City;
import com.BYL.lotteryTools.backstage.user.entity.Province;
import com.BYL.lotteryTools.backstage.user.service.CityService;
import com.BYL.lotteryTools.backstage.user.service.ProvinceService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;


@Service("appService")
@Transactional(propagation=Propagation.REQUIRED)
public class AppServiceImpl implements AppService {
	
	
	@Autowired
	private AppRepository appRepository;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	/**
	 * 
	* @Title: save
	* @Description: 保存应用实体内容
	* @Author : banna
	* @param @param entity    设定文件
	* @return void    返回类型
	* @throws
	 */
	public void save(App entity)
	{
		appRepository.save(entity);
	}
	
	/**
	 * 
	* @Title: update
	* @Description: 修改应用实体内容
	* @Author : banna
	* @param @param entity    设定文件
	* @return void    返回类型
	* @throws
	 */
	public void update(App entity)
	{
		appRepository.save(entity);
	}

	/**
	 * 
	* @Title: getAppById
	* @Description: 根据id获取应用内容
	* @Author : banna
	* @param @param id
	* @param @return    设定文件
	* @throws
	 */
	public App getAppById(String id) {
		
		App app = appRepository.getAppById(id);
		
		return app;
	}
	
	/**
	 * 
	* @Title: getAppList
	* @Description: 根据筛选条件获取应用列表数据
	* @Author : banna
	* @param @param entityClass
	* @param @param whereJpql
	* @param @param queryParams
	* @param @param orderby
	* @param @param pageable
	* @param @return    设定文件
	* @return QueryResult<App>    返回类型
	* @throws
	 */
	public QueryResult<App> getAppList(Class<App> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable)
	{
		
		QueryResult<App> appList = appRepository.getScrollDataByJpql(entityClass, whereJpql, queryParams,
				orderby, pageable);
		
		return appList;
	}
	
	public QueryResult<App> getAppOfFufei(Class<App> entityClass,
			String whereJpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby, Pageable pageable,String province,String city,String lotteryType,String stationId) {
		String sql = " SELECT u.* FROM T_BS_APPLICATION u LEFT JOIN RELA_BS_STATION_AND_APP rbsaa  ON u.ID= rbsaa.APP_ID "//left join的默认属性是outer，所以left join与left outer join效果相同
						+ "	AND rbsaa.IS_DELETED='1' AND rbsaa.STATUS='1' AND rbsaa.STATION_ID='"+stationId+"' "
						+ "  LEFT JOIN T_BS_APP_PRICE_AND_AREA tbs ON u.ID=tbs.APP_ID "
						+ " WHERE u.IS_DELETED='1' AND tbs.IS_DELETED='1'  AND u.APP_STATUS='1' AND u.PROVINCE='"+province+"' AND u.CITY IN ('"+Constants.CITY_ALL+"','"+city+"') "
						+ " 	AND  tbs.CITY='"+city+"' AND tbs.UNIT_PRICE>0 AND u.LOTTERY_TYPE='"+lotteryType+"' "
						+ " AND rbsaa.ID IS NULL  ";//
		QueryResult<App> appQueryResult = appRepository.
			getScrollDataBySql(App.class,sql, queryParams, pageable);
		return appQueryResult;
	}
	
	
	
	public  AppDTO toDTO(App entity) {
		AppDTO dto = new AppDTO();
		try {
			BeanUtil.copyBeanProperties(dto, entity);
			
			//处理实体中的特殊转换值
			if(null != entity.getCreateTime())//创建时间
			{
				dto.setCreateTime(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
			}
			
			if(null != entity.getProvince())//省级区域
			{
				Province province = new Province();
				province = provinceService.getProvinceByPcode(entity.getProvince());
				
				dto.setProvinceName(null != province?province.getPname():"");
				dto.setAppNameWithProvince(entity.getAppName()+" "+(null != province?province.getPname():""));
				
			}
			if(null != entity.getCity())//市级区域
			{
				if(Constants.CITY_ALL.equals(entity.getCity()))
				{
					dto.setCityName(Constants.CITY_ALL_NAME);
				}
				else
				{
					City city = new City();
					city = cityService.getCityByCcode(entity.getCity());
					dto.setCityName(null != city?city.getCname():"");
				}
				
			}
			
			if(null != entity.getAppStatus()&& !"".equals(entity.getAppStatus()))
			{
				String appStatus = entity.getAppStatus();
				String appTypeName ="";
				if(AppController.APP_STATUS_SJ.equals(appStatus))
				{
					appTypeName = "上架";
				}
				else 
					if(AppController.APP_STATUS_DSJ.equals(appStatus))
					{
						appTypeName = "待上架";
					}
					else 
						if(AppController.APP_STATUS_XJ.equals(appStatus))
						{
							appTypeName = "下架";
						}
						else 
							if(AppController.APP_STATUS_GX.equals(appStatus))
							{
								appTypeName = "更新";
							}
				dto.setAppTypeName(appTypeName);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
					
		return dto;
	}

	
	public  List<AppDTO> toRDTOS(List<App> entities) {
		List<AppDTO> dtos = new ArrayList<AppDTO>();
		AppDTO dto;
		for (App entity : entities) 
		{
			dto = toDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	public App getAppByAppName(String appName) {
		return appRepository.getAppByAppName(appName);
	}
			
}
