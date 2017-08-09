package com.BYL.lotteryTools.backstage.lotteryManage.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotteryManage.dao.WeixinDao;
import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryDiPinPlayDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryDiPinPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.repository.LotteryDiPinPlayPlanRepository;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryDiPinPlayService;
import com.BYL.lotteryTools.backstage.outer.entity.PailieFive;
import com.BYL.lotteryTools.backstage.outer.entity.QiLeCai;
import com.BYL.lotteryTools.backstage.outer.entity.ShuangSQ;
import com.BYL.lotteryTools.backstage.outer.entity.ThreeD;
import com.BYL.lotteryTools.backstage.outer.repository.PailieFiveRepository;
import com.BYL.lotteryTools.backstage.outer.repository.QiLeCaiRepository;
import com.BYL.lotteryTools.backstage.outer.repository.ShuangSQRepository;
import com.BYL.lotteryTools.backstage.outer.repository.ThreeDRepository;
import com.BYL.lotteryTools.common.util.BeanUtil;
import com.BYL.lotteryTools.common.util.DateUtil;
import com.BYL.lotteryTools.common.util.QueryResult;

@Service("lotteryDiPinPlayService")
@Transactional(propagation=Propagation.REQUIRED)
public class LotteryDiPinPlayServiceImpl implements LotteryDiPinPlayService {

	@Autowired
	private LotteryDiPinPlayPlanRepository lotteryDiPinPlayPlanRepository;
	
	@Autowired
	private ThreeDRepository threeDRepository;
	
	@Autowired
	private PailieFiveRepository pailieFiveRepository;
	
	@Autowired
	private ShuangSQRepository shuangSQRepository;
	
	@Autowired
	private QiLeCaiRepository qiLeCaiRepository;
	
	@Autowired
	private WeixinDao weixinDao;

	public void save(LotteryDiPinPlay entity) {
		lotteryDiPinPlayPlanRepository.save(entity);		
	}

	public void update(LotteryDiPinPlay entity) {
		lotteryDiPinPlayPlanRepository.save(entity);		
	}

	public LotteryDiPinPlay getLotteryDiPinPlayById(String id) {
		return lotteryDiPinPlayPlanRepository.getLotteryDiPinPlayById(id);
	}

	public QueryResult<LotteryDiPinPlay> getLotteryDiPinPlayList(
			LotteryDiPinPlayDTO dto, int rows, int page) {
		
		Pageable pageable = new PageRequest(page-1,rows);
		
		//参数
		StringBuffer buffer = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		
		//只查询未删除数据
		params.add("1");//只查询有效的数据
		buffer.append(" isDeleted = ?").append(params.size());
		
		if(null != dto.getPlanName() && !"".equals(dto.getPlanName()))
		{
			params.add(dto.getPlanName());
			buffer.append(" and planName = ?").append(params.size());
		}
		
		if(null != dto.getPlanCode() && !"".equals(dto.getPlanCode()))
		{
			params.add(dto.getPlanCode());
			buffer.append(" and planCode = ?").append(params.size());
		}
		
		if(null != dto.getLotteryType() && !"".equals(dto.getLotteryType()))
		{
			params.add(dto.getLotteryType());
			buffer.append(" and lotteryType = ?").append(params.size());
		}
		
		
		if(null != dto.getId() && !"".equals(dto.getId()))
		{//校验修改中的值的唯一性
			params.add(dto.getId());
			buffer.append(" and id != ?").append(params.size());
		}
		
		
	 	
		//排序
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
		orderBy.put("createTime", "desc");
		
		QueryResult<LotteryDiPinPlay> queryResult = lotteryDiPinPlayPlanRepository.
				getScrollDataByJpql(LotteryDiPinPlay.class,buffer.toString(), params.toArray(),orderBy, pageable);
		return queryResult;
	}

