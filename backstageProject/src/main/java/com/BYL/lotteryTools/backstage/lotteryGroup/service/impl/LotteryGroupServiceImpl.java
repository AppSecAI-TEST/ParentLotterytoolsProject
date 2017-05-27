package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.dto.LotteryGroupDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.LotteryGroupRespository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.backstage.user.entity.City;
import com.BYL.lotteryTools.backstage.user.entity.Province;
import com.BYL.lotteryTools.backstage.user.service.CityService;
import com.BYL.lotteryTools.backstage.user.service.ProvinceService;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lotteryGroupService")
@Transactional(propagation=Propagation.REQUIRED)
public class LotteryGroupServiceImpl implements LotteryGroupService 
{
	@Autowired
	private LotteryGroupRespository lotteryGroupRespository;
	
	@Autowired
	private UploadfileService uploadfileService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired 
	private CityService cityService;
	
	public List<LotteryGroup> findAll()
	{
		return lotteryGroupRespository.findAll();
	}
	
	public void save(LotteryGroup entity)
	{
		lotteryGroupRespository.save(entity);
	}
	
	public void update(LotteryGroup entity)
	{
		lotteryGroupRespository.save(entity);
	}
	
	public QueryResult<LotteryGroup> getLotteryGroupList(
			Class<LotteryGroup> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {
		
		QueryResult<LotteryGroup> queryResult = lotteryGroupRespository.getScrollDataByJpql
				(LotteryGroup.class, whereJpql, queryParams, orderby, pageable);
		
		return queryResult;
	}
	
	public LotteryGroupDTO toDTO(LotteryGroup entity) {
		
		LotteryGroupDTO dto = new LotteryGroupDTO();
		
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTimeStr(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
				}
				
				if(null != entity.getTouXiang()&&!"".equals(entity.getTouXiang()))
				{
					Uploadfile touxiangImg = uploadfileService.getUploadfileByNewsUuid(entity.getTouXiang());
					if(null != touxiangImg)
					{
						dto.setTouXiang(touxiangImg.getNewsUuid());
						dto.setTouXiangImgUrl(touxiangImg.getUploadfilepath()+touxiangImg.getUploadRealName());
					}
				}
				
				if(null != entity.getProvince() && !Constants.PROVINCE_ALL.equals(entity.getProvince()))
				{
					Province province = provinceService.getProvinceByPcode(entity.getProvince());
					
					dto.setProvinceName(province.getPname());
				}
				
				if(null != entity.getCity() && !Constants.CITY_ALL.equals(entity.getCity()))
				{
					City city = cityService.getCityByCcode(entity.getCity());
					
					if(null != city)
						dto.setCityName(city.getCname());
				}
				
				if(null != entity.getLotteryBuyerOrExpert())
				{
					dto.setOwnerName(entity.getLotteryBuyerOrExpert().getName());
					dto.setOwnerId(entity.getLotteryBuyerOrExpert().getId());
				}
				//判断当前群主是否有认证的彩票站
				if(null != entity.getLotteryBuyerOrExpert())
				{
					if(null != entity.getLotteryBuyerOrExpert().getLotteryStations())
						dto.setHaveStation("1");
				}
				else
				{
					dto.setHaveStation("0");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<LotteryGroupDTO> toDTOs(
			List<LotteryGroup> entities) {
		
		List<LotteryGroupDTO> dtos = new ArrayList<LotteryGroupDTO>();
		
		for (LotteryGroup entity : entities) 
		{
			LotteryGroupDTO dto = new LotteryGroupDTO();
			
			dto = toDTO(entity);
			
			dtos.add(dto);
		}
		
		return dtos;
	}

	public List<LotteryGroup> getLotteryGroupByGroupRobotID(String groupRobotID) 
	{
		return lotteryGroupRespository.getLotteryGroupByGroupRobotID(groupRobotID);
	}
	
	public LotteryGroup getLotteryGroupById(String id)
	{
		return lotteryGroupRespository.getLotteryGroupById(id);
	}

	public LotteryGroup getLotteryGroupByGroupNumber(String groupNumber) {
		return lotteryGroupRespository.getLotteryGroupByGroupNumber(groupNumber);
	}
	
	//TODO:生成站点邀请码
	public  String generateGroupNumber()
	{
		List<LotteryGroup> alllist = lotteryGroupRespository.findAll();
		
		int code = alllist.size()+1;
		StringBuffer str = new StringBuffer(code+"");
		//6位邀请码
		while(str.length()<8)
		{
			str.insert(0, "0");
		}
		return str.toString();
	}

	public List<LotteryGroup> getLotteryGroupByProvinceAndLotteryType(
			String province, String lotteryType) {
		return lotteryGroupRespository.getLotteryGroupByProvinceAndLotteryType(province, lotteryType);
	}
	
	
}
