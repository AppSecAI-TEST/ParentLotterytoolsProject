package com.BYL.lotteryTools.backstage.lotteryManage.service;

import java.util.List;

import com.BYL.lotteryTools.backstage.lotteryManage.dto.LotteryDiPinPlayDTO;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryDiPinPlay;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface LotteryDiPinPlayService {

	public void save(LotteryDiPinPlay entity);
	
	public void update(LotteryDiPinPlay entity);
	
	public LotteryDiPinPlay getLotteryDiPinPlayById(String id);
	
	public QueryResult<LotteryDiPinPlay> getLotteryDiPinPlayList(LotteryDiPinPlayDTO dto,int rows,int page);
	
	public LotteryDiPinPlayDTO toDTO(LotteryDiPinPlay entity);
	
	public List<LotteryDiPinPlayDTO> toDTOs(List<LotteryDiPinPlay> entities);
}
