package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto.LotterybuyerOrExpertDTO;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity.LotterybuyerOrExpert;
import com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.service.LotterybuyerOrExpertService;

/**
 * 
* @Description: TODO(用户（彩民/专家）控制类) 
* @author banna
* @date 2017年3月14日 上午9:52:33
 */
@Controller
@RequestMapping("/lbuyerOrexpert")
public class LotteryBuyerOrExpertController 
{
	private Logger logger = LoggerFactory.getLogger(LotteryBuyerOrExpertController.class);
	
	@Autowired
	private LotterybuyerOrExpertService lotterybuyerOrExpertService;
	
	@RequestMapping(value="/getDetailLborExpert" , method = RequestMethod.GET)
	public @ResponseBody LotterybuyerOrExpertDTO getDetailLborExpert(
			@RequestParam(value="id",required=true) String id)
	{
		LotterybuyerOrExpertDTO lotterybuyerOrExpertDTO = new LotterybuyerOrExpertDTO();
		
		LotterybuyerOrExpert lotterybuyerOrExpert = lotterybuyerOrExpertService.getLotterybuyerOrExpertById(id);
		
		lotterybuyerOrExpertDTO = lotterybuyerOrExpertService.toDTO(lotterybuyerOrExpert);
		
		return lotterybuyerOrExpertDTO;
	}
	
	
	
}
