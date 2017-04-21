package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.common.util.QueryResult;

public interface LotterybuyerOrExpertService {

	public void save(LotterybuyerOrExpert entity);
	
	public void update(LotterybuyerOrExpert entity);
	
	public LotterybuyerOrExpertDTO toDTO(LotterybuyerOrExpert entity);
	
	public List<LotterybuyerOrExpertDTO> toDTOs(List<LotterybuyerOrExpert> entities);
	
	public QueryResult<LotterybuyerOrExpert> getLotterybuyerOrExpertList(Class<LotterybuyerOrExpert> entityClass, String whereJpql, Object[] queryParams, 
			LinkedHashMap<String, String> orderby, Pageable pageable);
	
	public LotterybuyerOrExpert getLotterybuyerOrExpertById(String id);
	
	public LotterybuyerOrExpert getLotterybuyerOrExpertByTelephone(String telephone);
	
	public List<LotterybuyerOrExpert> getLotterybuyerOrExpertByCailiaoName(String cailiaoName);
}
