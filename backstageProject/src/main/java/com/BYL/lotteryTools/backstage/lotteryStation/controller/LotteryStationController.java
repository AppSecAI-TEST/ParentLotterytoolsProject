package com.BYL.lotteryTools.backstage.lotteryStation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BYL.lotteryTools.backstage.lotteryStation.dto.LotteryStationDTO;
import com.BYL.lotteryTools.backstage.lotteryStation.service.LotteryStationService;

@Controller
@RequestMapping("/lotteryStation")
public class LotteryStationController
{
	private Logger logger = LoggerFactory.getLogger(LotteryStationController.class);
	
	@Autowired
	private LotteryStationService lotteryStationService;
	
	/**
	 * 
	* @Title: getDetailStation 
	* @Description: TODO(获取彩票站数据详情) 
	* @param @param id
	* @param @return    设定文件 
	* @author banna
	* @date 2017年3月14日 上午9:25:10 
	* @return LotteryStationDTO    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getDetailStation" , method = RequestMethod.GET)
	public @ResponseBody LotteryStationDTO getDetailStation(
			@RequestParam(value="id",required=true) String id)
	{
		LotteryStationDTO lotteryStationDTO = new LotteryStationDTO();
		
		
		
		return lotteryStationDTO;
	}
	
	
	
	
	
	
	
	
	
}
