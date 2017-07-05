package com.BYL.lotteryTools.backstage.lotteryManage.service;

import java.util.List;

import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryDiPinPlayDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryDiPinPlay;
import com.BYL.lotteryTools.backstage.outer.entity.PailieFive;
import com.BYL.lotteryTools.backstage.outer.entity.QiLeCai;
import com.BYL.lotteryTools.backstage.outer.entity.ShuangSQ;
import com.BYL.lotteryTools.backstage.outer.entity.ThreeD;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface LotteryDiPinPlayService {

	public void save(LotteryDiPinPlay entity);
	
	public void update(LotteryDiPinPlay entity);
	
	public LotteryDiPinPlay getLotteryDiPinPlayById(String id);
	
	public QueryResult<LotteryDiPinPlay> getLotteryDiPinPlayList(LotteryDiPinPlayDTO dto,int rows,int page);
	
	public LotteryDiPinPlayDTO toDTO(LotteryDiPinPlay entity);
	
	public List<LotteryDiPinPlayDTO> toDTOs(List<LotteryDiPinPlay> entities);
	
	public LotteryDiPinPlay getLotteryDiPinPlayByPlanCode(String planCode);
	
	//获取3d开奖号
	public List<ThreeD> get3DNumKaijiang(String tableName);
	//获取5个开奖号码的数据（排列5）
	public List<PailieFive> getPailie5NumKaijiang(String tableName);
	//获取7个开奖号码的数据(双色球，大乐透)
	public List<ShuangSQ> getSevenNumberKaijiang(String tableName);
	//获取8个开奖号码的数据
	public List<QiLeCai> getEightNumberKaijiang(String tableName);
}
