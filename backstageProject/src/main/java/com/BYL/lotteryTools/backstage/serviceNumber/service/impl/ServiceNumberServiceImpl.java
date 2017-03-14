package com.BYL.lotteryTools.backstage.serviceNumber.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.BYL.lotteryTools.backstage.serviceNumber.repository.ServiceNumberRepository;
import com.BYL.lotteryTools.backstage.serviceNumber.service.ServiceNumberService;

@Service("serviceNumberService")
@Transactional(propagation = Propagation.REQUIRED)
public class ServiceNumberServiceImpl implements ServiceNumberService {

	@Autowired
	private ServiceNumberRepository serviceNumberRepository;
}
