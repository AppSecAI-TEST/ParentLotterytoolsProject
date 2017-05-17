package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.repository.LotterybuyerOrExpertRepository;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.backstage.user.entity.City;
import com.BYL.lotteryTools.backstage.user.entity.Province;
import com.BYL.lotteryTools.backstage.user.service.CityService;
import com.BYL.lotteryTools.backstage.user.service.ProvinceService;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.MyMD5Util;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lotterybuyerOrExpertService")
@Transactional(propagation= Propagation.REQUIRED)
public class LotterybuyerOrExpertServiceImpl implements
		LotterybuyerOrExpertService {

	@Autowired
	private LotterybuyerOrExpertRepository lotterybuyerOrExpertRepository;
	
	@Autowired
	private LotteryGroupService lotteryGroupService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private RongyunImService rongyunImService;
	
	@Autowired
	private UploadfileService uploadfileService;
	
	@Autowired
	private CityService cityService;

	public void save(LotterybuyerOrExpert entity) 
	{
		lotterybuyerOrExpertRepository.save(entity);		
	}

	public void update(LotterybuyerOrExpert entity) {
		lotterybuyerOrExpertRepository.save(entity);		
		
	}

	public LotterybuyerOrExpertDTO toDTO(LotterybuyerOrExpert entity) {
		
		LotterybuyerOrExpertDTO dto = new LotterybuyerOrExpertDTO();
		
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTimeStr(DateUtil.formatDate(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT));
				}
				
				if(null != entity.getTouXiang()&& !"".equals(entity.getTouXiang()))
				{
					Uploadfile touxiangImg = uploadfileService.getUploadfileByNewsUuid(entity.getTouXiang());
					if(null != touxiangImg)
					{
						dto.setTouXiang(touxiangImg.getNewsUuid());
						dto.setTouXiangUrl(touxiangImg.getUploadfilepath()+touxiangImg.getUploadRealName());
					}
				}
				
				if(null != entity.getProvinceCode() && !"".equals(entity.getProvinceCode()))
				{
					Province province  = provinceService.getProvinceByPcode(entity.getProvinceCode());
					if(null != province)
						dto.setProvinceName(province.getPname());
				}
				
				if(null != entity.getCityCode() && !"".equals(entity.getCityCode()))
				{
					City city =  cityService.getCityByCcode(entity.getCityCode());
					if(null != city)
						dto.setCityName(city.getCname());
				}
				
				//是否为群主
				if(null != entity.getLotteryGroups() && entity.getLotteryGroups().size()>0)
				{
					dto.setIsGroupOwner("1");
				}
				else
				{
					dto.setIsGroupOwner("0");
				}
				
				if(null == entity.getColorCoins())
				{
					dto.setColorCoins(new BigDecimal(0));
				}
				
				if(null == entity.getHandSel())
				{
					dto.setHandSel(new BigDecimal(0));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<LotterybuyerOrExpertDTO> toDTOs(
			List<LotterybuyerOrExpert> entities) {
		
		List<LotterybuyerOrExpertDTO> dtos = new ArrayList<LotterybuyerOrExpertDTO>();
		
		for (LotterybuyerOrExpert entity : entities) 
		{
			LotterybuyerOrExpertDTO dto = new LotterybuyerOrExpertDTO();
			
			dto = toDTO(entity);
			
			dtos.add(dto);
		}
		
		return dtos;
	}

	public QueryResult<LotterybuyerOrExpert> getLotterybuyerOrExpertList(
			Class<LotterybuyerOrExpert> entityClass, String whereJpql,
			Object[] queryParams, LinkedHashMap<String, String> orderby,
			Pageable pageable) {
		
		QueryResult<LotterybuyerOrExpert> queryResult = lotterybuyerOrExpertRepository.getScrollDataByJpql
				(LotterybuyerOrExpert.class, whereJpql, queryParams, orderby, pageable);
		
		return queryResult;
	}

	public LotterybuyerOrExpert getLotterybuyerOrExpertById(String id) {
		return lotterybuyerOrExpertRepository.getLotterybuyerOrExpertById(id);
	}
	
	public LotterybuyerOrExpert getLotterybuyerOrExpertByTelephone(String telephone)
	{
		return lotterybuyerOrExpertRepository.getLotterybuyerOrExpertByTelephone(telephone);
	}
	
	public List<LotterybuyerOrExpert> getLotterybuyerOrExpertByCailiaoName(String cailiaoName)
	{
		return lotterybuyerOrExpertRepository.getLotterybuyerOrExpertByCailiaoName(cailiaoName);
	}
	
	/**
	 * 创建机器人用户
	* @Title: createRobotUser 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param province
	* @param @param city
	* @param @param lotteryType
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月24日 上午10:28:59 
	* @return String    返回类型 
	* @throws
	 */
	public String createRobotUser(String province,String city,String lotteryType)
	{
		//查询当前省份彩种是否有机器人
		//放置分页参数
		Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		params.add("1");//查询机器人数据
		buffer.append(" and  isRobot = ?").append(params.size());
		
		//连接查询条件
		if(null != province && !"".equals(province)&& !Constants.PROVINCE_ALL.equals(province))
		{
			params.add(province);
			buffer.append(" and provinceCode = ?").append(params.size());
		}
		
		if(null != city && !"".equals(city)&& !Constants.CITY_ALL.equals(city))
		{
			params.add(city);
			buffer.append(" and cityCode = ?").append(params.size());
		}
		
		/*if(null != lotteryType && !"".equals(lotteryType))
		{
			params.add(lotteryType);
			buffer.append(" and lotteryType = ?").append(params.size());
		}*/
		
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("createTime", "desc");
		
		QueryResult<LotterybuyerOrExpert> lQueryResult = this
				.getLotterybuyerOrExpertList(LotterybuyerOrExpert.class,
				buffer.toString(), params.toArray(),orderBy, pageable);
				
		List<LotterybuyerOrExpert> robotlist = lQueryResult.getResultList();
		
		String robotUserId="";
		List<LotteryGroup> list = null;
		if(null != robotlist && robotlist.size()>0)
		{
			robotUserId = robotlist.get(0).getId();//获取最新创建的机器人的用户id
			
			//判断当前用户属于多少个群
			list = lotteryGroupService.getLotteryGroupByGroupRobotID(robotUserId);
		}
		
		
		
		//若超过500个群（有效群，若群删除时则要移除群机器人，要去融云解除用户和群的关系绑定），则需要新建新的机器人
		if("".equals(robotUserId)|| null == list || list.size()>=500)
		{//若机器人的加群数大于500，则要创建新的
			Province pro = provinceService.getProvinceByPcode(province);//获取省份信息
			//创建机器人用户
			LotterybuyerOrExpert robot = new LotterybuyerOrExpert();
			robot.setId(UUID.randomUUID().toString());
			robot.setName(pro.getPname()+"机器人");//拼接机器人名称
			robot.setProvinceCode(province);
			robot.setCityCode(city);
			robot.setColorCoins(new BigDecimal(0));
			robot.setHandSel(new BigDecimal(0));
			try {
				robot.setPassword(MyMD5Util.getEncryptedPwd("123456"));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			robot.setIsPhone("0");//从app端走注册接口的一定是手机用户
			robot.setIsExpert("0");//注册时用户的默认身份是彩民
			robot.setIsVirtual("0");//是否为虚拟用户（虚拟用户是由公司来创建的，没有实际意义）
			robot.setIsRobot("1");//机器人用户
			robot.setIsStationOwner("0");//在注册时默认都不是站主
			robot.setFromApp("0");//app注册入口进入则为app用户
			
			robot.setIsDeleted(Constants.IS_NOT_DELETED);
			robot.setCreator(robot.getId());
			robot.setCreateTime(new Timestamp((System.currentTimeMillis())));
			robot.setModify(robot.getId());
			robot.setModifyTime(new Timestamp((System.currentTimeMillis())));
			
			//创建机器人的融云账户
			String token = rongyunImService.getUserToken(robot.getId(),
					robot.getName(), "");
			robot.setToken(token);
			//保存机器人用户信息
			this.save(robot);
			robotUserId = robot.getId();
		}
		
		
		return robotUserId;
	}
}
