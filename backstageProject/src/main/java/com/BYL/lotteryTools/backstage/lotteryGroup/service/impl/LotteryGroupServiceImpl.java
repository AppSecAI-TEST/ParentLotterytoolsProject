package com.BYL.lotteryTools.backstage.lotteryGroup.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryGroup.controller.OuterLotteryGroupController;
import com.BYL.lotteryTools.backstage.lotteryGroup.dto.LotteryGroupDTO;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LGroupLevel;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.LotteryGroupNotice;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaApplyOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaBindOfLbuyerorexpertAndGroup;
import com.BYL.lotteryTools.backstage.lotteryGroup.entity.RelaGroupUpLevelRecord;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.LotteryGroupNoticeRepository;
import com.BYL.lotteryTools.backstage.lotteryGroup.repository.LotteryGroupRespository;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LGroupLevelService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.LotteryGroupService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaApplybuyerAndGroupService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaBindbuyerAndGroupService;
import com.BYL.lotteryTools.backstage.lotteryGroup.service.RelaGroupUpLevelService;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.repository.LotteryPlayRepository;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;
import com.BYL.lotteryTools.backstage.outer.repository.rongYunCloud.io.rong.models.CodeSuccessResult;
import com.BYL.lotteryTools.backstage.outer.service.RongyunImService;
import com.BYL.lotteryTools.backstage.user.entity.City;
import com.BYL.lotteryTools.backstage.user.entity.Province;
import com.BYL.lotteryTools.backstage.user.service.CityService;
import com.BYL.lotteryTools.backstage.user.service.ProvinceService;
import com.BYL.lotteryTools.common.bean.ResultBean;
import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QRCodeUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lotteryGroupService")
@Transactional(propagation=Propagation.REQUIRED)
public class LotteryGroupServiceImpl implements LotteryGroupService 
{
	private Logger LOG = LoggerFactory.getLogger(LotteryGroupServiceImpl.class);
	
	@Autowired
	private LotteryGroupRespository lotteryGroupRespository;
	
	@Autowired
	private UploadfileService uploadfileService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired 
	private CityService cityService;
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	@Autowired
	private RelaBindbuyerAndGroupService relaBindbuyerAndGroupService;
	
	@Autowired
	private RongyunImService rongyunImService;
	
	@Autowired
	private RelaApplybuyerAndGroupService relaApplybuyerAndGroupService;
	
	@Autowired
	private RelaGroupUpLevelService relaGroupUpLevelService;
	
	@Autowired
	private LotteryGroupNoticeRepository lotteryGroupNoticeRepository;
	
	@Autowired
	private LotteryPlayRepository lotteryPlayRepository;
	
	@Autowired
	private LGroupLevelService lGroupLevelService;
	
	
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
					