	public LotteryDiPinPlayDTO toDTO(LotteryDiPinPlay entity) {
		
		LotteryDiPinPlayDTO dto = new LotteryDiPinPlayDTO();
		
		if(null != entity)
		{
			try {
				BeanUtil.copyBeanProperties(dto, entity);
				
				if(null != entity.getCreateTime())
				{
					dto.setCreateTimeStr(DateUtil.formatDate
							(entity.getCreateTime(), DateUtil.FULL_DATE_FORMAT	));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public List<LotteryDiPinPlayDTO> toDTOs(List<LotteryDiPinPlay> entities) {
		
		List<LotteryDiPinPlayDTO> dtos = new ArrayList<LotteryDiPinPlayDTO>();
		
		if(null != entities)
		{
			for (LotteryDiPinPlay entity : entities) {
				LotteryDiPinPlayDTO dto = this.toDTO(entity);
				dtos.add(dto);
			}
		}
		
		
		return dtos;
	}

	public LotteryDiPinPlay getLotteryDiPinPlayByPlanCode(String planCode) {
		return lotteryDiPinPlayPlanRepository.getLotteryDiPinPlayByPlanCode(planCode);
	}

	public List<ThreeD> get3DNumKaijiang(String tableName,String issueNumber) {
		int limit =300;
		Object[] queryParams = null;
		StringBuffer execSql = new StringBuffer("SELECT ID,ISSUE_NUMBER,NO1,NO2,NO3,TEST_NUM FROM analysis."+tableName+" ");
		if(null != issueNumber && !"".equals(issueNumber))
		{
			execSql.append(" where ISSUE_NUMBER=? ");
			queryParams = new Object[]{
					issueNumber
			};
		}
		else
		{
			queryParams = new Object[]{
			};
		}
		execSql.append(" ORDER BY ISSUE_NUMBER DESC LIMIT "+limit);
		
		List<ThreeD> threeDList =threeDRepository.getEntityListBySql(ThreeD.class,execSql.toString(), queryParams);
		return threeDList;
	}

	public List<PailieFive> getPailie5NumKaijiang(String tableName,String issueNumber) {
		int limit =300;
		Object[] queryParams = null;
		StringBuffer execSql = new StringBuffer("SELECT ID,ISSUE_NUMBER,NO1,NO2,NO3,NO4,NO5 FROM analysis."+tableName+" ");
		if(null != issueNumber && !"".equals(issueNumber))
		{
			execSql.append(" where ISSUE_NUMBER=? ");
			queryParams = new Object[]{
					issueNumber
			};
		}
		else
		{
			queryParams = new Object[]{
			};
		}
		execSql.append(" ORDER BY ISSUE_NUMBER DESC LIMIT "+limit);
		List<PailieFive> datalist =pailieFiveRepository.getEntityListBySql(PailieFive.class,execSql.toString(), queryParams);
		return datalist;
	}

	public List<ShuangSQ> getSevenNumberKaijiang(String tableName,String issueNumber) {
		int limit =300;
		Object[] queryParams = null;
		StringBuffer execSql = new StringBuffer("SELECT ID,ISSUE_NUMBER,NO1,NO2,NO3,NO4,NO5,NO6,NO7 FROM analysis."+tableName+" ");
		if(null != issueNumber && !"".equals(issueNumber))
		{
			execSql.append(" where ISSUE_NUMBER=? ");
			queryParams = new Object[]{
					issueNumber
			};
		}
		else
		{
			queryParams = new Object[]{
			};
		}
		execSql.append(" ORDER BY ISSUE_NUMBER DESC LIMIT "+limit);
		List<ShuangSQ> datalist =shuangSQRepository.getEntityListBySql(ShuangSQ.class,execSql.toString(), queryParams);
		return datalist;
	}

	public List<QiLeCai> getEightNumberKaijiang(String tableName,String issueNumber) {
		int limit =300;
		Object[] queryParams = null;
		StringBuffer execSql = new StringBuffer("SELECT ID,ISSUE_NUMBER,NO1,NO2,NO3,NO4,NO5,NO6,NO7,NO8 FROM analysis."+tableName+" ");
		if(null != issueNumber && !"".equals(issueNumber))
		{
			execSql.append(" where ISSUE_NUMBER=? ");
			queryParams = new Object[]{
					issueNumber
			};
		}
		else
		{
			queryParams = new Object[]{
			};
		}
		execSql.append(" ORDER BY ISSUE_NUMBER DESC LIMIT "+limit);
		List<QiLeCai> datalist =qiLeCaiRepository.getEntityListBySql(QiLeCai.class,execSql.toString(), queryParams);
		return datalist;
	}

	public boolean addLpBuluPlan(String lotteryPlay, String issueNum,
			String numJ) {
		return weixinDao.addLpBuluPlan(lotteryPlay, issueNum, numJ);
	}


	
	
	
	
	
}
