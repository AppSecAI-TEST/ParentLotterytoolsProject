package com.BYL.lotteryTools.backstage.serviceNumber.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BYL.lotteryTools.backstage.serviceNumber.service.ServiceNumberService;

@Controller
@RequestMapping("/snumber")
public class ServiceNumberController 
{
	private static final Logger LOG = LoggerFactory.getLogger(ServiceNumberController.class);
	
	@Autowired
	private ServiceNumberService serviceNumberService;
	
	
	
		
}
