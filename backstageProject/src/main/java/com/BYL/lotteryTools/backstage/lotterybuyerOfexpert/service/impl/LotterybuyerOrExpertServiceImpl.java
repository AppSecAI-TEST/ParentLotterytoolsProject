package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.repository.LotterybuyerOrExpertRepository;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;

@Service("lotterybuyerOrExpertService")
@Transactional(propagation= Propagation.REQUIRED)
public class LotterybuyerOrExpertServiceImpl implements
		LotterybuyerOrExpertService {

	@Autowired
	private LotterybuyerOrExpertRepository lotterybuyerOrExpertRepository;
}
