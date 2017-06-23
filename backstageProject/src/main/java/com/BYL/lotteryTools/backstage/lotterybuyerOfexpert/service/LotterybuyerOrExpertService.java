package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotteryChatCardDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotteryChatCard;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.RelaLBEUserAndLtcard;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface LotterybuyerOrExpertService {

	public void save(LotterybuyerOrExpert entity);
	
	public void update(LotterybuyerOrExpert entity);
	
	public LotterybuyerOrExpertDTO toDTO(LotterybuyerOrExpert entity);
	
	public List<LotterybuyerOrExpertDTO> toDTOs(List<LotterybuyerOrExpert> entities);
	
	public QueryResult<LotterybuyerOrExpert> getLotterybuyerOrExpertList(
			int page,
			int rows,
			String name,
			String provinceCode,
			String isVirtual,
			String isRobot,
			String telephone);
	
	public LotterybuyerOrExpert getLotterybuyerOrExpertById(String id);
	
	public LotterybuyerOrExpert getLotterybuyerOrExpertByTelephone(String telephone);
	
	public List<LotterybuyerOrExpert> getLotterybuyerOrExpertByCailiaoName(String cailiaoName);
	
	public String createRobotUser(String province,String city,String lotteryType,HttpServletRequest request);
	
	//用户和卡关联操作
	public RelaLBEUserAndLtcard getRelaLBEUserAndLtcardByUserIdAndCardId(String userId,String cardId);
	
	public RelaLBEUserAndLtcard getRelaLBEUserAndLtcardById(String id);
	
	public void saveRelaLBEUserAndLtcard(RelaLBEUserAndLtcard entity);
	
	public void updateRelaLBEUserAndLtcard(RelaLBEUserAndLtcard entity);
	
	public void deleteRelaLBEUserAndLtcard(RelaLBEUserAndLtcard entity);
	
	//彩聊卡操作
	public LotteryChatCard getLotteryChatCardById(String id);
	
	public List<LotteryChatCard> findAllLotteryChatCards();
	
	public List<LotteryChatCardDTO> toLotteryChatCardDTOs(List<LotteryChatCard> entites);
	
	public LotteryChatCardDTO  toLotteryChatCardDTO(LotteryChatCard entity);
	
	//添加一张建群卡
	public void updateCardsOfUser(LotterybuyerOrExpert owner,String cardId,Integer num);
	
	//减少一张建群卡
	public void reduceCardsOfUser(LotterybuyerOrExpert owner,String cardId);
	
}
