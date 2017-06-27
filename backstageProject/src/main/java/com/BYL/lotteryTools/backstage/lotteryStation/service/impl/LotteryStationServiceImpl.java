package com.BYL.lotteryTools.backstage.lotteryStation.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryStation.dto.LotteryStationDTO;
import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.backstage.lotteryStation.repository.LotteryStationRepository;
import com.BYL.lotteryTools.backstage.lotteryStation.service.LotteryStationService;
import com.BYL.lotteryTools.backstage.user.entity.City;
import com.BYL.lotteryTools.backstage.user.entity.Province;
import com.BYL.lotteryTools.backstage.user.service.CityService;
import com.BYL.lotteryTools.backstage.user.service.ProvinceService;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lotteryStationService")
@Transactional(propagation=Propagation.REQUIRED)
public class LotteryStationServiceImpl implements LotteryStationService {

	@Autowired
	private LotteryStationRepository lotteryStationRepository;
	
	@Autowired
	private UploadfileService uploadfileService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;

	public void save(LotteryStation entity)
	{
		lotteryStationRepository.save(entity);
		
	}

	public void update(LotteryStation entity) 
	{
		lotteryStationRepository.save(entity);		
	}

	public LotteryStation getLotteryStationById(String id) 
	{
		return lotteryStationRepository.getLotteryStationById(id);
	}

	public QueryResult<LotteryStation> getLotteryStationList(
			Class<LotteryStation> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {
		
		QueryResult<LotteryStation> queryResult = lotteryStationRepository.getScrollDataByJpql(LotteryStation.class,
				whereJpql, queryParams, orderby, pageable);
		
		return queryResult;
	}

	public LotteryStationDTO toDTO(LotteryStation entity) 
	{
		LotteryStationDTO dto = new LotteryStationDTO();
		
		if(null != entity)
		{
			try 
			{
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTimeStr(DateUtil.formatDate(entity.getCreateTime(),DateUtil.FULL_DATE_FORMAT));
				}
				
				if(null != entity.getOpenDoorTime())
				{
					dto.setOpenDoorTimeStr(DateUtil.formatDate(entity.getOpenDoorTime(),DateUtil.FULL_DATE_FORMAT));
				}
				
				if(null != entity.getCloseDoorTime())
				{
					dto.setCloseDoorTimeStr(DateUtil.formatDate(entity.getCloseDoorTime(),DateUtil.FULL_DATE_FORMAT));
				}
				
				if(null != entity.getLotteryBuyerOrExpert())
				{//放置站主用户id
					dto.setUserId(entity.getLotteryBuyerOrExpert().getId());
					dto.setStationOwner(entity.getLotteryBuyerOrExpert().getName());
				}
				
				if(null != entity.getApprovalStatus())
				{
					dto.setApprovalStatusName(entity.getApprovalStatus().equals("1")?"审核完成":"审核中");
				}
				
				if(null != entity.getStatus())
				{
					if(entity.getStatus().equals("1"))
					{
						dto.setStatusName("已认证");
					}
					else
						if(entity.getStatus().equals("0"))
						{
							dto.setStatusName("认证失败");
						}
						
				}
				else
				{
					dto.setStatusName("未审核");
				}
				
				if(null != entity.getDaixiaoImg())
				{
					Uploadfile daixiao = uploadfileService.getUploadfileByNewsUuid(entity.getDaixiaoImg());
					dto.setDaixiaoImgUrl(daixiao.getUploadfilepath()+daixiao.getUploadRealName());
				}
				
				//获取站主的身份证图片
				if(null != entity.getIdNumberFrontImg())
				{
					Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(entity.getIdNumberFrontImg());
					dto.setIdNumberFrontImgUrl(uploadfile.getUploadfilepath()+uploadfile.getUploadRealName());
				}
				
				if(null != entity.getIdNumberBackImg())
				{
					Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(entity.getIdNumberBackImg());
					dto.setIdNumberBackImgUrl(uploadfile.getUploadfilepath()+uploadfile.getUploadRealName());
				}
				
				if(null != entity.getProvince())
				{
					Province province = provinceService.getProvinceByPcode(entity.getProvince());
					dto.setProvinceName(province.getPname());
				}
					
				if(null != entity.getCity())
				{
					City city = cityService.getCityByCcode(entity.getCity());
					dto.setCityName(city.getCname());
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			
		}
		
		return dto;
	}

	public List<LotteryStationDTO> toDTOs(List<LotteryStation> entities) 
	{
		List<LotteryStationDTO> dtos = new ArrayList<LotteryStationDTO>();
			
		for (LotteryStation entity : entities) 
		{
			LotteryStationDTO dto = toDTO(entity);
			
			dtos.add(dto);
		}
		
		return dtos;
	}

	public LotteryStation getLotteryStationByStationNumber(String stationNumber)
	{
		return lotteryStationRepository.getLotteryStationByStationNumber(stationNumber);
	}
	
	public List<LotteryStation> getLotteryStationByUserId(String userId)
	{
		return lotteryStationRepository.getLotteryStationByUserId(userId);
	}
	
	public List<LotteryStation> findAll()
	{
		return lotteryStationRepository.findAll();
	}

	public LotteryStation getLotteryStationByInviteCode(String inviteCode) {
		return lotteryStationRepository.getLotteryStationByInviteCode(inviteCode);
	}
	
}