					//获取当前群的高频彩种
					List<LotteryPlay> gaopin = lotteryPlayRepository.
							getLotteryPlayByProvinceAndLotteryType(entity.getProvince(), entity.getLotteryType());
					String lnumArr[] = new String[gaopin.size()];
					String lplayNameArr[] = new String[gaopin.size()];
					String lplayIdArr[] = new String[gaopin.size()];
					for (int i=0;i<gaopin.size();i++) {
						lnumArr[i] = gaopin.get(i).getLotteryNumber();
						lplayNameArr[i] = gaopin.get(i).getName();
						lplayIdArr[i] = gaopin.get(i).getId();
					}
					dto.setLotteryNumber(lnumArr);
					dto.setGaoPinLotteryPlay(lplayNameArr);
					dto.setGaoPinLotteryPlayId(lplayIdArr);
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
		return "1"+str.toString();
	}

	public List<LotteryGroup> getLotteryGroupByProvinceAndLotteryType(
			String province, String lotteryType) {
		return lotteryGroupRespository.getLotteryGroupByProvinceAndLotteryType(province, lotteryType);
	}

	public List<LotteryGroup> getLotteryGroupByProvinceAndLotteryTypeAndCityAndDetailLotteryType(
			String province, String lotteryType, String city,
			String detailLotteryType) {
		return lotteryGroupRespository.getLotteryGroupByProvinceAndLotteryTypeAndCityAndDetailLotteryType
				(province, lotteryType, city, detailLotteryType);
	}

	/**
	 * 
	* @Title: joinInCityCenterGroup 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param province
	* @param @param city
	* @param @param lotteryType
	* @param @param detailLotteryType
	* @param @param userId:要加入中心群的用户id
	* @param @return    设定文件 
	* @author banna
	* @date 2017年6月6日 下午5:22:33 
	* @throws
	 */
	public boolean joinInCityCenterGroup(String province, String city,
			String lotteryType, String detailLotteryType,String userId) {
		
		boolean flag = true;
		LotteryGroup group = new LotteryGroup();
		List<LotteryGroup> lotteryGroups = lotteryGroupRespository.getLotteryGroupByProvinceAndLotteryTypeAndCityAndDetailLotteryType
					(province, lotteryType, city, detailLotteryType);
		LotterybuyerOrExpert user = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(userId);
		if(null != user && null != lotteryGroups && lotteryGroups.size() != 0)
		{
			//每个群最多能加3000人，
			group = lotteryGroups.get(0);//获取最近创建的群
			//判断当前群已加入多少人
			Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
			QueryResult<RelaBindOfLbuyerorexpertAndGroup> lQueryResult = relaBindbuyerAndGroupService.
					getMemberOfJoinGroup(pageable, group.getId());
			List<RelaBindOfLbuyerorexpertAndGroup> relalist = lQueryResult.getResultList();
			if(null != relalist)
			{
				if(relalist.size()>3000)
				{
					//群人数多于3000人，需要创建新群
					group = this.createCenterCityGroup(province, city, lotteryType);
				}
				/*else
				{
					addUserToLotteryGroup(user,group);//添加用户到群
				}*/
				
				
			}
			else
			{//当前符合条件群不存在，新建
				group = this.createCenterCityGroup(province, city, lotteryType);
			}
			addUserToLotteryGroup(user,group);//添加用户到群
		}
		
		
		return flag;
	}
	
	//创建中心群
	private LotteryGroup createCenterCityGroup(String province,String city,String lotteryType)
	{
		LotteryGroup group = new LotteryGroup();
		StringBuffer groupName = new StringBuffer();
		Province pro = provinceService.getProvinceByPcode(province);
		City cityname = cityService.getCityByCcode(city);
		groupName.append(pro.getPname()).
			append(cityname.getCname()).
			append("1".equals(lotteryType)?"体彩中心群":"福彩中心群");
		
		group.setId(UUID.randomUUID().toString());
		group.setName(groupName.toString());
		group.setProvince(province);
		group.setCity(city);
		group.setLotteryType(Constants.CENTER_CITY_GROUP_ID);
		group.setDetailLotteryType(lotteryType);
		group.setFabuKj(0);
		group.setFabuZs(0);
		group.setSsKjChaxun(0);
		group.setSsYlChaxun(0);
		group.setSsZjChaxun(0);
		group.setNoticeReview(0);
		group.setJoinType(0);//0为自由加入
		group.setLotteryBuyerOrExpert(null);//群主
		
		group.setIsDeleted(Constants.IS_NOT_DELETED);
		group.setCreator("system");
		group.setCreateTime(new Timestamp((System.currentTimeMillis())));
		group.setModify("system");
		group.setModifyTime(new Timestamp((System.currentTimeMillis())));
		this.save(group);
		
		return group;
	}
	
	//添加用户到群
	public String addUserToLotteryGroup(LotterybuyerOrExpert user,LotteryGroup group)
	{
		RelaBindOfLbuyerorexpertAndGroup rela = new RelaBindOfLbuyerorexpertAndGroup();
		rela.setIsDeleted(Constants.IS_NOT_DELETED);
		rela.setIsReceive("1");
		rela.setIsTop("0");//是否置顶1：置顶 0：不置顶
		rela.setIsGroupOwner("0");//群成员
		rela.setLotterybuyerOrExpert(user);
		rela.setLotteryGroup(group);
		rela.setCreator(group.getId());
		rela.setCreateTime(new Timestamp(System.currentTimeMillis()));
		rela.setModify(group.getId());
		rela.setModifyTime(new Timestamp(System.currentTimeMillis()));
		//保存关联
		relaBindbuyerAndGroupService.save(rela);
		
		//建立融云中群和用户的关系
		String[] joinUsers = {user.getId()};
		CodeSuccessResult result= rongyunImService.joinUserInGroup(joinUsers, group.getId(), group.getName());
		if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
		{
			LOG.error("融云群加入用户报错", result.getErrorMessage());
		}
		return result.getCode().toString();
	}

	public List<LotteryGroup> getLotteryGroupByLotteryType(String lotteryType) {
		return lotteryGroupRespository.getLotteryGroupByLotteryType(lotteryType);
	}
	
	@SuppressWarnings("unused")
	public  ResultBean joinUserInGroup(
			String[] joinUsers,
			String groupId)
	{
		ResultBean resultBean = new ResultBean();
		
		//建立群和要加入用户的关联
		LotteryGroup group = this.getLotteryGroupById(groupId);
		Integer nowMemberCount = group.getRelaBindOfLbuyerorexpertAndGroups().size();//获取当前群中的群成员人数
		Integer memberCount = group.getMemberCount();//获取群成员可以加入的人数
		
		//若要加入的人数和现在的人数的总和小于群可以加入的人数，则可以继续添加，否则无法添加
		int overplusMember= memberCount-nowMemberCount-joinUsers.length;//获取可以加入的人数
		if(null != group)
		{
			if(overplusMember>=0)
			{
				LotterybuyerOrExpert user = null;
				RelaBindOfLbuyerorexpertAndGroup rela = null;
				for (String userId : joinUsers) 
				{
					rela = relaBindbuyerAndGroupService.getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(userId, groupId);
					if(null == rela)
					{
						user = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(userId);
						rela = new RelaBindOfLbuyerorexpertAndGroup();
						rela.setIsDeleted(Constants.IS_NOT_DELETED);
						rela.setIsReceive("1");
						rela.setIsTop("0");//是否置顶1：置顶 0：不置顶
						rela.setIsGroupOwner("0");//群成员
						rela.setLotterybuyerOrExpert(user);
						rela.setLotteryGroup(group);
						rela.setCreator(groupId);
						rela.setCreateTime(new Timestamp(System.currentTimeMillis()));
						rela.setModify(groupId);
						rela.setModifyTime(new Timestamp(System.currentTimeMillis()));

						//保存关联
						relaBindbuyerAndGroupService.save(rela);
						
						//建立融云中群和用户的关系
						CodeSuccessResult result= rongyunImService.joinUserInGroup(joinUsers, groupId, group.getName());
						if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
						{
							LOG.error("融云群加入用户报错", result.getErrorMessage());
						}
						resultBean.setFlag(true);
						resultBean.setResultCode(Constants.SUCCESS_CODE);
						resultBean.setMessage("加入成功");
					}
					else
					{
						LOG.error("加群错误", "当前userId="+userId+"的用户已加入:"+group.getName()+"群内，无法多次加入");
						resultBean.setFlag(false);
						resultBean.setResultCode(Constants.FAIL_CODE_OF_USER_IS_ALREADY_JOIN_GROUP);
						resultBean.setMessage("加入失败");
					}
					
					//向群内发送“加群”小灰条消息
					String[] groupIds = {groupId};
					rongyunImService.sendInfoNtfMessageToGroups(userId,groupIds , "用户"+user.getName()+"加入"+group.getName()+"群", null);
				}
			}
			else
			{
				int couldJoin = joinUsers.length-(nowMemberCount+joinUsers.length-memberCount);
				resultBean.setFlag(false);
				resultBean.setResultCode(Constants.FAIL_CODE_OF_JOIN_GROUP_MEMBER);
				resultBean.setMessage("群等级不够加入当前要求加入的人数，当前只可以加入:"+couldJoin+"人");
			}
		}
		else
		{
			resultBean.setFlag(false);
			resultBean.setResultCode(Constants.FAIL_CODE_OF_GROUP_IS_NOT_FIND);
			resultBean.setMessage("没有查找到要加入的群");
		}
		
		return resultBean;
	}
	
	public Map<String,Object> deleteGroup(
			LotteryGroupDTO dto,
			HttpServletRequest request,HttpSession httpSession)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		//删除群的同时删除群绑定的机器人
		LotteryGroup entity = this.getLotteryGroupById(dto.getId());
		
		if(null != entity)
		{
			String groupId = entity.getId();
			entity.setGroupRobotID(null);
			//删除融云的群信息
			CodeSuccessResult result = rongyunImService.groupDismiss(entity.getLotteryBuyerOrExpert().getId(), groupId);
			if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
			{
				LOG.error("融云删除群报错", result.getErrorMessage());
			}
			
			//删除数据库中的群信息
			entity.setIsDeleted(Constants.IS_DELETED);
			entity.setModify(dto.getOwnerId());
			entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
			entity.setLotteryBuyerOrExpert(null);
			
			//删除群的同时删除群的关联关系(用户和群的关联关系)
			Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
			QueryResult<RelaBindOfLbuyerorexpertAndGroup> relas = relaBindbuyerAndGroupService.getMemberOfJoinGroup(pageable, groupId);
			List<RelaBindOfLbuyerorexpertAndGroup> list = relas.getResultList();
			if(null != list)
			{
				for (RelaBindOfLbuyerorexpertAndGroup relaBindOfLbuyerorexpertAndGroup : list) 
				{
					try
					{
						relaBindbuyerAndGroupService.delete(relaBindOfLbuyerorexpertAndGroup);
					}
					catch(Exception e)
					{
						LOG.error("delete,error:", e);
					}
				}
			}
			
			//删除加群申请的关联关系
			List<RelaApplyOfLbuyerorexpertAndGroup> applys = relaApplybuyerAndGroupService.
					getRelaApplyOfLbuyerorexpertAndGroupByGroupId(groupId);
			if(null != applys)
			{
				for (RelaApplyOfLbuyerorexpertAndGroup delApply : applys) 
				{
					relaApplybuyerAndGroupService.delete(delApply);
				}
			}
			
			//删除群等级关联关系
			List<RelaGroupUpLevelRecord> records = relaGroupUpLevelService.getRelaGroupUpLevelRecordByGroupId(groupId);
			if(null != records)
			{
				for (RelaGroupUpLevelRecord relaGroupUpLevelRecord : records) {
					relaGroupUpLevelService.delete(relaGroupUpLevelRecord);
				}
			}
			
			//删除群头像和群二维码
			List<Uploadfile> touxiang = uploadfileService.getUploadfilesByNewsUuid(entity.getTouXiang());
			if(null != touxiang &&touxiang.size()!=0)
				uploadfileService.deleteUploadFile(touxiang, httpSession);//调用删除附件数据和附件文件方法
			
			//删除二维码图片
			String savePath = httpSession.getServletContext().getRealPath(entity.getGroupQRImg());//获取二维码绝对路径
			File dirFile = new File(savePath);
			boolean deleteFlag = dirFile.delete();
			if(deleteFlag)
			{
				LOG.info("删除成功",deleteFlag);
			}
			else
			{
				LOG.error(Constants.ERROR_STR,"删除失败，文件："+entity.getGroupQRImg());//若删除失败记录未删除成功的文件,之后做手动删除
			}
			
			//删除群公告
			List<LotteryGroupNotice> noticelist = entity.getLotteryGroupNotices();
			for (LotteryGroupNotice lotteryGroupNotice : noticelist) {
				lotteryGroupNoticeRepository.delete(lotteryGroupNotice);
			}
			
			entity.setRelaApplyOfLbuyerorexpertAndGroups(null);
			entity.setRelaBindOfLbuyerorexpertAndGroups(null);
			entity.setRelaGroupUpLevelRecords(null);
			entity.setLotteryGroupNotices(null);
			this.update(entity);
			map.put(Constants.MESSAGE_STR, "删除成功");
			map.put(Constants.CODE_STR, Constants.SUCCESS_CODE);
			map.put(Constants.FLAG_STR, true);
		}
		else
		{
			map.put(Constants.MESSAGE_STR, "删除失败");
			map.put(Constants.CODE_STR, Constants.FAIL_CODE_OF_DELETE_GROUP);
			map.put(Constants.FLAG_STR, false);
		}
		return map;
	}

	public List<LotteryGroup> getLotteryGroupByGroupOwnerId(String groupOwnerId) {
		return lotteryGroupRespository.getLotteryGroupByGroupOwnerId(groupOwnerId);
	}

	public ResultBean quitUserFronGroup(String[] quitUsers, String groupId) {
		//解除群和要加入用户的关联
		ResultBean resultBean = new ResultBean();
		LotteryGroup group = this.getLotteryGroupById(groupId);
		String[] groupIds = {groupId};
		for (String userId : quitUsers) 
		{
			//根据用户id和群id获取关联关系
			RelaBindOfLbuyerorexpertAndGroup rela = relaBindbuyerAndGroupService.
					getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(userId, groupId);
			LotterybuyerOrExpert user = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(userId);
			//机器人用户不可以被删除
			if(null != rela && !"1".equals(user.getIsRobot()))
			{
				//删除关联
				rela.setLotterybuyerOrExpert(null);
				rela.setLotteryGroup(null);
				rela.setIsDeleted(Constants.IS_DELETED);
				relaBindbuyerAndGroupService.update(rela);
			}
			//向群内发送“加群”小灰条消息
			rongyunImService.sendInfoNtfMessageToGroups(userId,groupIds , "用户"+user.getName()+"退出"+group.getName()+"群", null);
		}
		//删除融云中群和用户的关系
		try
		{
			CodeSuccessResult result = rongyunImService.quitUserFronGroup(quitUsers, groupId);
			if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
			{
				LOG.error("融云执行用户退群时错误：", result.getErrorMessage());
			}
		}
		catch(Exception e)
		{
			LOG.error(Constants.ERROR_STR, e);
			resultBean.setFlag(false);
			resultBean.setResultCode(Constants.SERVER_FAIL_CODE);
			resultBean.setMessage("服务器错误");
		}
		
		resultBean.setFlag(true);
		resultBean.setResultCode(Constants.SUCCESS_CODE);
		resultBean.setMessage("退群成功");
		return resultBean;
	}

	public ResultBean saveOrUpdateGroup(LotteryGroupDTO dto,HttpServletRequest request) 
	{
		ResultBean bean = new ResultBean();
		LotteryGroup entity = this.getLotteryGroupById(dto.getId());
		if(null != entity)
		{//修改群信息
			try
			{
				String lastName = entity.getName();
				String lastOwnerId = entity.getLotteryBuyerOrExpert().getId();
				
				entity.setName(dto.getName());
				entity.setGroupLevel(dto.getGroupLevel());
				entity.setFabuKj(dto.getFabuKj());
				entity.setFabuZs(dto.getFabuZs());
				entity.setIntroduction(dto.getIntroduction());
//				entity.setJoinType(dto.getJoinType());//前端没有修改加入方式的字段
				entity.setLotteryType(dto.getLotteryType());
				if(!"4".equals(dto.getLotteryType()))
				{
					entity.setDetailLotteryType(dto.getLotteryType());
				}
				entity.setProvince(dto.getProvince());
				entity.setCity(dto.getCity());
				entity.setSsKjChaxun(dto.getSsKjChaxun());
				entity.setSsYlChaxun(dto.getSsYlChaxun());
				entity.setSsZjChaxun(dto.getSsZjChaxun());
				entity.setNoticeReview(dto.getNoticeReview());
				if(!lastOwnerId.equals(dto.getOwnerId()))
				{
					LotterybuyerOrExpert owner = lotterybuyerOrExpertService.
							getLotterybuyerOrExpertById(dto.getOwnerId());
					entity.setLotteryBuyerOrExpert(owner);//更改群与群主的关系
					
					//2017-5-16ADD：建立群主和群的加入关系
					RelaBindOfLbuyerorexpertAndGroup rela 
						= relaBindbuyerAndGroupService.getRelaBindOfLbuyerorexpertAndGroupByUserIdAndGroupId(lastOwnerId, dto.getId());
					if(null != rela)
						relaBindbuyerAndGroupService.delete(rela);
					
					rela = new RelaBindOfLbuyerorexpertAndGroup();
					rela.setIsDeleted(Constants.IS_NOT_DELETED);
					rela.setIsReceive("1");
					rela.setIsTop("0");//是否置顶1：置顶 0：不置顶
					rela.setIsGroupOwner("1");//群主
					rela.setLotterybuyerOrExpert(owner);
					rela.setLotteryGroup(entity);
					rela.setCreator(dto.getOwnerId());
					rela.setCreateTime(new Timestamp(System.currentTimeMillis()));
					rela.setModify(dto.getOwnerId());
					rela.setModifyTime(new Timestamp(System.currentTimeMillis()));
					//保存关联
					relaBindbuyerAndGroupService.save(rela);
				}
				
				if(!lastName.equals(entity.getName()))
				{
					String logo = null;//内嵌logo图片，若群头像不为空，则嵌入群头像
					String uploadPath = "upload";
					String path = request.getSession().getServletContext().getRealPath(uploadPath); 
					Uploadfile uploadfile = uploadfileService.getUploadfileByNewsUuid(entity.getTouXiang());
					if(null != uploadfile)
						logo = path+File.separator+uploadfile.getUploadRealName();
					
					String fileName = QRCodeUtil.encode(entity.getGroupNumber(), logo, path, true,entity.getGroupNumber());
					entity.setGroupQRImg(File.separator+uploadPath+File.separator+fileName);
				}
				this.update(entity);
				bean.setFlag(true);
				bean.setMessage("修改成功");
			}
			catch(Exception e)
			{
				LOG.error("error:", e);
				bean.setMessage("修改失败");
				bean.setFlag(false);
			}
		}
		else
		{
			//在服务器创建群信息
			try 
			{
				entity = new LotteryGroup();
				BeanUtil.copyBeanProperties(entity, dto);
				entity.setId(UUID.randomUUID().toString());//生成id
				
				if(!"4".equals(dto.getLotteryType()))
				{
					entity.setDetailLotteryType(dto.getLotteryType());
				}
				
				entity.setGroupNumber(this.generateGroupNumber());//放置群号
				LotterybuyerOrExpert owner = lotterybuyerOrExpertService.
						getLotterybuyerOrExpertById(dto.getOwnerId());
				entity.setLotteryBuyerOrExpert(owner);//放置群与群主的关系
				
				//处理群头像
				Uploadfile uploadfile =null;
				if(null != dto.getTouXiangImg())
				{
					String newsUuid = UUID.randomUUID().toString();
					try {
							 uploadfile = uploadfileService.uploadFiles(dto.getTouXiangImg(),request,newsUuid);
						
					} catch (Exception e) {
						LOG.error("error:", e);
					}
					if(null != uploadfile)
						entity.setTouXiang(uploadfile.getNewsUuid());
				}
				else
				{
					if(null != dto.getTouXiang() && !"".equals(dto.getTouXiang()))
					{
						uploadfile = uploadfileService.getUploadfileByNewsUuid(dto.getTouXiang());
						
					}
				}
				
				//TODO:创建群的同时创建群的机器人,如果区域彩种机器人已经存在，或者机器人加群数以及饱和，则要再创建机器人
				String robotUserId = lotterybuyerOrExpertService.
						createRobotUser(dto.getProvince(), dto.getCity(), dto.getLotteryType(),request);
				
				entity.setGroupRobotID(robotUserId);
				
				
				//TODO:放置群等级
				String level1Id = "1";//等级1群的等级id
				entity.setMemberCount(20);//以及群
				entity.setGroupLevel(level1Id);
				
				entity.setIsDeleted(Constants.IS_NOT_DELETED);
				entity.setCreator(dto.getOwnerId());
				entity.setCreateTime(new Timestamp((System.currentTimeMillis())));
				entity.setModify(dto.getOwnerId());
				entity.setModifyTime(new Timestamp((System.currentTimeMillis())));
				
				//生成群二维码
				String logo = null;//内嵌logo图片，若群头像不为空，则嵌入群头像
				String uploadPath = "upload";
				String path = request.getSession().getServletContext().getRealPath(uploadPath); 
				if(null != uploadfile)
					logo = path+File.separator+uploadfile.getUploadRealName();
				
				String fileName = QRCodeUtil.encode(entity.getGroupNumber(), logo, path, true,entity.getGroupNumber());
				entity.setGroupQRImg(File.separator+uploadPath+File.separator+fileName);
				
				//保存群信息
				this.save(entity);
				
				//放置群升级记录表数据
				RelaGroupUpLevelRecord level = new RelaGroupUpLevelRecord();
				LGroupLevel L1 = lGroupLevelService.getLGroupLevelByID(level1Id);//获取L1等级的实体数据
				level.setAfterLevel(L1);//一级群
				level.setBeforeLevel(null);
				level.setCreateTime(new Timestamp(System.currentTimeMillis()));
				level.setCreator(entity.getId());
				level.setIsDeleted(Constants.IS_NOT_DELETED);
				level.setLotteryGroup(entity);
				level.setModifyTime(new Timestamp(System.currentTimeMillis()));
				level.setModify(entity.getId());
				level.setOperator(dto.getOwnerId());
				relaGroupUpLevelService.save(level);//保存群等级记录表数据
				
				//2017-5-11ADD：建立群主和群的加入关系
				RelaBindOfLbuyerorexpertAndGroup rela = new RelaBindOfLbuyerorexpertAndGroup();
				rela.setIsDeleted(Constants.IS_NOT_DELETED);
				rela.setIsReceive("1");
				rela.setIsTop("0");//是否置顶1：置顶 0：不置顶
				rela.setIsGroupOwner("1");//群主
				rela.setLotterybuyerOrExpert(owner);
				rela.setLotteryGroup(entity);
				rela.setCreator(dto.getOwnerId());
				rela.setCreateTime(new Timestamp(System.currentTimeMillis()));
				rela.setModify(dto.getOwnerId());
				rela.setModifyTime(new Timestamp(System.currentTimeMillis()));
				//保存关联
				relaBindbuyerAndGroupService.save(rela);
				
				//创建机器人和群的关联关系
				LotterybuyerOrExpert robot = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(robotUserId);
				RelaBindOfLbuyerorexpertAndGroup relaRobot = new RelaBindOfLbuyerorexpertAndGroup();
				relaRobot.setIsDeleted(Constants.IS_NOT_DELETED);
				relaRobot.setIsReceive("1");
				relaRobot.setIsTop("0");//是否置顶1：置顶 0：不置顶
				relaRobot.setIsGroupOwner("0");//群主
				relaRobot.setLotterybuyerOrExpert(robot);
				relaRobot.setLotteryGroup(entity);
				relaRobot.setCreator(robotUserId);
				relaRobot.setCreateTime(new Timestamp(System.currentTimeMillis()));
				relaRobot.setModify(robotUserId);
				relaRobot.setModifyTime(new Timestamp(System.currentTimeMillis()));
				//保存关联
				relaBindbuyerAndGroupService.save(relaRobot);
				
				//在融云创建群信息
				String[] joinUserId = {dto.getOwnerId(),robotUserId};//群主id加入要加入群的数组中,机器人加入群组中
				CodeSuccessResult result = rongyunImService.createGroup(joinUserId, entity.getId(), entity.getName());
				
				if(!OuterLotteryGroupController.SUCCESS_CODE.equals(result.getCode().toString()))
				{//若创建失败
					bean.setMessage( result.getErrorMessage());//创建失败返回融云端群创建失败信息
					LOG.error("createGroup error:", result.getErrorMessage());
					bean.setFlag(false);
				}
				else
				{
					bean.setFlag(true);
					bean.setMessage("创建成功");
					
					//创建成功后，将当前群主的建群卡个数减1
				}
				
				
				
				
			} catch (Exception e) 
			{
				LOG.error("error:", e);
				bean.setMessage("创建失败");
				bean.setFlag(false);
			}
			
		}
		return bean;
	}
	
}
