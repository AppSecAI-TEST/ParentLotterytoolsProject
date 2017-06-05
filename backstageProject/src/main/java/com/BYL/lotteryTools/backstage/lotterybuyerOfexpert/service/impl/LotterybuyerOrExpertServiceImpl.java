package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.controller.OuterLotteryBuyerOrExpertController;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotteryChatCardDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotteryChatCard;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.RelaLBEUserAndLtcard;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.repository.LotteryChatCardRepository;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.repository.LotterybuyerOrExpertRepository;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.repository.RelaLBEUserAndLtcardRepository;
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
@Transactional(propagation= Propagation.REQUIRED,rollbackForClassName={"Exception"})
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
	
	@Autowired
	private RelaLBEUserAndLtcardRepository relaLBEUserAndLtcardRepository;
	
	@Autowired
	private LotteryChatCardRepository lotteryChatCardRepository;

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
			int page,
			int rows,
			String name,
			String provinceCode,
			String isVirtual,
			String isRobot,
			String telephone) {
		

	 	//放置分页参数
		Pageable pageable = new PageRequest(page-1,rows);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		//连接查询条件
		if(null != name&&!"".equals(name.trim()))
		{
			params.add("%"+name+"%");
			buffer.append(" and name like ?").append(params.size());
		}
		
		if(null != provinceCode && !"".equals(provinceCode)&& !Constants.PROVINCE_ALL.equals(provinceCode))
		{
			params.add(provinceCode);
			buffer.append(" and provinceCode = ?").append(params.size());
		}
		
		//虚拟用户查询条件
		if(null != isVirtual && !"".equals(isVirtual))
		{
			params.add(isVirtual);
			buffer.append(" and  isVirtual = ?").append(params.size());
		}
		
		//是否为机器人
		if(null != isRobot && !"".equals(isRobot))
		{
			params.add(isRobot);
			buffer.append(" and  isRobot = ?").append(params.size());
		}
		
		//手机号
		if(null != telephone && !"".equals(telephone))
		{
			params.add(telephone);
			buffer.append(" and  telephone = ?").append(params.size());
		}
		
		
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("createTime", "desc");
		
		QueryResult<LotterybuyerOrExpert> queryResult = lotterybuyerOrExpertRepository.getScrollDataByJpql
				(LotterybuyerOrExpert.class, buffer.toString(), params.toArray(),orderBy, pageable);
		
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
	public String createRobotUser(String province,String city,String lotteryType,HttpServletRequest request)
	{
		//查询当前省份彩种是否有机器人
		QueryResult<LotterybuyerOrExpert> lQueryResult = this
				.getLotterybuyerOrExpertList(1, Integer.MAX_VALUE, null, province, null, "1",null);
				
		List<LotterybuyerOrExpert> robotlist = lQueryResult.getResultList();
		
		String robotUserId="";
		List<LotteryGroup> list = null;
		if(null != robotlist && robotlist.size()>0)
		{
			robotUserId = robotlist.get(0).getId();//获取最新创建的机器人的用户id
			
			//判断当前用户属于多少个群
			list = lotteryGroupService.getLotteryGroupByGroupRobotID(robotUserId);
		}
		
		
		
		//若超过200个群（有效群，若群删除时则要移除群机器人，要去融云解除用户和群的关系绑定），则需要新建新的机器人
		if("".equals(robotUserId)|| null == list || list.size()>=200)
		{//若机器人的加群数大于500，则要创建新的
			Province pro = provinceService.getProvinceByPcode(province);//获取省份信息
			//创建机器人用户
			LotterybuyerOrExpert robot = new LotterybuyerOrExpert();
			robot.setId(UUID.randomUUID().toString());
			robot.setName("彩小二");//拼接机器人名称
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
			Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(OuterLotteryBuyerOrExpertController.morenTouxiang);
			//创建机器人的融云账户
			StringBuffer imguri = new StringBuffer();
			imguri.append(OuterLotteryBuyerOrExpertController.DOMAIN)
			.append(request.getContextPath()).append(uploadfile.getUploadfilepath()).append(uploadfile.getUploadRealName());
			robot.setTouXiang(uploadfile.getNewsUuid());
			String token = rongyunImService.getUserToken(robot.getId(),
					robot.getName(), imguri.toString());
			robot.setToken(token);
			//保存机器人用户信息
			this.save(robot);
			robotUserId = robot.getId();
		}
		
		
		return robotUserId;
	}

	public RelaLBEUserAndLtcard getRelaLBEUserAndLtcardByUserIdAndCardId(
			String userId, String cardId) {
		return relaLBEUserAndLtcardRepository.getRelaLBEUserAndLtcardByUserIdAndCardId(userId, cardId);
	}

	public void saveRelaLBEUserAndLtcard(RelaLBEUserAndLtcard entity) {
		relaLBEUserAndLtcardRepository.save(entity);
	}

	public void updateRelaLBEUserAndLtcard(RelaLBEUserAndLtcard entity) {
		relaLBEUserAndLtcardRepository.save(entity);		
	}

	public RelaLBEUserAndLtcard getRelaLBEUserAndLtcardById(String id) {
		return relaLBEUserAndLtcardRepository.getRelaLBEUserAndLtcardById(id);
	}

	public LotteryChatCard getLotteryChatCardById(String id) {
		return lotteryChatCardRepository.getLotteryChatCardById(id);
	}
	
	public List<LotteryChatCard> findAllLotteryChatCards()
	{
		return lotteryChatCardRepository.findAll();
	}
	
	/**
	 * 
	* @Title: updateCardsOfUser 
	* @Description: 卡片数加（addNum）张
	* @param @param owner
	* @param @param cardId
	* @param @param addNum    设定文件 
	* @author banna
	* @date 2017年5月25日 下午3:25:16 
	* @throws
	 */
	public void updateCardsOfUser(LotterybuyerOrExpert owner,String cardId,Integer addNum)
	{
		RelaLBEUserAndLtcard card = this.
				getRelaLBEUserAndLtcardByUserIdAndCardId(owner.getId(), cardId);
		
		if(null != card)
		{//已经有卡的，卡数加1
			card.setNotUseCount(card.getNotUseCount()+addNum);
		}
		else
		{
			card = new RelaLBEUserAndLtcard();
			card.setCreateTime(new Timestamp(System.currentTimeMillis()));
			card.setModifyTime(new Timestamp(System.currentTimeMillis()));
			card.setIsDeleted(Constants.IS_NOT_DELETED);
			card.setUseCount(0);
			card.setNotUseCount(addNum); 
			card.setLotterybuyerOrExpert(owner);
			LotteryChatCard lotteryChatCard = this.getLotteryChatCardById(cardId);
			card.setLotteryChatCard(lotteryChatCard);
			
			this.saveRelaLBEUserAndLtcard(card);
		}
		
	}
	
	/**
	 * 
	* @Title: reduceCardsOfUser 
	* @Description: 减少一张卡片 
	* @param @param owner
	* @param @param cardId    设定文件 
	* @author banna
	* @date 2017年5月25日 下午3:25:37 
	* @throws
	 */
	public void reduceCardsOfUser(LotterybuyerOrExpert owner,String cardId)
	{
		RelaLBEUserAndLtcard card = this.
				getRelaLBEUserAndLtcardByUserIdAndCardId(owner.getId(), cardId);
		
		card.setUseCount(card.getUseCount()+1);
		card.setNotUseCount(card.getNotUseCount()-1);
		card.setModifyTime(new Timestamp(System.currentTimeMillis()));
		
		this.updateRelaLBEUserAndLtcard(card);
	}
	
	public LotteryChatCardDTO  toLotteryChatCardDTO(LotteryChatCard entity)
	{
		LotteryChatCardDTO dto = new LotteryChatCardDTO();
		
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}
	
	public List<LotteryChatCardDTO> toLotteryChatCardDTOs(List<LotteryChatCard> entites)
	{
		List<LotteryChatCardDTO> dtos = new ArrayList<LotteryChatCardDTO>();
		
		if(null != entites)
		{
			for (LotteryChatCard entity : entites) {
				LotteryChatCardDTO dto = this.toLotteryChatCardDTO(entity);
				
				dtos.add(dto);
			}
		}
		
		return dtos;
	}
}
